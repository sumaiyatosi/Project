package student_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class RegistrationInfo extends JFrame {
    JTable table;
    DefaultTableModel model;
    JButton cancelBtn;
    String role, userEmail;

    public RegistrationInfo(String role, String userEmail) {
        this.role = role;
        this.userEmail = userEmail;

        setTitle("Course Registration");
        setSize(1300, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Font headerFont = new Font("Segoe UI", Font.BOLD, 14);
        Font tableFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 20);

        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("image/program.jpg"));
        Image bgImage = bgIcon.getImage().getScaledInstance(1300, 600, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        setContentPane(background);
        background.setLayout(new BorderLayout());

        model = new DefaultTableModel();
        table = new JTable(model);
        table.setFont(tableFont);
        table.setRowHeight(25);
        table.getTableHeader().setFont(headerFont);
        table.getTableHeader().setBackground(Color.GRAY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(Color.GRAY);
        table.setForeground(Color.BLACK);
        table.setOpaque(false);

        model.setColumnIdentifiers(new String[] {
            "ID", "Name", "Program", "Dept", "Session", "Semester",
            "Course", "Retake", "Advisor", "Duration", "Fee", "GPA",
            "Waiver", "Offer", "Fine", "Date", "Father", "Phone", "Email", "Address", "Paid"
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        background.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        buttonPanel.setOpaque(false);

        cancelBtn = new JButton("Cancel");
        styleButton(cancelBtn, buttonFont);
        buttonPanel.add(cancelBtn);

        background.add(buttonPanel, BorderLayout.SOUTH);

        loadCourseRegistrationData();

        ImageIcon windowIcon = new ImageIcon(ClassLoader.getSystemResource("image/icon.png"));
        setIconImage(windowIcon.getImage());

        cancelBtn.addActionListener(e -> {
            Home.getInstance(role, userEmail).setVisible(true);
            dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton button, Font font) {
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setPreferredSize(new Dimension(120, 40));
    }

    private void loadCourseRegistrationData() {
        model.setRowCount(0); 
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_management_system", "root", "");

            String query = "SELECT * FROM Course_Registration"; 
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("Student_ID"),
                    rs.getString("Student_Name"),
                    rs.getString("Program"),
                    rs.getString("Department"),
                    rs.getString("Session"),
                    rs.getString("Semester"),
                    rs.getString("Course_Name"),
                    rs.getString("Retake"),
                    rs.getString("Course_Advisor"),
                    rs.getString("Duration"),
                    rs.getDouble("Tuition_Fee"),
                    rs.getDouble("GPA"),
                    rs.getString("Waiver"),
                    rs.getString("Offer"),
                    rs.getString("Fine"),
                    rs.getDate("Date"),
                    rs.getString("Fathers_Name"),
                    rs.getString("Phone"),
                    rs.getString("Email"),
                    rs.getString("Address"),
                    rs.getString("Paid") 
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
        new RegistrationInfo("Admin", "admin@gmail.com");
    }
}
