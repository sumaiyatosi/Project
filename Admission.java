package student_management_system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Admission extends JFrame {
    JTextField tfStudent_ID, tfStudent_Name, tfProgram, tfDepartment, tfSession, tfSemester, tfOffer,
    tfGPA, tfWaiver, tfGender, tfFathers_Name, tfPhone, tfEmail;
    JTextArea taAddress;
    JButton btnSubmit, btnBack, btnSearch;
    String role, userEmail;

    public Admission(String role, String userEmail) {
        this.role = role;
        this.userEmail = userEmail;

        setTitle("Admission");
        setSize(1000, 600);
        setLocation(240, 80);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font labelFont = new Font("Alice", Font.BOLD, 17);
        Font fieldFont = new Font("Alice", Font.PLAIN, 18);

        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("image/bg_img1.jpg"));
        Image i22 = i11.getImage().getScaledInstance(1000, 600, Image.SCALE_DEFAULT);
        ImageIcon i33 = new ImageIcon(i22);
        JLabel image = new JLabel(i33);
        image.setBounds(0, 0, 1000, 600);
        setContentPane(image);
        image.setLayout(null);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(800, 20, 130, 40);
        btnSearch.setFont(labelFont);
        btnSearch.setBackground(Color.GRAY);
        btnSearch.setForeground(Color.WHITE);
        image.add(btnSearch);

        tfStudent_ID = addLabelAndTextField("Student ID", 50, 70, 200, 33, image, labelFont, fieldFont);
        tfStudent_Name = addLabelAndTextField("Student Name", 50, 110, 200, 33, image, labelFont, fieldFont);
        tfProgram = addLabelAndTextField("Program", 50, 150, 200, 33, image, labelFont, fieldFont);
        tfDepartment = addLabelAndTextField("Department", 50, 190, 200, 33, image, labelFont, fieldFont);
        tfSemester = addLabelAndTextField("Semester", 50, 230, 200, 33, image, labelFont, fieldFont);
        tfSession = addLabelAndTextField("Session", 50, 270, 200, 33, image, labelFont, fieldFont);
        tfGender = addLabelAndTextField("Gender", 50, 310, 200, 33, image, labelFont, fieldFont);
        tfFathers_Name = addLabelAndTextField("Father's Name", 50, 350, 200, 33, image, labelFont, fieldFont);
        tfGPA = addLabelAndTextField("GPA", 500, 70, 200, 33, image, labelFont, fieldFont);
        tfWaiver = addLabelAndTextField("Waiver", 500, 110, 200, 33, image, labelFont, fieldFont);
        tfPhone = addLabelAndTextField("Phone", 500, 150, 200, 33, image, labelFont, fieldFont);
        tfEmail = addLabelAndTextField("Email", 500, 190, 200, 33, image, labelFont, fieldFont);
        tfOffer = addLabelAndTextField("Offer", 500, 230, 200, 33, image, labelFont, fieldFont);

        JLabel lblAddress = new JLabel("Address");
        lblAddress.setBounds(500, 270, 120, 33);
        lblAddress.setForeground(Color.WHITE);
        lblAddress.setFont(labelFont);
        image.add(lblAddress);

        taAddress = new JTextArea();
        taAddress.setBounds(650, 270, 200, 33);
        taAddress.setBackground(Color.WHITE);
        taAddress.setFont(fieldFont);
        image.add(taAddress);

        btnSubmit = new JButton("Admit");
        btnSubmit.setBounds(320, 420, 100, 40);
        btnSubmit.setFont(labelFont);
        btnSubmit.setBackground(Color.GRAY);
        btnSubmit.setForeground(Color.WHITE);
        image.add(btnSubmit);

        btnBack = new JButton("Cancel");
        btnBack.setBounds(450, 420, 100, 40);
        btnBack.setFont(labelFont);
        btnBack.setBackground(Color.GRAY);
        btnBack.setForeground(Color.WHITE);
        image.add(btnBack);

        btnBack.addActionListener(e -> {
            Home.getInstance(role, userEmail).setVisible(true);
            dispose();
        });

        btnSubmit.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfStudent_ID.getText().trim());
                String name = tfStudent_Name.getText().trim();
                String program = tfProgram.getText().trim();
                String dept = tfDepartment.getText().trim();
                String semester = tfSemester.getText().trim();
                String session = tfSession.getText().trim();
                String gender = tfGender.getText().trim();
                String father = tfFathers_Name.getText().trim();
                double gpa = Double.parseDouble(tfGPA.getText().trim());
                String waiver = tfWaiver.getText().trim();
                int phone = Integer.parseInt(tfPhone.getText().trim());
                String email = tfEmail.getText().trim();
                String offer = tfOffer.getText().trim();
                String address = taAddress.getText().trim();

                if(name.isEmpty()) {
                    JOptionPane.showMessageDialog(Admission.this, "Name is Required");
                    return;
                }

                Connection con = ConnectionProvider.getConnection();
                String query = "INSERT INTO Student (Student_ID, Student_Name, Program, Department, Semester, Session, Offer, GPA, Waiver, Gender, Fathers_Name, Phone, Email, Address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setString(3, program);
                ps.setString(4, dept);
                ps.setString(5, semester);
                ps.setString(6, session);
                ps.setString(7, offer);
                ps.setDouble(8, gpa);
                ps.setString(9, waiver);
                ps.setString(10, gender);
                ps.setString(11, father);
                ps.setInt(12, phone);
                ps.setString(13, email);
                ps.setString(14, address);

                int rows = ps.executeUpdate();
                if(rows > 0) JOptionPane.showMessageDialog(Admission.this, "Admitted");

                ps.close();
                con.close();

            } catch(NumberFormatException nfe) {
                JOptionPane.showMessageDialog(Admission.this, "ID, GPA and Phone must be numeric");
            } catch(Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Admission.this, "Failed");
            }
        });

        btnSearch.addActionListener(e -> {
            try {
                int studentID = Integer.parseInt(tfStudent_ID.getText().trim());

                Connection con = ConnectionProvider.getConnection();
                String query = "SELECT * FROM Student WHERE Student_ID = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, studentID);

                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    tfStudent_Name.setText(rs.getString("Student_Name"));
                    tfProgram.setText(rs.getString("Program"));
                    tfDepartment.setText(rs.getString("Department"));
                    tfSemester.setText(rs.getString("Semester"));
                    tfSession.setText(rs.getString("Session"));
                    tfOffer.setText(rs.getString("Offer"));
                    tfGPA.setText(String.valueOf(rs.getDouble("GPA")));
                    tfWaiver.setText(rs.getString("Waiver"));
                    tfGender.setText(rs.getString("Gender"));
                    tfFathers_Name.setText(rs.getString("Fathers_Name"));
                    tfPhone.setText(String.valueOf(rs.getInt("Phone")));
                    tfEmail.setText(rs.getString("Email"));
                    taAddress.setText(rs.getString("Address"));
                } else {
                    JOptionPane.showMessageDialog(Admission.this, "Not Found");
                }
                rs.close();
                stmt.close();
                con.close();
            } catch(NumberFormatException nfe) {
                JOptionPane.showMessageDialog(Admission.this, "Enter ID");
            } catch(Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Admission.this, "Error ");
            }
        });
        setIconImage(new ImageIcon(ClassLoader.getSystemResource("image/icon.png")).getImage());
        setVisible(true);
    }

    private JTextField addLabelAndTextField(String label, int x, int y, int width, int height, JLabel parent, Font labelFont, Font fieldFont) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(x, y, 120, 25);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(labelFont);
        parent.add(lbl);

        JTextField tf = new JTextField();
        tf.setBounds(x + 150, y, width, height);
        tf.setBackground(Color.WHITE);
        tf.setFont(fieldFont);
        parent.add(tf);
        return tf;
    }
    public static void main(String[] args) {
        new Admission("DeptHead", "arif@gmail.com");
    }
}
