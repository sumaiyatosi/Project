package student_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AttendanceInfo extends JFrame {
    JTable table;
    DefaultTableModel model;
    String role, userEmail;

    public AttendanceInfo(String role, String userEmail) {
        this.role = role;
        this.userEmail = userEmail;

        setTitle("Information");
        setSize(1000, 600);
        setLocation(240, 80);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Font headingFont = new Font("Arial", Font.BOLD, 20);
        Font tableFont = new Font("Arial", Font.BOLD, 18);
        Font buttonFont = new Font("Arial", Font.BOLD, 23);

        JLabel heading = new JLabel("Attendance Info", JLabel.CENTER);
        heading.setFont(headingFont);
        heading.setForeground(Color.black);
        heading.setBackground(Color.white);
        heading.setOpaque(true);
        heading.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(heading, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Student ID", "Student Name", "Total Present"});

        table = new JTable(model);
        table.setFont(tableFont);
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.getTableHeader().setBackground(Color.white);
        table.setSelectionBackground(new Color(84, 130, 9));
        table.setGridColor(Color.yellow);
        table.setBackground(Color.black);
        table.setForeground(Color.white);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(245, 245, 245));

        JButton backBtn = new JButton("Cancel");
        backBtn.setFont(buttonFont);
        backBtn.setBackground(Color.black);
        backBtn.setForeground(Color.white);
        backBtn.setFocusPainted(false);
        backBtn.setPreferredSize(new Dimension(120, 40));
        bottomPanel.add(backBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        ImageIcon windowIcon = new ImageIcon(ClassLoader.getSystemResource("image/icon.png"));
        setIconImage(windowIcon.getImage());

        backBtn.addActionListener(e -> {
            dispose();
            new Attendance(role, userEmail).setVisible(true);
        });
        loadSemesterData();
        setVisible(true);
    }

    private void loadSemesterData() {
        String semesterStart = "2023-01-01";
        String semesterEnd = "2060-12-30";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_management_system", "root", "");

            String sql = "SELECT Student_ID, Student_Name, COUNT(*) AS present_count " +
                    "FROM Attendance " +
                    "WHERE Date BETWEEN ? AND ? AND Status = 'Present' " +
                    "GROUP BY Student_ID, Student_Name";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, semesterStart);
            stmt.setString(2, semesterEnd);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String studentId = rs.getString("Student_ID");
                String studentName = rs.getString("Student_Name");
                int presentDays = rs.getInt("present_count");

                model.addRow(new Object[]{studentId, studentName, presentDays});
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error" + e.getMessage());
        }
    }
    public static void main(String[] args) {
        new AttendanceInfo("Teacher", "teacher@gmail.com");
    }
}
