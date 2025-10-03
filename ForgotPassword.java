//Forgot Password Page
package student_management_system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ForgotPassword extends JFrame {
    JTextField emailField;
    JPasswordField passwordField;

    public ForgotPassword() 
    {
        setTitle("Forgot Password");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        Font labelFont = new Font("Alice", Font.BOLD, 18);
        Font fieldFont = new Font("Alice", Font.PLAIN, 18);

        JLabel heading = new JLabel("RESET ACCOUNT");
        heading.setFont(new Font("Alice", Font.BOLD, 20));
        heading.setForeground(Color.black);
        heading.setBounds(110, 30, 300, 20);
        add(heading);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(50, 100, 150, 27);
        emailLabel.setForeground(Color.black);
        emailLabel.setFont(labelFont);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 100, 200, 30);
        emailField.setFont(fieldFont);
        add(emailField);

        JLabel passwordLabel = new JLabel("New Password");
        passwordLabel.setBounds(15, 150, 150, 27);
        passwordLabel.setForeground(Color.black);
        passwordLabel.setFont(labelFont);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 150, 200, 30);
        passwordField.setFont(fieldFont);
        add(passwordField);

        JButton submitBtn = new JButton("Reset");
        submitBtn.setBounds(140, 220, 100, 40);
        submitBtn.setFont(labelFont);
        submitBtn.setBackground(Color.white);
        add(submitBtn);

        ImageIcon bgImg = new ImageIcon(ClassLoader.getSystemResource("image/forgot.jpg"));
        Image scaledImg = bgImg.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(scaledImg));
        background.setBounds(0, 0, 400, 400);
        add(background);

        ImageIcon windowIcon = new ImageIcon(ClassLoader.getSystemResource("image/icon.png"));
        setIconImage(windowIcon.getImage());

        submitBtn.addActionListener(e -> 
        {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if(email.isEmpty() || password.isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "Fill in All");
                return;
            }
            try (Connection con = ConnectionProvider.getConnection();
                 PreparedStatement ps = con.prepareStatement(
                     "UPDATE Login SET password = ? WHERE email = ?")) 
                     {
                          ps.setString(1, password);
                          ps.setString(2, email);

                int updated = ps.executeUpdate();
                if(updated > 0) 
                {
                    JOptionPane.showMessageDialog(this, "Updated");
                    dispose(); 
                    new Login().setVisible(true);  
                } 
                else 
                {
                    JOptionPane.showMessageDialog(this, "Not Found");
                }
            } catch (Exception ex) 
            {
                JOptionPane.showMessageDialog(this, "Error " + ex.getMessage());
            }
        });
    }
    public static void main(String[] args) 
    {
        new ForgotPassword().setVisible(true);
    }
}
