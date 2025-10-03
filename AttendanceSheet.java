package student_management_system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class AttendanceSheet extends JFrame {

    JTextField studentIdField, nameField;
    JComboBox<String> statusBox;
    JLabel dateLabel;
    String role, userEmail;
    JButton btnSearch;

    public AttendanceSheet(String role, String userEmail) 
    {
        this.role = role;
        this.userEmail = userEmail;

        setTitle("Sheet");
        setSize(1000, 600);
        setLocation(240, 80);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(84, 130, 9));

        Font headingFont = new Font("Arial", Font.BOLD, 25);
        Font labelFont = new Font("Arial", Font.PLAIN, 15);
        Font fieldFont = new Font("Arial", Font.PLAIN, 17);
        Font buttonFont = new Font("Arial", Font.BOLD, 20);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(600, 50, 110, 40);
        btnSearch.setFont(labelFont);
        btnSearch.setBackground(Color.black);
        btnSearch.setForeground(Color.WHITE);
        add(btnSearch);

        JLabel heading = new JLabel("ATTENDANCE SHEET");
        heading.setFont(headingFont);
        heading.setForeground(new Color(255, 255, 255));
        heading.setBounds(200, 20, 300, 30);
        add(heading);

        int labelX = 60, fieldX = 256, width = 230, height = 35, y = 120, gap = 46;
        addLabel("Student ID", labelX, y, labelFont);
        studentIdField = createTextField(fieldX, y, width, height, fieldFont);
        add(studentIdField);

        y += gap;
        addLabel("Student Name", labelX, y, labelFont);
        nameField = createTextField(fieldX, y, width, height, fieldFont);
        add(nameField);

        y += gap;
        addLabel("Date", labelX, y, labelFont);
        dateLabel = new JLabel(LocalDate.now().toString());
        dateLabel.setFont(fieldFont);
        dateLabel.setBounds(fieldX, y, width, height);
        add(dateLabel);

        y += gap;
        addLabel("Status", labelX, y, labelFont);
        statusBox = new JComboBox<>(new String[] { "Present", "Absent" });
        statusBox.setFont(fieldFont);
        statusBox.setBounds(fieldX, y, width, height);
        statusBox.setBackground(Color.WHITE);
        add(statusBox);

        JButton submitBtn = createStyledButton("Insert", buttonFont);
        JButton backBtn = createStyledButton("Back", buttonFont);
        submitBtn.setBounds(140, y + 90, 110, 40);
        backBtn.setBounds(300, y + 90, 110, 40);
        add(submitBtn);
        add(backBtn);

        ImageIcon windowIcon = new ImageIcon(ClassLoader.getSystemResource("image/icon.png"));
        setIconImage(windowIcon.getImage());

        btnSearch.addActionListener(e -> {
            String studentID = studentIdField.getText().trim();
            if (studentID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter ID");
                return;
            }

            try {
                Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_management_system", "root", ""
                );
                String sql = "SELECT Student_Name FROM Student WHERE Student_ID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, studentID);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    nameField.setText(rs.getString("Student_Name"));
                } else {
                    JOptionPane.showMessageDialog(this, "Not found");
                    nameField.setText("");
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error " + ex.getMessage());
            }
        });

        submitBtn.addActionListener(e -> insertAttendance());

        backBtn.addActionListener(e -> {
            dispose();
            new Attendance(role, userEmail);
        });

        setVisible(true);
    }

    private void addLabel(String text, int x, int y, Font font) 
    {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setBounds(x, y, 150, 40);
        label.setForeground(new Color(255, 255, 255));
        add(label);
    }

    private JTextField createTextField(int x, int y, int w, int h, Font font) 
    {
        JTextField field = new JTextField();
        field.setFont(font);
        field.setBounds(x, y, w, h);
        return field;
    }

    private JButton createStyledButton(String text, Font font) 
    {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 0, 0));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        return button;
    }

    private void insertAttendance() 
    {
        String id = studentIdField.getText().trim();
        String name = nameField.getText().trim();
        String date = LocalDate.now().toString();
        String status = (String) statusBox.getSelectedItem();

        if (id.isEmpty() || name.isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Fill in All");
            return;
        }
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_management_system", "root", ""
            );
            String sql = "INSERT INTO Attendance (Student_ID, Student_Name, Date, Status) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, date);
            stmt.setString(4, status);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Insert");
            stmt.close();
            conn.close();

        } catch (SQLIntegrityConstraintViolationException ex) 
        {
            JOptionPane.showMessageDialog(this, "Doesn't exist");
        } 
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(this, "Error" + ex.getMessage());
        }
    }
    public static void main(String[] args) 
    {
        new AttendanceSheet("Teacher", "teacher@gmail.com");
    }
}
