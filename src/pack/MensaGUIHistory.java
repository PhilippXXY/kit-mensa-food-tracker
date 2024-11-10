package pack;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/** 
 * The {@code MensaGUIHistory} class extends {@code MensaGUI} and represents
 * the GUI for displaying the selected meal history.
 * It includes a table to show the selected meals with their details and allows
 * users to remove entries by clicking on them.
 * 
 * @author Philipp Schmidt (uggro)
 * @since version 1.0
 */
public class MensaGUIHistory extends MensaGUI {

    // GUI components
    private Container c;
    private JPanel panelHeader = new JPanel();
    private JPanel panelContent = new JPanel();
    private JPanel panelFooter = new JPanel();

    private JLabel headerLabel = new JLabel("Ausgewählte Speisen Übersicht");
    private JLabel noDataJLabel;

    private JTable table;
    private DefaultTableModel tableModel;

    private JLabel explanationLabel = new JLabel("Sie können durch Klicken Einträge entfernen");

    /**
     * Constructs a new {@code MensaGUIHistory} object.
     * Initializes the GUI components and fills the table with data.
     */
    public MensaGUIHistory() {
        c = this.getContentPane();

        // Add panels to the container
        c.add(panelHeader, BorderLayout.NORTH);
        c.add(panelContent, BorderLayout.CENTER);
        c.add(panelFooter, BorderLayout.SOUTH);

        // Add header label to the header panel
        panelHeader.add(headerLabel);

        // Create a label for displaying "no data" message and hide it initially
        noDataJLabel = new JLabel("Es wurden keine Speisen der Historie hinzugefügt");
        noDataJLabel.setVisible(false);
        panelContent.add(noDataJLabel);

        // Create a table model and set it to the JTable
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Datum");
        tableModel.addColumn("Gericht");
        tableModel.addColumn("Preis");
        tableModel.addColumn("Kalorien");
        tableModel.addColumn("Proteine");
        tableModel.addColumn("Kohlenhydrate");
        table = new JTable(tableModel);
        panelContent.add(new JScrollPane(table));

        // Add a selection listener to the table
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                onTableSelectionChanged();
            }
        });

        // Add the explanation label to the footer panel
        panelFooter.add(explanationLabel);

        // Fill the table with data
        tableFill();
    }

    /**
     * Fills the table with selected meal data.
     * If there are selected meals, it displays the table and hides the "no data"
     * message.
     * If there are no selected meals, it hides the table and displays the "no data"
     * message.
     */
    public void tableFill() {
        // Clear the table
        clearTable();

        if (!MensaData.selectedMealList.isEmpty()) {
            // If there are selected meals, display the table and hide the "no data" message
            table.setVisible(true);
            noDataJLabel.setVisible(false);

            // Sort the selected meal list by meal date
            Collections.sort(MensaData.selectedMealList, new Comparator<MensaData>() {
                @Override
                public int compare(MensaData meal1, MensaData meal2) {
                    return meal1.getMealDate().compareTo(meal2.getMealDate());
                }
            });

            // Add each meal data to the table
            for (int i = 0; i < MensaData.selectedMealList.size(); i++) {
                MensaData x = MensaData.selectedMealList.get(i);
                String mealDate = x.getMealDate();
                String mealName = x.getMealName();
                float mealPrice = x.getMealPrice();
                float mealKcal = x.getMealKcal();
                float mealProteins = x.getMealProteins();
                float mealCarbs = x.getMealCarbs();

                tableModel.addRow(new Object[] { mealDate, mealName, mealPrice + " €", mealKcal + " kcal",
                        mealProteins + " g", mealCarbs + " g" });
            }
        } else {
            // If there are no selected meals, hide the table and display the "no data"
            // message
            table.setVisible(false);
            noDataJLabel.setVisible(true);
        }
    }

    /**
     * Handles the selection change event of the table.
     * Removes the selected meal from the selected meal list and updates the table.
     */
    private void onTableSelectionChanged() {
        if (!table.getSelectionModel().getValueIsAdjusting()) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Get the selected meal name and date
                String mealName = tableModel.getValueAt(selectedRow, 1).toString();
                String mealDate = tableModel.getValueAt(selectedRow, 0).toString();

                System.out.println("Ausgewählt: " + mealName);

                // Remove the selected meal from the selected meal list
                for (int i = 0; i < MensaData.selectedMealList.size(); i++) {
                    MensaData x = MensaData.selectedMealList.get(i);
                    if (mealName.equals(x.getMealName()) && mealDate.equals(x.getMealDate())) {
                        MensaData.selectedMealList.remove(i);
                        break;
                    }
                }
                // Update the table
                tableFill();
            }
        }
    }

    /**
     * Clears the table by removing all rows.
     */
    public void clearTable() {
        tableModel.setRowCount(0);
    }

    // Getters and setters for GUI components

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

    public JPanel getPanelFooter() {
        return panelFooter;
    }

    public void setPanelFooter(JPanel panelFooter) {
        this.panelFooter = panelFooter;
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

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public JLabel getExplanationLabel() {
        return explanationLabel;
    }

    public void setExplanationLabel(JLabel explanationLabel) {
        this.explanationLabel = explanationLabel;
    }
}
