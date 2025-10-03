package student_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Result extends JFrame {
    JTable table;
    DefaultTableModel model;
    JButton cancelBtn, searchBtn;
    JTextField idField;
    String role;
    String userEmail;

    public Result(String role, String userEmail) {
        this.role = role;
        this.userEmail = userEmail;

        setTitle("Result");
        setSize(1200, 700);
        setLocation(90, 30);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        Font headerFont = new Font("Segoe UI", Font.BOLD, 15);
        Font tableFont = new Font("Segoe UI", Font.BOLD, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 18);

        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("image/bg_img2.jpg"));
        Image bgImage = bgIcon.getImage().getScaledInstance(1200, 700, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setBounds(0, 0, 1200, 700);
        setContentPane(background);
        background.setLayout(null);

        JLabel idLabel = new JLabel("Student ID");
        idLabel.setForeground(Color.WHITE);
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        idLabel.setBounds(50, 20, 100, 30);
        background.add(idLabel);

        idField = new JTextField();
        idField.setBounds(160, 20, 150, 30);
        background.add(idField);

        searchBtn = new JButton("Search");
        styleButton(searchBtn, buttonFont);
        searchBtn.setBounds(320, 20, 120, 35);
        background.add(searchBtn);

        model = new DefaultTableModel();
        table = new JTable(model);
        table.setFont(tableFont);
        table.setRowHeight(30);
        table.getTableHeader().setFont(headerFont);
        table.getTableHeader().setBackground (new Color(84, 130, 9));
        table.getTableHeader().setForeground(Color.black);
        table.setGridColor(Color.green);
        table.setForeground(Color.BLACK);

        model.setColumnIdentifiers(new String[]{
                "ID", "Name", "Semester", "Department", "Course",
                "Marks", "CGPA", "Attendance", "Assignment", "Tutorial", "Mid", "Final"
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40, 70, 1100, 500);
        background.add(scrollPane);

        cancelBtn = new JButton("Cancel");
        styleButton(cancelBtn, buttonFont);
        cancelBtn.setBounds(500, 590, 120, 40);
        background.add(cancelBtn);

        ImageIcon windowIcon = new ImageIcon(ClassLoader.getSystemResource("image/icon.png"));
        setIconImage(windowIcon.getImage());

        cancelBtn.addActionListener(e -> {
            Home.getInstance(role, userEmail).setVisible(true);
            dispose();
        });

        searchBtn.addActionListener(e -> loadResultData());

        setVisible(true);
    }

    private void styleButton(JButton button, Font font) {
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 0, 0));
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    private void loadResultData() {
        model.setRowCount(0);

        String id = idField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter ID");
            return;
        }
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_management_system", "root", "");

            String query = "SELECT * FROM Result WHERE Student_ID=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, id);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("Student_ID"),
                        rs.getString("Student_Name"),
                        rs.getString("Semester"),
                        rs.getString("Department"),
                        rs.getString("Course_Name"),
                        rs.getInt("Marks"),
                        rs.getDouble("CGPA"),
                        rs.getInt("Attendance"),
                        rs.getInt("Assignment"),
                        rs.getInt("Tutorial"),
                        rs.getInt("Mid"),
                        rs.getInt("Final")
                });
            }
            rs.close();
            pst.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        new Result("Student", "student@gmail.com");
    }
}
