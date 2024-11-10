package pack;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;

/** 
 * The {@code MensaGUIOverview} class represents the graphical user interface
 * (GUI) for the meal overview
 * in the Mensa Food Tracker application. It extends the {@code MensaGUI} class
 * and displays cumulative
 * information based on the meal history.
 * 
 * @author Philipp Schmidt (uggro)
 * @since version 1.0
 */
public class MensaGUIOverview extends MensaGUI {

    private Container c;

    private JPanel panelHeader = new JPanel();
    private JPanel panelContent = new JPanel();

    private JLabel headerLabel = new JLabel("Kummulierte Angaben ausgehend von der Historie");
    private JLabel noDataJLabel;

    private JTable table;
    private static DefaultTableModel tableModel;

    /**
     * Constructs a new instance of the {@code MensaGUIOverview} class.
     * Sets up the GUI components and layout.
     */
    public MensaGUIOverview() {

        c = this.getContentPane();

        // Set the layout for the container
        c.setLayout(new BorderLayout());

        // Add the header panel to the top of the container
        c.add(panelHeader, BorderLayout.NORTH);

        // Add the content panel to the center of the container
        c.add(panelContent, BorderLayout.CENTER);

        // Add the header label to the header panel
        panelHeader.add(headerLabel);

        // Create a label to display a message when there is no meal data
        noDataJLabel = new JLabel("Es befinden sich keinerlei Speisen in Ihrer Historie");
        noDataJLabel.setVisible(false);

        // Add the noDataJLabel to the content panel
        panelContent.add(noDataJLabel);

        // Create a table with a default table model
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Add columns to the table model
        tableModel.addColumn("Kalorien");
        tableModel.addColumn("Proteine");
        tableModel.addColumn("Kohlenhydrate");
        tableModel.addColumn("Fett");
        tableModel.addColumn("Gesamtkosten");
        tableModel.addColumn("Veggie-Anteil");

        // Create a JTable with the table model and add it to the content panel
        table = new JTable(tableModel);
        panelContent.add(new JScrollPane(table));

        // Fill the table with meal data
        preTableFill();
    }

    /**
     * Fills the table with cumulative meal information based on the meal history.
     */
    private static void preTableFill() {
        float mealKcal = 0;
        float mealProteins = 0;
        float mealCarbs = 0;
        float mealFats = 0;
        float mealPrices = 0;
        int mealVeggieCounter = 0;
        float mealVeggiePercentage;
        String mealType;

        // Iterate through the selectedMealList and calculate cumulative meal
        // information
        for (int i = 0; i < MensaData.selectedMealList.size(); i++) {
            MensaData x = MensaData.selectedMealList.get(i);
            mealKcal = mealKcal + x.getMealKcal();
            mealProteins = mealProteins + x.getMealProteins();
            mealCarbs = mealCarbs + x.getMealCarbs();
            mealFats = mealFats + x.getMealFat();
            mealPrices = mealPrices + x.getMealPrice();
            mealType = x.getMealType().toString();
            if (mealType.equals("VEGAN") || mealType.equals("VEGETARIAN")) {
                mealVeggieCounter++;
            }
        }

        // Calculate the percentage of veggie meals
        mealVeggiePercentage = (float) mealVeggieCounter / MensaData.selectedMealList.size();

        // Add a new row to the table with the cumulative meal information and formatted numbers
        tableModel.addRow(new Object[] { mealKcal, mealProteins + " g", mealCarbs + " g", mealFats + " g",
                MensaData.df.format(mealPrices) + " €", MensaData.df.format(mealVeggiePercentage) + " %" });
    }

    // Getters and setters for the class properties

    public Container getC() {
        return c;
    }

    public void setC(Container c) {
        this.c = c;
    }

    public JPanel getPanelHeader() {
        return panelHeader;
    }

    public void setPanelHeader(JPanel panelHeader) {
        this.panelHeader = panelHeader;
    }

    public JPanel getPanelContent() {
        return panelContent;
    }

    public void setPanelContent(JPanel panelContent) {
        this.panelContent = panelContent;
    }

    public JLabel getHeaderLabel() {
        return headerLabel;
    }

    public void setHeaderLabel(JLabel headerLabel) {
        this.headerLabel = headerLabel;
    }

    public JLabel getNoDataJLabel() {
        return noDataJLabel;
    }

    public void setNoDataJLabel(JLabel noDataJLabel) {
        this.noDataJLabel = noDataJLabel;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public static DefaultTableModel getTableModel() {
        return tableModel;
    }

    public static void setTableModel(DefaultTableModel tableModel) {
        MensaGUIOverview.tableModel = tableModel;
    }

}
