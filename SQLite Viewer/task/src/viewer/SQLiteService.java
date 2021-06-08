package viewer;

import org.sqlite.SQLiteDataSource;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteService {
    private final SQLiteDataSource dataSource = new SQLiteDataSource();

    public SQLiteService(String url) {
        dataSource.setUrl("jdbc:sqlite:" + url);
        if (!new File(url).exists()) {
            JOptionPane.showMessageDialog(new Frame(), "File doesn't exist!", "Exception", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<String> getTables() {
        List<String> tables = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tables.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(new Frame(), e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
        }
        return tables;
    }

    public QueryResultTableModel executeQuery(String query) {
        String[] columns = null;
        List<String[]> data = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            columns = new String[columnCount];
            for (int i = 0; i < columns.length; i++) {
                columns[i] = metaData.getColumnLabel(i + 1);
            }
            while (resultSet.next()) {
                String[] row = new String[columnCount];
                for (int i = 0; i < columns.length; i++) {
                    row[i] = resultSet.getString(columns[i]);
                }
                data.add(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(new Frame(), e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
        }
        if (data.isEmpty()) {
            return null;
        }
        return new QueryResultTableModel(columns, data.toArray(String[][]::new));
    }
}
