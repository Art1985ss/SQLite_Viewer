package viewer;

import javax.swing.*;
import java.awt.*;

public class SQLiteViewer extends JFrame {

    public SQLiteViewer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 900);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("SQLite Viewer");

        this.getContentPane().add(new GUI(), BorderLayout.NORTH);

        this.setVisible(true);
    }
}
