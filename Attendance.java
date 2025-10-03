package student_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class Attendance extends JFrame {
    JTable table;
    DefaultTableModel model;
    JTextField searchField;
    JComboBox<String> searchType;
    String role, userEmail;
    JButton searchBtn, reloadBtn, semesterBtn, insertBtn, cancelBtn;

    public Attendance(String role, String userEmail) {
        this.role = role;
        this.userEmail = userEmail;

        setTitle("Attendance");
        setSize(1000, 600);
        setLocation(240, 80);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Font headingFont = new Font("Arial", Font.BOLD, 25);
        Font normalFont = new Font("Arial", Font.PLAIN, 17);
        Font buttonFont = new Font("Arial", Font.BOLD, 15);

        JLabel heading = new JLabel("Attendance", JLabel.CENTER);
        heading.setFont(headingFont);
        heading.setForeground(Color.BLACK);
        heading.setBounds(0, 10, 1000, 40);
        add(heading);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Student ID", "Student Name", "Date", "Status"
        });

        table = new JTable(model);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Alice", Font.BOLD, 16));
        header.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);
        table.setFont(new Font("Alice", Font.BOLD, 16));
        table.setRowHeight(28);
        table.setBackground(new Color(84, 130, 9));
        table.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 60, 900, 400);
        add(scrollPane);

        searchType = new JComboBox<>(new String[]{"ID", "Date"});
        searchType.setFont(normalFont);
        searchType.setBackground(Color.BLACK);
        searchType.setForeground(Color.WHITE);
        searchType.setBounds(100, 478, 120, 30);
        add(searchType);

        searchField = new JTextField();
        searchField.setFont(normalFont);
        searchField.setBounds(215, 478, 150, 30);
        add(searchField);

        searchBtn = createStyledButton("Search", buttonFont);
        searchBtn.setBounds(375, 478, 100, 40);
        add(searchBtn);

        reloadBtn = createStyledButton("Reload", buttonFont);
        reloadBtn.setBounds(485, 478, 100, 40);
        add(reloadBtn);

        semesterBtn = createStyledButton("Info", buttonFont);
        semesterBtn.setBounds(595, 478, 100, 40);
        add(semesterBtn);

        insertBtn = createStyledButton("Sheet", buttonFont);
        insertBtn.setBounds(705, 478, 100, 40);
        add(insertBtn);

        cancelBtn = createStyledButton("Cancel", buttonFont);
        cancelBtn.setBounds(815, 478, 100, 40);
        add(cancelBtn);

        loadAttendanceData();

        ImageIcon windowIcon = new ImageIcon(ClassLoader.getSystemResource("image/icon.png"));
        setIconImage(windowIcon.getImage());

        searchBtn.addActionListener(e -> searchData());

        reloadBtn.addActionListener(e -> {
            searchField.setText("");
            model.setRowCount(0);
            loadAttendanceData();
        });

        semesterBtn.addActionListener(e -> {
            dispose();
            new AttendanceInfo(role, userEmail).setVisible(true);
        });

        insertBtn.addActionListener(e -> {
            dispose();
            new AttendanceSheet(role, userEmail).setVisible(true);
        });

        cancelBtn.addActionListener(e -> {
            dispose();
            Home(role, userEmail).setVisible(true);
        });

        setVisible(true);
    }
    private JComponent Home(String role2, String userEmail2) {
      throw new UnsupportedOperationException("'Home'");
    }

    private JButton createStyledButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setBackground(new Color(84, 130, 9));
        button.setForeground(Color.BLACK);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return button;
    }

    private void loadAttendanceData() {
        model.setRowCount(0);

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "root", "");
            String sql = "SELECT * FROM Attendance ORDER BY Date DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("Student_ID"),
                        rs.getString("Student_Name"),
                        rs.getString("Date"),
                        rs.getString("Status")
                });
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error " + e.getMessage());
        }
    }

    private void searchData() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Keyword");
            return;
        }

        model.setRowCount(0);

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "root", "");
            String sql;

            if (searchType.getSelectedItem().equals("Date")) {
                sql = "SELECT * FROM Attendance WHERE Date = ?";
            } else {
                sql = "SELECT * FROM Attendance WHERE Student_ID LIKE ?";
                keyword = "%" + keyword + "%";
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, keyword);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("Student_ID"),
                        rs.getString("Student_Name"),
                        rs.getString("Date"),
                        rs.getString("Status")
                });
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        new Attendance("Teacher", "teacher@gmail.com");
    }
}
