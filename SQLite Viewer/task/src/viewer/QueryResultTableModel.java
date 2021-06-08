package viewer;

import javax.swing.table.AbstractTableModel;

public class QueryResultTableModel extends AbstractTableModel {
    private final String[] columns;
    private final String[][] data;

    public QueryResultTableModel(String[] columns, String[][] data) {
        this.columns = columns;
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
