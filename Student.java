package student_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.sql.*;

public class Student extends JFrame {
    JTextField tfStudent_ID, tfStudent_Name, tfP, tfDepartment, tfSe, tfSes, tfOffer, tfGender, tfFathers_Name, tfCGPA, tfWaiver, tfPhone_Number, tfEmail;
    JTextArea taAddress;
    JButton btnSearch, btnBack;
    JTable table;
    String role, userEmail;

    public Student(String role, String userEmail) {
        this.role = role;
        this.userEmail = userEmail;

        setTitle("University Student");
        setSize(1300, 610);
        setLocation(40, 70);
        getContentPane().setBackground(new Color(84, 130, 9));
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font labelFont = new Font("Alice", Font.BOLD, 17);
        Font fieldFont = new Font("Alice", Font.PLAIN, 18);

        JLabel lblID = new JLabel("Student ID");
        lblID.setFont(labelFont);
        lblID.setBounds(20, 20, 120, 30);
        add(lblID);

        tfStudent_ID = new JTextField();
        tfStudent_ID.setFont(fieldFont);
        tfStudent_ID.setBounds(140, 20, 200, 30);
        add(tfStudent_ID);

        btnSearch = new JButton("Search");
        btnSearch.setFont(labelFont);
        btnSearch.setBackground(Color.BLACK);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setBounds(360, 20, 120, 35);
        add(btnSearch);

        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Alice", Font.PLAIN, 15));
        table.setBackground(Color.white);
        table.setForeground(Color.black);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Alice", Font.BOLD, 14));
        header.setBackground(Color.black);
        header.setForeground(Color.white);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 70, 1250, 420);
        add(scrollPane);

        btnBack = new JButton("Cancel");
        btnBack.setFont(new Font("Alice", Font.BOLD, 25));
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.setBounds(565, 510, 150, 40);
        add(btnBack);

        loadAllStudents();

        btnBack.addActionListener(e -> {
            Home.getInstance(role, userEmail).setVisible(true);
            dispose();
        });

        btnSearch.addActionListener(e -> {
            String id = tfStudent_ID.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter ID");
                return;
            }
            searchStudent(id);
        });

        ImageIcon windowIcon = new ImageIcon(ClassLoader.getSystemResource("image/icon.png"));
        setIconImage(windowIcon.getImage());

        setVisible(true);
    }

    private void loadAllStudents() {
        try {
            Connection con = ConnectionProvider.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Student");

            String[] columns = {"Student ID", "Student Name", "Program", "Department", "Semester", "Session", "Offer", "GPA", "Waiver", "Gender", "Father's Name", "Phone", "Email", "Address"};
            Object[][] data = {};

            DefaultTableModel model = new DefaultTableModel(data, columns);
            table.setModel(model);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("Student_ID"),
                        rs.getString("Student_Name"),
                        rs.getString("Program"),
                        rs.getString("Department"),
                        rs.getString("Semester"),
                        rs.getString("Session"),
                        rs.getString("Offer"),
                        rs.getDouble("GPA"),
                        rs.getString("Waiver"),
                        rs.getString("Gender"),
                        rs.getString("Fathers_Name"),
                        rs.getInt("Phone"),
                        rs.getString("Email"),
                        rs.getString("Address")
                });
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed");
        }
    }
    private void searchStudent(String studentID) {
        try {
            Connection con = ConnectionProvider.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM Student WHERE Student_ID = ?");
            pst.setInt(1, Integer.parseInt(studentID));
            ResultSet rs = pst.executeQuery();

            String[] columns = {"Student ID", "Student Name", "Program", "Department", "Semester", "Session", "Offer", "GPA", "Waiver", "Gender", "Father's Name", "Phone", "Email", "Address"};
            Object[][] data = {};
            DefaultTableModel model = new DefaultTableModel(data, columns);
            table.setModel(model);

            if (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("Student_ID"),
                        rs.getString("Student_Name"),
                        rs.getString("Program"),
                        rs.getString("Department"),
                        rs.getString("Semester"),
                        rs.getString("Session"),
                        rs.getString("Offer"),
                        rs.getDouble("GPA"),
                        rs.getString("Waiver"),
                        rs.getString("Gender"),
                        rs.getString("Fathers_Name"),
                        rs.getInt("Phone"),
                        rs.getString("Email"),
                        rs.getString("Address")
                });
            } else {
                JOptionPane.showMessageDialog(this, "Not Found");
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error!");
        }
    }
    public static void main(String[] args) {
        new Student("Admin", "admin@gmail.com");
    }
}
