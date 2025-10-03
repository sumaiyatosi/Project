// LibraryStudent.java
package student_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class LibraryStudent extends JFrame {
    JTable table;
    DefaultTableModel model;
    JButton cancelBtn, searchBtn;
    JTextField studentIdField;
    String role;
    String userEmail;

    public LibraryStudent(String role, String userEmail) 
    {
        this.role = role;
        this.userEmail = userEmail;
        
        setTitle("Library Member");
        setSize(1200, 640);
        setLocation(240, 80);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Font headerFont = new Font("Segoe UI", Font.BOLD, 16);
        Font tableFont = new Font("Segoe UI", Font.BOLD, 13);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 20);

        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("image/library.jpg"));
        Image bgImage = bgIcon.getImage().getScaledInstance(1200, 640, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        setContentPane(background);
        background.setLayout(new BorderLayout());

        model = new DefaultTableModel();
        table = new JTable(model) 
        {
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                String searchId = studentIdField.getText().trim();
                String tableId = getValueAt(row, 0).toString();
                if (!searchId.isEmpty() && tableId.equalsIgnoreCase(searchId)) 
                {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                } else 
                {
                    c.setBackground(Color.LIGHT_GRAY);
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        };

        table.setFont(tableFont);
        table.setRowHeight(25);
        table.getTableHeader().setFont(headerFont);
        table.getTableHeader().setBackground(Color.DARK_GRAY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(Color.GRAY);
        table.setOpaque(false);

        model.setColumnIdentifiers(new String[]
        {
            "Student ID", "Student Name", "Session", "Semester", "Department", "Father's Name", "Member No", "Phone", "Email", "Address"
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setOpaque(false);

        JLabel idLabel = new JLabel("Student ID");
        idLabel.setForeground(Color.WHITE);
        studentIdField = new JTextField();
        studentIdField.setPreferredSize(new Dimension(120, 35));

        searchBtn = new JButton("Search");
        styleButton(searchBtn, buttonFont);

        searchPanel.add(idLabel);
        searchPanel.add(studentIdField);
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
            new Library(role, userEmail).setVisible(true);
            dispose();
        });

        searchBtn.addActionListener(e -> loadLibraryStudents());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton button, Font font) 
    {
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 0, 0));
        button.setForeground(Color.white);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.black));
        button.setPreferredSize(new Dimension(120, 40));
    }

    private void loadLibraryStudents() 
    {
        model.setRowCount(0); 
        String searchId = studentIdField.getText().trim();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "root", "");
            String query;

            if (!searchId.isEmpty()) {
                query = "SELECT * FROM Library_Member WHERE Student_ID=?";
            } else {
                query = "SELECT * FROM Library_Member";
            }
            PreparedStatement pst = conn.prepareStatement(query);

            if (!searchId.isEmpty()) {
                pst.setString(1, searchId);
            }
            ResultSet rs = pst.executeQuery();

            while (rs.next()) 
            {
                model.addRow(new Object[]
                {
                    rs.getString("Student_ID"),
                    rs.getString("Student_Name"),
                    rs.getString("Session"),
                    rs.getString("Semester"),
                    rs.getString("Department"),
                    rs.getString("Fathers_Name"),
                    rs.getString("Member_No"),
                    rs.getString("Phone"),
                    rs.getString("Email"),
                    rs.getString("Address")
                });
            }
            rs.close();
            pst.close();
            conn.close();
            table.repaint();
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error " + e.getMessage());
        }
    }
    public static void main(String[] args) 
    {
        new LibraryStudent("LibraryOfficer", "mizan@gmail.com");
    }
}
