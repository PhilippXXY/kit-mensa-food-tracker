# KIT Mensa Food Tracker

The **KIT Mensa Food Tracker** is a Java-based application designed to help users track their meals from the [Karlsruhe Institute of Technology Mensa](https://kit.edu). It provides a graphical user interface (GUI) to view the meal plan, meal history, and an overview of the nutritional information of the selected meals.
This project was developed as an assignment during the lecture of of **Java Network Programming (ProkSy)**.
For further interest in the exakt task, please refer to the corresponding [PDFs](./task/).

## Features

- **Meal Plan View**: Displays the available meals for the selected date.
- **Meal History**: Shows the list of meals that the user has selected.
- **Nutritional Overview**: Provides cumulative nutritional information based on the selected meals.
- **Data Persistence**: Reads and writes meal data to a file to maintain state between sessions.

## Installation

1. **Clone the repository**:
    ```sh
    git clone https://github.com/PhilippXXY/kit-mensa-food-tracker.git
    ```

2. **Navigate to the project directory**:
    ```sh
    cd kit-mensa-food-tracker
    ```

3. **Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse)**.

4. **Ensure you have the required dependencies**:
    - `javax.swing`
    - `java.awt`
    - `edu.kit.aifb.atks.mensascraper.lib`

5. **Build and run the project**.

## Usage

1. **Run the application**:
    - The main entry point is the `MensaMain` class.
    - Execute the `main` method to start the application.

2. **Navigate through the GUI**:
    - Use the menu bar to switch between the meal plan, meal history, and nutritional overview.

3. **Select meals**:
    - In the meal plan view, select meals to add them to your history.
    - In the meal history view, you can remove meals by selecting them.

4. **View nutritional information**:
    - The overview section provides cumulative nutritional information based on your meal history.

## Project Structure

- `MensaMain.java`: The main class that initializes and runs the application.
- `MensaGUI.java`: The base class for the GUI, including the menu bar.
- `MensaGUIPlan.java`: The GUI for displaying the meal plan.
- `MensaGUIHistory.java`: The GUI for displaying the meal history.
- `MensaGUIOverview.java`: The GUI for displaying the nutritional overview.
- `MensaData.java`: The class representing meal data and handling data fetching and storage.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgements

- This project uses the `KITMensaScraper` library by [Ferdinand Mütsch](https://github.com/muety) to fetch meal data from the KIT Mensa.