package pack;

import javax.swing.*;

/** 
 * The {@code MensaGUI} class represents a graphical user interface for a Mensa
 * application.
 * It extends the JFrame class to create a window with a menu bar and menu
 * items.
 * 
 * @author Philipp Schmidt (uggro)
 * @since version 1.0
 */
public class MensaGUI extends JFrame {

    /**
     * The menu bar of the GUI.
     */
    public JMenuBar menuBar = new JMenuBar();

    /**
     * The "Funktionen" menu in the menu bar.
     */
    public JMenu menuFunktionen = new JMenu("Funktionen");

    /**
     * The "Essensplan" menu item in the "Funktionen" menu.
     */
    public JMenuItem menuEssensPlan = new JMenuItem("Essensplan");

    /**
     * The "Historie" menu item in the "Funktionen" menu.
     */
    public JMenuItem menuHistorie = new JMenuItem("Historie");

    /**
     * The "Übersicht" menu item in the "Funktionen" menu.
     */
    public JMenuItem menuUebersicht = new JMenuItem("Übersicht");

    /**
     * Constructs a new instance of the MensaGUI class.
     * Sets up the GUI components, including the menu bar and menu items.
     */
    public MensaGUI() {
        this.setJMenuBar(menuBar);
        menuBar.add(menuFunktionen);
        menuFunktionen.add(menuEssensPlan);
        menuFunktionen.add(menuHistorie);
        menuFunktionen.add(menuUebersicht);
    }
}
