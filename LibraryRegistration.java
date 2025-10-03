//Register Page
package student_management_system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LibraryRegistration extends JFrame {
    JTextField idField, nameField, sessionField, semesterField, deptField,
    fatherField, memberField, phoneField, emailField, addressField;
    String role, userEmail;

    public LibraryRegistration(String role, String userEmail) 
    {
        this.role = role;
        this.userEmail = userEmail;

        setTitle("Library Member");
        setSize(1120, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        Font labelFont = new Font("Alice", Font.BOLD, 18);
        Font fieldFont = new Font("Alice", Font.PLAIN, 18);

        JLabel heading = new JLabel("REGISTRATION");
        heading.setFont(new Font("Alice", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        heading.setBounds(400, 8, 300, 40);
        add(heading);

        JButton backBtn = new JButton("⬅Home");
        backBtn.setBounds(60, 20, 110, 35);
        backBtn.setFont(labelFont);
        backBtn.setBackground(Color.GRAY);
        backBtn.setForeground(Color.WHITE);
        add(backBtn);

        JLabel idLabel = new JLabel("Student ID");
        idLabel.setBounds(300, 70, 150, 27);
        idLabel.setForeground(Color.WHITE);
        idLabel.setFont(labelFont);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(450, 70, 200, 35);
        idField.setFont(fieldFont);
        add(idField);

        JLabel nameLabel = new JLabel("Student Name");
        nameLabel.setBounds(300, 110, 150, 27);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(labelFont);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(450, 110, 200, 35);
        nameField.setFont(fieldFont);
        add(nameField);

        JLabel sessionLabel = new JLabel("Session");
        sessionLabel.setBounds(300, 150, 150, 27);
        sessionLabel.setForeground(Color.WHITE);
        sessionLabel.setFont(labelFont);
        add(sessionLabel);

        sessionField = new JTextField();
        sessionField.setBounds(450, 150, 200, 35);
        sessionField.setFont(fieldFont);
        add(sessionField);

        JLabel semesterLabel = new JLabel("Semester");
        semesterLabel.setBounds(300, 190, 150, 27);
        semesterLabel.setForeground(Color.WHITE);
        semesterLabel.setFont(labelFont);
        add(semesterLabel);

        semesterField = new JTextField();
        semesterField.setBounds(450, 190, 200, 35);
        semesterField.setFont(fieldFont);
        add(semesterField);

        JLabel deptLabel = new JLabel("Department");
        deptLabel.setBounds(300, 230, 150, 27);
        deptLabel.setForeground(Color.WHITE);
        deptLabel.setFont(labelFont);
        add(deptLabel);

        deptField = new JTextField();
        deptField.setBounds(450, 230, 200, 35);
        deptField.setFont(fieldFont);
        add(deptField);

        JLabel fatherLabel = new JLabel("Father's Name");
        fatherLabel.setBounds(300, 270, 150, 27);
        fatherLabel.setForeground(Color.WHITE);
        fatherLabel.setFont(labelFont);
        add(fatherLabel);

        fatherField = new JTextField();
        fatherField.setBounds(450, 270, 200, 35);
        fatherField.setFont(fieldFont);
        add(fatherField);

        JLabel memberLabel = new JLabel("Member No.");
        memberLabel.setBounds(300, 310, 150, 27);
        memberLabel.setForeground(Color.WHITE);
        memberLabel.setFont(labelFont);
        add(memberLabel);

        memberField = new JTextField();
        memberField.setBounds(450, 310, 200, 35);
        memberField.setFont(fieldFont);
        add(memberField);

        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setBounds(300, 350, 150, 27);
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setFont(labelFont);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(450, 350, 200, 35);
        phoneField.setFont(fieldFont);
        add(phoneField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(300, 390, 150, 27);
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(labelFont);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(450, 390, 200, 35);
        emailField.setFont(fieldFont);
        add(emailField);

        JLabel addressLabel = new JLabel("Address");
        addressLabel.setBounds(300, 430, 150, 27);
        addressLabel.setForeground(Color.WHITE);
        addressLabel.setFont(labelFont);
        add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(450, 430, 200, 35);
        addressField.setFont(fieldFont);
        add(addressField);

        JButton registerBtn = new JButton("Submit");
        registerBtn.setBounds(430, 500, 120, 40);
        registerBtn.setFont(labelFont);
        registerBtn.setBackground(Color.white);
        add(registerBtn);

        ImageIcon bgImg = new ImageIcon(ClassLoader.getSystemResource("image/register.jpg"));
        Image scaledImg = bgImg.getImage().getScaledInstance(1120, 650, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(scaledImg));
        background.setBounds(0, 0, 1120, 650);
        add(background);

        ImageIcon windowIcon = new ImageIcon(ClassLoader.getSystemResource("image/icon.png"));
        setIconImage(windowIcon.getImage());

        registerBtn.addActionListener(e -> 
        {
            try (Connection con = ConnectionProvider.getConnection();
                 PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Library_Member " +
                    "(Student_ID, Student_Name, Session, Semester, Department, Fathers_Name, Member_No, Phone, Email, Address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) 
            {
                ps.setInt(1, Integer.parseInt(idField.getText()));
                ps.setString(2, nameField.getText());
                ps.setString(3, sessionField.getText());
                ps.setString(4, semesterField.getText());
                ps.setString(5, deptField.getText());
                ps.setString(6, fatherField.getText());
                ps.setString(7, memberField.getText());
                ps.setString(8, phoneField.getText());
                ps.setString(9, emailField.getText());
                ps.setString(10, addressField.getText());

                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Registered");
                clearFields();

            } catch (Exception ex) 
            {
                JOptionPane.showMessageDialog(this, "Error " + ex.getMessage());
            }
        });

        backBtn.addActionListener(e -> 
        {
            dispose();
            new Library(role, userEmail).setVisible(true);
        });
    }

    void clearFields() 
    {
        idField.setText("");
        nameField.setText("");
        sessionField.setText("");
        semesterField.setText("");
        deptField.setText("");
        fatherField.setText("");
        memberField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
    }
    public static void main(String[] args) 
    {
        new LibraryRegistration("LibraryOfficer", "mizan@gmail.com").setVisible(true);
    }
}
