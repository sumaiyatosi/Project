package student_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Routine extends JFrame {
    JTable table;
    DefaultTableModel model;
    JButton cancelBtn, searchBtn;
    JTextField semesterField, deptField;
    String role, userEmail;

    public Routine(String role, String userEmail) 
    {
        this.role = role;
        this.userEmail = userEmail;
        
        setTitle("Routine");
        setSize(1300, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Font headerFont = new Font("Segoe UI", Font.BOLD, 16);
        Font tableFont = new Font("Segoe UI", Font.BOLD, 11);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 18);

        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("image/bg_img3.jpg"));
        Image bgImage = bgIcon.getImage().getScaledInstance(1300, 650, Image.SCALE_SMOOTH);
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
        table.setGridColor(Color.white);
        table.setForeground(Color.BLACK);
        table.setOpaque(false);

        model.setColumnIdentifiers(new String[]
        {
            "Day", "Session", "Semester", "Department", "Teacher", "Course Name", "Schedule", "Room"
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setOpaque(false);

        JLabel semesterLabel = new JLabel("Semester");
        semesterLabel.setForeground(Color.WHITE);
        semesterField = new JTextField();
        semesterField.setPreferredSize(new Dimension(100, 35)); 

        JLabel deptLabel = new JLabel("Department");
        deptLabel.setForeground(Color.WHITE);
        deptField = new JTextField();
        deptField.setPreferredSize(new Dimension(100, 35)); 

        searchPanel.add(semesterLabel);
        searchPanel.add(semesterField);
        searchPanel.add(deptLabel);
        searchPanel.add(deptField);

        searchBtn = new JButton("Search");
        styleButton(searchBtn, buttonFont);
        searchPanel.add(searchBtn);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setOpaque(false);

        cancelBtn = new JButton("Cancel");
        styleButton(cancelBtn, buttonFont);
        bottomPanel.add(cancelBtn);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        background.add(searchPanel, BorderLayout.NORTH);
        background.add(scrollPane, BorderLayout.CENTER);
        background.add(bottomPanel, BorderLayout.SOUTH);

        ImageIcon windowIcon = new ImageIcon(ClassLoader.getSystemResource("image/icon.png"));
        setIconImage(windowIcon.getImage());

        cancelBtn.addActionListener(e -> 
        {
            Home.getInstance(role, userEmail).setVisible(true);
            dispose();
        });

        searchBtn.addActionListener(e -> loadRoutineData());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton button, Font font) 
    {
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBackground(new Color(80, 80, 80));
        button.setForeground(Color.white);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.gray));
        button.setPreferredSize(new Dimension(120, 40));
    }

    private void loadRoutineData() 
    {
        model.setRowCount(0); 

        String semester = semesterField.getText().trim();
        String dept = deptField.getText().trim();

        if (semester.isEmpty() || dept.isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Fill in Both");
            return;
        }
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_management_system", "root", ""
            );

            String query = "SELECT Session, Semester, Department, Course_Name, Teacher, Day, Schedule, Room " +
                           "FROM Routine " +
                           "WHERE Semester=? AND Department=? " +
                           "ORDER BY FIELD(Day,'Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'), Schedule";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, semester);
            pst.setString(2, dept);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) 
            {
                String day = rs.getString("Day");

                if (day.equalsIgnoreCase("Friday") || day.equalsIgnoreCase("Saturday")) {
                    model.addRow(new Object[]
                    {
                        day + " - Holiday", "", "", "", "", "", "", ""
                    });
                } else {
                    model.addRow(new Object[]
                    {
                        day,
                        rs.getString("Session"),
                        rs.getString("Semester"),
                        rs.getString("Department"),
                        rs.getString("Teacher"),
                        rs.getString("Course_Name"),
                        rs.getString("Schedule"),
                        rs.getString("Room")
                    });
                }
            }
            rs.close();
            pst.close();
            conn.close();

            table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                Font holidayFont = new Font("Alice", Font.BOLD, 14);
                Font normalFont = new Font("Segoe UI", Font.PLAIN, 11);

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                                                               boolean isSelected, boolean hasFocus,
                                                               int row, int column) 
                {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    String dayVal = (String) table.getValueAt(row, 0); 
                    if (dayVal != null && dayVal.contains("Holiday")) {
                        c.setFont(holidayFont);
                        c.setForeground(Color.black);
                        c.setBackground(new Color(255, 225, 225));
                    } else {
                        c.setFont(normalFont);
                        c.setForeground(Color.BLACK);
                        c.setBackground(Color.WHITE);
                    }
                    return c;
                }
            });
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void main(String[] args) 
    {
        new Routine("Student", "student@gmail.com");
    }
}
