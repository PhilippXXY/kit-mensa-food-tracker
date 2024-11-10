package pack;

import javax.swing.*;
import edu.kit.aifb.atks.mensascraper.lib.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * The main class for the Mensa Food Tracker application.
 * 
 * @author Philipp Schmidt (uggro)
 * @version 1.2
 */
public class MensaMain {

    private static JFrame currentFrame;
    static byte restartCounter = 0;

    /**
     * The main entry point of the program.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        try {
            readFileData(); // Read meal data from a file
            openMensaGUIPlan(); // Open the GUI for the meal plan
            registerShutdownHook(); // Register a shutdown hook to write meal data to a file upon program
                                    // termination
        } catch (Exception e) {
            restartCounter++;
            if (restartCounter >= 5) {// Checks for fatal mistakes and forces shutdown
                System.err.println("Programm wurde beendet.");
                registerShutdownHook();
            } else {
                System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
                System.out.println("Das Programm wird neu gestartet.");
                main(args); // Restart the program in case of an error
            }
        }
    }

    /**
     * Reads meal data from a file and populates the selectedMealList.
     */
    public static void readFileData() {
        File file = new File("data/selectedMealList.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            if (file.createNewFile()) {
                System.out.println("Datei erstellt: " + file.getName()); // Print a message indicating file creation
            } else {
                System.out.println("Datei existiert bereits."); // Print a message indicating that the file already
                                                                // exists
                for (int i = 0; i < MensaData.selectedMealList.size(); i++) {
                    MensaData.selectedMealList.remove(i); // Clear the existing meal list
                }
                String line;
                boolean isFirstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }
                    String[] values = line.split(";");
                    if (values.length == 8) {
                        // Extract meal data from each line of the file
                        String mealDate = values[0];
                        String mealName = values[1];
                        float mealPrice = Float.parseFloat(values[2]);
                        MensaMealType mealType = MensaMealType.valueOf(values[3]);
                        float mealKcal = Float.parseFloat(values[4]);
                        float mealProteins = Float.parseFloat(values[5]);
                        float mealCarbs = Float.parseFloat(values[6]);
                        float mealFat = Float.parseFloat(values[7]);
                        // Create a MensaData object with the extracted meal data
                        MensaData mealData = new MensaData(mealDate, mealName, mealPrice, mealType, mealKcal,
                                mealProteins, mealCarbs, mealFat);
                        MensaData.selectedMealList.add(mealData); // Add the meal data to the selectedMealList
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Exception aufgetreten. Datei wurde dennoch erstellt");
        }
    }

    /**
     * Writes meal data to a file.
     */
    public static void writeFileData() {
        File file = new File("data/selectedMealList.txt");
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            writer.write("mealDate;mealName;mealPrice;mealType;mealKcal;mealProteins;mealCarbs;mealFat" + "\n");
            for (MensaData meal : MensaData.selectedMealList) {
                // Write each meal's data to a line in the file
                writer.write(meal.getMealDate() + ";" + meal.getMealName() + ";" + meal.getMealPrice() + ";"
                        + meal.getMealType().toString() + ";" + meal.getMealKcal() + ";" + meal.getMealProteins()
                        + ";" + meal.getMealCarbs() + ";" + meal.getMealFat() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the GUI for the meal plan {@code MensaGUIPlan}.
     */
    public static void openMensaGUIPlan() {
        closeCurrentFrame(); // Close the currently open frame, if any
        MensaGUIPlan frame = new MensaGUIPlan(); // Create a new instance of the GUI plan
        frame.setTitle("Mensa Food Tracker");
        frame.setSize(16 * 60, 9 * 60);// Sets screen size
        frame.setMinimumSize(new Dimension(16 * 40, 9 * 40)); // Sets minimum screen size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        currentFrame = frame; // Set the current frame to the newly created frame
        menuBarPressed(frame); // Add event listeners for menu bar actions
    }

    /**
     * Opens the GUI for meal history {@code MensaGUIHistory}.
     */
    public static void openMensaGUIHistory() {
        closeCurrentFrame(); // Close the currently open frame, if any
        MensaGUIHistory frame = new MensaGUIHistory(); // Create a new instance of the GUI historie
        frame.setTitle("Mensa Food Tracker");
        frame.setSize(16 * 60, 9 * 60);// Sets screen size
        frame.setMinimumSize(new Dimension(16 * 40, 9 * 40)); // Sets minimum screen size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        currentFrame = frame; // Set the current frame to the newly created frame
        menuBarPressed(frame); // Add event listeners for menu bar actions
    }

    /**
     * Opens the GUI for meal overview {@code MensaGUIOverview}.
     */
    public static void openMensaGUIOverview() {
        closeCurrentFrame(); // Close the currently open frame, if any
        MensaGUIOverview frame = new MensaGUIOverview(); // Create a new instance of the GUI uebersicht
        frame.setTitle("Mensa Food Tracker");
        frame.setSize(16 * 60, 9 * 60);// Sets screen size
        frame.setMinimumSize(new Dimension(16 * 40, 9 * 40)); // Sets minimum screen size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        currentFrame = frame; // Set the current frame to the newly created frame
        menuBarPressed(frame); // Add event listeners for menu bar actions
    }

    /**
     * Adds event listeners to the menu bar actions.
     *
     * @param instance The instance of the {@code MensaGUI}.
     */
    public static void menuBarPressed(MensaGUI instance) {
        instance.menuEssensPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeContent(instance.getContentPane()); // Remove the current content from the container
                openMensaGUIPlan(); // Open the GUI plan
            }
        });

        instance.menuHistorie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeContent(instance.getContentPane()); // Remove the current content from the container
                openMensaGUIHistory(); // Open the GUI historie
            }
        });

        instance.menuUebersicht.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeContent(instance.getContentPane()); // Remove the current content from the container
                openMensaGUIOverview(); // Open the GUI uebersicht
            }
        });
    }

    /**
     * Removes all components from a container.
     *
     * @param container The container to remove components from.
     */
    public static void removeContent(Container container) {
        container.removeAll(); // Remove all components from the container
        container.revalidate(); // Revalidate the container to reflect the changes
        container.repaint(); // Repaint the container to update the display
    }

    /**
     * Closes the current frame, if any.
     */
    public static void closeCurrentFrame() {
        if (currentFrame != null) {
            currentFrame.dispose(); // Dispose the current frame to close it
        }
    }

    /**
     * Registers a shutdown hook to write meal data to a file upon program
     * termination.
     */
    public static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                writeFileData(); // Write meal data to a file
            }
        });
    }
}
