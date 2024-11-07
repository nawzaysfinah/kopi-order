package com.example.kopiorder

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var drink: String = ""
    private var temperature: String = ""
    private var milkType: String = ""
    private var sugarLevel: String = ""
    private lateinit var dbHelper: OrderDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Database Helper
        dbHelper = OrderDatabaseHelper(this)

        // RadioGroups and Buttons
        val drinkGroup: RadioGroup = findViewById(R.id.drinkGroup)
        val temperatureGroup: RadioGroup = findViewById(R.id.temperatureGroup)
        val milkGroup: RadioGroup = findViewById(R.id.milkGroup)
        val sugarGroup: RadioGroup = findViewById(R.id.sugarGroup)
        val buttonAddData: Button = findViewById(R.id.addButton)
        val buttonClearData: Button = findViewById(R.id.clearButton)
//        val buttonShowData: Button = findViewById(R.id.showButton)
        val orderSummary: TextView = findViewById(R.id.resultTextView)
        val savedDataDisplay: TextView = findViewById(R.id.savedDataDisplay)

        // Drink Selection
        drinkGroup.setOnCheckedChangeListener { _, checkedId ->
            drink = findViewById<RadioButton>(checkedId).text.toString()
            updateOrderSummary(orderSummary)
        }

        // Temperature Selection
        temperatureGroup.setOnCheckedChangeListener { _, checkedId ->
            temperature = findViewById<RadioButton>(checkedId).text.toString()
            updateOrderSummary(orderSummary)
        }

        // Milk Type Selection
        milkGroup.setOnCheckedChangeListener { _, checkedId ->
            milkType = findViewById<RadioButton>(checkedId).text.toString()
            updateOrderSummary(orderSummary)
        }

        // Sugar Level Selection
        sugarGroup.setOnCheckedChangeListener { _, checkedId ->
            sugarLevel = findViewById<RadioButton>(checkedId).text.toString()
            updateOrderSummary(orderSummary)
        }

        // Add Data to Database
        buttonAddData.setOnClickListener {
            if (drink.isNotEmpty() && temperature.isNotEmpty() && milkType.isNotEmpty() && sugarLevel.isNotEmpty()) {
                try {
                    addDataToDatabase(drink, temperature, milkType, sugarLevel)
                    displaySavedData(savedDataDisplay)
                } catch (e: Exception) {
                    Toast.makeText(this, "Error adding order: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please select all options", Toast.LENGTH_SHORT).show()
            }
        }

        // Clear Data in Database
        buttonClearData.setOnClickListener {
            dbHelper.writableDatabase.execSQL("DELETE FROM ${OrderDatabaseHelper.TABLE_NAME}")
            savedDataDisplay.text = ""
            Toast.makeText(this, "All orders cleared", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateOrderSummary(orderSummary: TextView) {
        val summary = StringBuilder("Order Summary: \n")
        if (drink.isNotEmpty()) {
            summary.append("Drink: $drink\n")
        }
        if (temperature.isNotEmpty()) {
            summary.append("Temperature: $temperature\n")
        }
        if (milkType.isNotEmpty()) {
            summary.append("Milk Type: $milkType\n")
        }
        if (sugarLevel.isNotEmpty()) {
            summary.append("Sugar Level: $sugarLevel\n")
        }
        orderSummary.text = summary.toString()
    }

    private fun addDataToDatabase(drink: String, temperature: String, milkType: String, sugarLevel: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(OrderDatabaseHelper.COLUMN_DRINK, drink)
            put(OrderDatabaseHelper.COLUMN_TEMPERATURE, temperature)
            put(OrderDatabaseHelper.COLUMN_MILK_TYPE, milkType)
            put(OrderDatabaseHelper.COLUMN_SUGAR_LEVEL, sugarLevel)
        }
        val newRowId = db.insert(OrderDatabaseHelper.TABLE_NAME, null, values)
        if (newRowId == -1L) {
            throw Exception("Failed to insert order into database")
        } else {
            Toast.makeText(this, "Order added successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displaySavedData(savedDataDisplay: TextView) {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            OrderDatabaseHelper.TABLE_NAME,
            arrayOf(OrderDatabaseHelper.COLUMN_DRINK, OrderDatabaseHelper.COLUMN_TEMPERATURE, OrderDatabaseHelper.COLUMN_MILK_TYPE, OrderDatabaseHelper.COLUMN_SUGAR_LEVEL),
            null, null, null, null, null
        )

        val data = StringBuilder()
        with(cursor) {
            while (moveToNext()) {
                val drink = getString(getColumnIndexOrThrow(OrderDatabaseHelper.COLUMN_DRINK))
                val temperature = getString(getColumnIndexOrThrow(OrderDatabaseHelper.COLUMN_TEMPERATURE))
                val milkType = getString(getColumnIndexOrThrow(OrderDatabaseHelper.COLUMN_MILK_TYPE))
                val sugarLevel = getString(getColumnIndexOrThrow(OrderDatabaseHelper.COLUMN_SUGAR_LEVEL))
                data.append("$temperature $milkType $drink ($sugarLevel)\n")
            }
        }
        cursor.close()
        savedDataDisplay.text = data.toString()
    }
}

class OrderDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_ENTRIES = """
            CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_DRINK TEXT NOT NULL,
            $COLUMN_TEMPERATURE TEXT NOT NULL,
            $COLUMN_MILK_TYPE TEXT NOT NULL,
            $COLUMN_SUGAR_LEVEL TEXT NOT NULL
            )
        """
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Order.db"
        const val TABLE_NAME = "orders"
        const val COLUMN_ID = "_id"
        const val COLUMN_DRINK = "drink"
        const val COLUMN_TEMPERATURE = "temperature"
        const val COLUMN_MILK_TYPE = "milk_type"
        const val COLUMN_SUGAR_LEVEL = "sugar_level"
    }
}
