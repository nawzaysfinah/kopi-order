# Kopi Order Mobile Application

This project is a simple mobile application that allows users to order drinks commonly found in a Singaporean kopitiam (coffee shop). The app is built using Android Studio, utilizing Kotlin for the logic and XML for the UI. The application guides users through the ordering process by allowing them to customize their drink selections, including the type of drink, temperature, milk type, and sugar level. It also includes a local SQLite database to store and display orders.

## Features
- **Select Drink Options**: Choose between popular drinks like "Teh" or "Kopi."
- **Customize Temperature**: Choose either hot or iced versions of your drink.
- **Milk Type**: Add or omit milk, with options such as "C," "O," or "Regular."
- **Sugar Level**: Select the amount of sugar: "Kosong" (no sugar), "Siew Dai" (less sugar), or "Regular."
- **Save Orders**: Save your customized drink order to a local database.
- **Show Saved Orders**: Display saved orders in a list format.
- **Clear All Orders**: Delete all saved orders from the database.

## Technologies Used
- **Kotlin**: Main programming language for Android app development.
- **Android XML**: Used for designing the user interface (UI).
- **SQLite**: For local data storage.

## Screenshots
![Screenshot of Kopi Order App](link_to_screenshot)

## How to Run the Project
1. **Clone the Repository**: Clone this project to your local machine using:
   ```bash
   git clone https://github.com/username/kopi-order-app.git
   ```

2. **Open in Android Studio**: Open the project in Android Studio. Ensure you have the Android SDK installed.

3. **Run the Application**: Connect an Android device or use an emulator to run the application.

## Installation Requirements
- **Android Studio**: Version 4.0 or higher.
- **Android SDK**: API Level 21 or higher.
- **Kotlin**: Version 1.4 or higher.

## Code Structure
### Main Files
- **`activity_main.xml`**: Defines the user interface layout, which includes radio buttons for user selections, text views for instructions, and buttons for actions.
- **`MainActivity.kt`**: The main logic of the app. It handles user interactions, manages data flow between the UI and the SQLite database, and displays stored data.
- **`colors.xml`**: Defines the custom colors used in the application to maintain a consistent theme.

## Usage
- **Ordering a Drink**: Select your preferred drink, temperature, milk type, and sugar level. Press the "Add" button to save your order.
- **Clearing Orders**: Use the "Clear" button to remove all saved orders.

## Contributing
If you want to contribute to the project, please follow these steps:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m 'Add a feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a Pull Request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact
For any questions or suggestions, feel free to reach out:
- **Email**: your.email@example.com
- **GitHub**: [nawzaysfinah](https://github.com/nawzaysfinah)

Enjoy your kopi and teh experience with this app! ☕️✨

