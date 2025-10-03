// Sign Up Page
package student_management_system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SignUp extends JFrame {
    JTextField emailField;
    JPasswordField passwordField;
    JComboBox<String> roleCombo; 

    public SignUp() {
        setTitle("Sign Up");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        Font labelFont = new Font("Alice", Font.BOLD, 18);
        Font fieldFont = new Font("Alice", Font.PLAIN, 18);

        JLabel heading = new JLabel("SIGN UP");
        heading.setFont(new Font("Alice", Font.BOLD, 20));
        heading.setForeground(Color.black);
        heading.setBounds(150, 30, 300, 20);
        add(heading);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(50, 80, 150, 27);
        emailLabel.setForeground(Color.black);
        emailLabel.setFont(labelFont);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 80, 200, 30);
        emailField.setFont(fieldFont);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50, 130, 150, 27);
        passwordLabel.setForeground(Color.black);
        passwordLabel.setFont(labelFont);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 130, 200, 30);
        passwordField.setFont(fieldFont);
        add(passwordField);

        JLabel roleLabel = new JLabel("Role"); 
        roleLabel.setBounds(50, 180, 150, 27);
        roleLabel.setForeground(Color.black);
        roleLabel.setFont(labelFont);
        add(roleLabel);

        String[] roles = { "Admin", "Teacher", "Student", "DeptHead", "LibraryOfficer", "CourseAdvisor" };
        roleCombo = new JComboBox<>(roles);
        roleCombo.setBounds(150, 180, 200, 30);
        roleCombo.setFont(fieldFont);
        add(roleCombo);

        JButton submitBtn = new JButton("Sign Up");
        submitBtn.setBounds(140, 240, 120, 40);
        submitBtn.setFont(labelFont);
        submitBtn.setBackground(Color.white);
        add(submitBtn);

        ImageIcon bgImg = new ImageIcon(ClassLoader.getSystemResource("image/sign up.jpg"));
        Image scaledImg = bgImg.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(scaledImg));
        background.setBounds(0, 0, 400, 400);
        setContentPane(background); 
        background.setLayout(null);

        background.add(heading);
        background.add(emailLabel);
        background.add(emailField);
        background.add(passwordLabel);
        background.add(passwordField);
        background.add(roleLabel);
        background.add(roleCombo);
        background.add(submitBtn);

        ImageIcon windowIcon = new ImageIcon(ClassLoader.getSystemResource("image/icon.png"));
        setIconImage(windowIcon.getImage());

        submitBtn.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleCombo.getSelectedItem(); 

            if (email.isEmpty() || password.isEmpty() || role == null) {
                JOptionPane.showMessageDialog(this, "Fill in All");
                return;
            }

            try (Connection con = ConnectionProvider.getConnection();
                 PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO Login (email, password, role) VALUES (?, ?, ?)")) {
                ps.setString(1, email);
                ps.setString(2, password);
                ps.setString(3, role);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Successful as " + role);
                dispose();
                new Login().setVisible(true);

            } catch (SQLIntegrityConstraintViolationException ex1) {
                JOptionPane.showMessageDialog(this, "Exist");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error " + ex.getMessage());
            }
        });
    }
    public static void main(String[] args) {
        new SignUp().setVisible(true);
    }
}
