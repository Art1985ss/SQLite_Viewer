package viewer;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;

public class GUI extends JPanel {
    private SQLiteService sqLiteService;

    public GUI() {

        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        final Container container = this;
        container.setLayout(layout);

        final JTextField fileNameField = new JTextField();
        fileNameField.setName("FileNameTextField");
        constraints.weightx = 0.8;
        constraints.gridx = 0;
        constraints.gridy = 0;
        container.add(fileNameField, constraints);

        final JButton openFileBtn = new JButton("Open");
        openFileBtn.setName("OpenFileButton");
        constraints.weightx = 0.2;
        constraints.gridx = 1;
        constraints.gridy = 0;
        container.add(openFileBtn, constraints);

        final JComboBox<String> tables = new JComboBox<>();
        tables.setName("TablesComboBox");
        constraints.gridwidth = 2;
        constraints.weightx = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        container.add(tables, constraints);

        final JTextArea queryArea = new JTextArea();
        queryArea.setName("QueryTextArea");
        queryArea.setEnabled(false);
        constraints.gridwidth = 1;
        constraints.weightx = 0.8;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.ipady = 100;
        container.add(queryArea, constraints);

        final JButton executeQueryBtn = new JButton("Execute");
        executeQueryBtn.setName("ExecuteQueryButton");
        executeQueryBtn.setEnabled(false);
        constraints.weightx = 0.2;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.ipady = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        container.add(executeQueryBtn, constraints);

        final JTable table = new JTable();
        table.setName("Table");
        constraints.gridwidth = 2;
        constraints.weightx = 1;
        constraints.gridx = 0;
        constraints.gridy = 3;
        container.add(new JScrollPane(table), constraints);
        this.setVisible(true);


        openFileBtn.addActionListener(action -> {
            sqLiteService = new SQLiteService(fileNameField.getText());
            List<String> tableItems = sqLiteService.getTables();
            tables.removeAllItems();
            boolean enable = !tableItems.isEmpty();
            queryArea.setEnabled(enable);
            tables.setEnabled(enable);
            executeQueryBtn.setEnabled(enable);
            tableItems.forEach(tables::addItem);
        });

        tables.addItemListener(selected -> {
            String tableName = (String) selected.getItem();
            queryArea.setText("SELECT * FROM " + tableName + ";");
        });

        executeQueryBtn.addActionListener(action -> {
            if (sqLiteService == null) {
                return;
            }
            TableModel tableModel = sqLiteService.executeQuery(queryArea.getText());
            if (tableModel == null) {
                return;
            }
            table.setModel(tableModel);
        });
    }
}
