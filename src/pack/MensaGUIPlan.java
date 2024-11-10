package pack;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The {@code MensaGUIPlan} class represents a graphical user interface for
 * displaying a
 * dining plan.
 * It extends the {@code MensaGUI} class, which serves as the base class for the
 * GUI.
 * 
 * @author Philipp Schmidt (uggro)
 * @since version 1.0
 */
public class MensaGUIPlan extends MensaGUI {

    private Container c;

    private JPanel panelHeader = new JPanel();
    private JPanel panelFilter = new JPanel();
    private JPanel panelContent = new JPanel();
    private JPanel panelFooter = new JPanel();

    private JLabel headerLabel = new JLabel("Essensplan Übersicht");

    private JComboBox<String> dateComboBox = new JComboBox<String>();
    private JLabel noDataJLabel;

    private JTable table;
    private DefaultTableModel tableModel;

    private JLabel explanationLabel = new JLabel("Sie können durch Klicken Speisen zu ihrer Historie hinzufügen");

    /**
     * Constructs a new instance of {@code MensaGUIPlan}.
     * It initializes the GUI components and sets up event listeners.
     */
    public MensaGUIPlan() {
        // Get the content pane of the GUI
        c = this.getContentPane();

        // Set up the layout and add panels to the content pane
        c.add(panelHeader, BorderLayout.NORTH);
        c.add(panelFilter, BorderLayout.WEST);
        c.add(panelContent, BorderLayout.CENTER);
        c.add(panelFooter, BorderLayout.SOUTH);

        // Add the header label to the header panel
        panelHeader.add(headerLabel);

        // Set up the layout for the filter panel
        panelFilter.setLayout(new BoxLayout(panelFilter, BoxLayout.Y_AXIS));
        panelFilter.add(dateComboBox);

        // Set the maximum size of the dateComboBox
        dateComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, dateComboBox.getPreferredSize().height));

        // Create a label for displaying a message when no data is available for the
        // selected date
        noDataJLabel = new JLabel("Mensa geschlossen ");
        noDataJLabel.setVisible(false);
        panelFilter.add(noDataJLabel);

        // Create the table and table model for displaying the meal data
        tableModel = new DefaultTableModel() {
            // Override the isCellEditable() method to make the table cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Gericht");
        tableModel.addColumn("Preis");
        table = new JTable(tableModel);

        // Add the table to a scroll pane and add the scroll pane to the content panel
        panelContent.add(new JScrollPane(table));

        // Add the explanation label to the footer panel
        panelFooter.add(explanationLabel);

        // Populate the dateComboBox with dates and set the current date as the selected
        // item
        datesIntoComboBox(dateComboBox);

        // Add an action listener to the dateComboBox to handle date selection changes
        dateComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDateComboBoxSelectionChanged();
            }
        });

        // Add a list selection listener to the table to handle row selection changes
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                onTableSelectionChanged();
            }
        });

        // Fetch the meal data from the library
        MensaData.fetchLibrary();

        // Pre-fill the table with meal data for the current date
        preTableFill();
    }

    /**
     * Fills the dateComboBox with dates from the current date to the end date.
     * Sets the current date as the selected item.
     *
     * @param comboBox the JComboBox to populate with dates
     */
    public static void datesIntoComboBox(JComboBox<String> comboBox) {
        LocalDate currentDate = MensaData.currentDate;
        LocalDate endDate = MensaData.endDate;
        DateTimeFormatter formatter = MensaData.formatter;

        // Add dates to the combo box from the current date to the end date
        while (currentDate.isBefore(endDate)) {
            String formattedDate = currentDate.format(formatter);
            comboBox.addItem(formattedDate);
            currentDate = currentDate.plusDays(1);
        }

        // Set the current date as the selected item
        comboBox.setSelectedItem(currentDate.format(formatter));
    }

    /**
     * Handles the selection change event of the dateComboBox.
     * Updates the table with meal data for the selected date.
     */
    private void onDateComboBoxSelectionChanged() {
        String selectedDate = (String) dateComboBox.getSelectedItem();

        // Clear the table model
        tableModel.setRowCount(0);

        int j = 0;

        // Iterate over the meal data and add rows to the table model for the selected
        // date
        for (int i = 0; i < MensaData.mealDataList.size(); i++) {
            MensaData x = MensaData.mealDataList.get(i);
            if (x.getMealDate().equals(selectedDate)) {
                String mealName = x.getMealName();
                String mealPrice = String.valueOf(x.getMealPrice());
                tableModel.addRow(new Object[] { mealName, mealPrice + " €" });
                j++;
            }
        }

        // Show or hide the table and the noDataJLabel based on whether data is
        // available for the selected date
        if (j == 0 && !noDataJLabel.isVisible()) {
            table.setVisible(false);
            noDataJLabel.setVisible(true);
        } else if (j == 0 && noDataJLabel.isVisible()) {
            // Do nothing
        } else {
            table.setVisible(true);
            noDataJLabel.setVisible(false);
        }
    }

    /**
     * Pre-fills the table with meal data for the current date.
     * If no data is available, it hides the table and shows the noDataJLabel.
     */
    public void preTableFill() {
        String currentDate = MensaData.currentDate.format(MensaData.formatter);

        int j = 0;

        // Iterate over the meal data and add rows to the table model for the current
        // date
        for (int i = 0; i < MensaData.mealDataList.size(); i++) {
            MensaData x = MensaData.mealDataList.get(i);
            if (x.getMealDate().equals(currentDate)) {
                String mealName = x.getMealName();
                String mealPrice = String.valueOf(x.getMealPrice());
                tableModel.addRow(new Object[] { mealName, mealPrice + " €" });
                j++;
            }
        }

        // Show or hide the table and the noDataJLabel based on whether data is
        // available for the current date
        if (j == 0) {
            table.setVisible(false);
            noDataJLabel.setVisible(true);
        }
    }

    /**
     * Handles the selection change event of the table.
     * Adds the selected meal to the selectedMealList.
     */
    private void onTableSelectionChanged() {
        if (!table.getSelectionModel().getValueIsAdjusting()) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String mealName = tableModel.getValueAt(selectedRow, 0).toString();
                String mealDate = (String) dateComboBox.getSelectedItem();

                System.out.println("Ausgewählt: " + mealName);

                // Iterate over the meal data and add the selected meal to the selectedMealList
                for (int i = 0; i < MensaData.mealDataList.size(); i++) {
                    MensaData x = MensaData.mealDataList.get(i);
                    if (mealName.equals(x.getMealName()) && mealDate.equals(x.getMealDate())) {
                        MensaData.selectedMealList.add(x);
                        break;
                    }
                }
            }
        }
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

    public JPanel getPanelFilter() {
        return panelFilter;
    }

    public void setPanelFilter(JPanel panelFilter) {
        this.panelFilter = panelFilter;
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

    public JComboBox<String> getDateComboBox() {
        return dateComboBox;
    }

    public void setDateComboBox(JComboBox<String> dateComboBox) {
        this.dateComboBox = dateComboBox;
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
