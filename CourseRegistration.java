package student_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;

public class CourseRegistration extends JFrame {

    JTextField studentIdField, studentNameField, programField, departmentField, semesterField, sessionField,
    courseNameField, retakeField, courseAdvisorField, durationField, feeField, gpaField, enrollField,
    fatherNameField, phoneField, emailField, addressField, offerField, fineField;
    JLabel autoWaiverLabel;
    JCheckBox paidCheckBox;
    JButton submitBtn, cancelBtn, searchBtn;
    JLabel registeredLabel;
    String role, userEmail;
    double waiverAmount = 0;

    public CourseRegistration(String role, String userEmail) {
        this.role = role;
        this.userEmail = userEmail;

        setTitle("Course Registration");
        setSize(1100, 700);
        setLocation(180, 15);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font labelFont = new Font("Alice", Font.BOLD, 17);
        Font fieldFont = new Font("Alice", Font.PLAIN, 14);

        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("image/bg_img2.jpg"));
        Image bgImage = bgIcon.getImage().getScaledInstance(1100, 700, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setBounds(0, 0, 1100, 700);
        setContentPane(background);
        background.setLayout(null);

        registeredLabel = new JLabel("Registered");
        registeredLabel.setBounds(960, 0, 150, 80);
        registeredLabel.setFont(labelFont);
        registeredLabel.setForeground(Color.WHITE);
        registeredLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        background.add(registeredLabel);

        registeredLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RegistrationInfo(role, userEmail).setVisible(true);
                dispose();
            }
        });

        int leftXLabel = 100, leftXField = 250;
        int rightXLabel = 500, rightXField = 630;
        int y = 70, height = 28, gap = 35;

        studentIdField = createField(background, labelFont, fieldFont, "Student ID", leftXLabel, y, 180, height, leftXField, y);
        y += gap;
        searchBtn = new JButton("Search");
        searchBtn.setBounds(leftXField + 600, y - gap, 120, 35);
        searchBtn.setFont(new Font("Alice", Font.BOLD, 20));
        searchBtn.setForeground(Color.black);
        searchBtn.setBackground(Color.white);
        background.add(searchBtn);

        studentNameField = createField(background, labelFont, fieldFont, "Student Name", leftXLabel, y, 180, height, leftXField, y);
        y += gap;
        programField = createField(background, labelFont, fieldFont, "Program", leftXLabel, y, 180, height, leftXField, y);
        y += gap;
        departmentField = createField(background, labelFont, fieldFont, "Department", leftXLabel, y, 180, height, leftXField, y);
        y += gap;
        semesterField = createField(background, labelFont, fieldFont, "Semester", leftXLabel, y, 180, height, leftXField, y);
        y += gap;
        sessionField = createField(background, labelFont, fieldFont, "Session", leftXLabel, y, 180, height, leftXField, y);
        y += gap;
        courseAdvisorField = createField(background, labelFont, fieldFont, "Course Advisor", leftXLabel, y, 180, height, leftXField, y);
        y += gap;
        durationField = createField(background, labelFont, fieldFont, "Duration", leftXLabel, y, 180, height, leftXField, y);
        y += gap;
        fatherNameField = createField(background, labelFont, fieldFont, "Father's Name", leftXLabel, y, 180, height, leftXField, y);
        y += gap;
        phoneField = createField(background, labelFont, fieldFont, "Phone", leftXLabel, y, 180, height, leftXField, y);
        y += gap;
        emailField = createField(background, labelFont, fieldFont, "Email", leftXLabel, y, 180, height, leftXField, y);
        y += gap;
        addressField = createField(background, labelFont, fieldFont, "Address", leftXLabel, y, 180, height, leftXField, y);
        y += gap;

        y = 70;
        courseNameField = createField(background, labelFont, fieldFont, "Course Name", rightXLabel, y, 180, height, rightXField, y);
        y += gap;
        retakeField = createField(background, labelFont, fieldFont, "Retake", rightXLabel, y, 180, height, rightXField, y);
        y += gap;
        feeField = createField(background, labelFont, fieldFont, "Tuition Fee", rightXLabel, y, 180, height, rightXField, y);
        y += gap;
        enrollField = createField(background, labelFont, fieldFont, "Date", rightXLabel, y, 180, height, rightXField, y);
        y += gap;
        gpaField = createField(background, labelFont, fieldFont, "GPA", rightXLabel, y, 180, height, rightXField, y);
        y += gap;

        autoWaiverLabel = new JLabel("0.0");
        autoWaiverLabel.setBounds(rightXField, y, 150, height);
        autoWaiverLabel.setForeground(Color.WHITE);
        autoWaiverLabel.setFont(labelFont);
        JLabel lblWaiver = new JLabel("Waiver");
        lblWaiver.setBounds(rightXLabel, y, 150, height);
        lblWaiver.setForeground(Color.WHITE);
        lblWaiver.setFont(labelFont);
        background.add(lblWaiver);
        background.add(autoWaiverLabel);
        y += gap;

        offerField = createField(background, labelFont, fieldFont, "Offer", rightXLabel, y, 180, height, rightXField, y);
        y += gap;
        fineField = createField(background, labelFont, fieldFont, "Fine", rightXLabel, y, 180, height, rightXField, y);
        y += gap;

        paidCheckBox = new JCheckBox();
        paidCheckBox.setBounds(rightXField, y, 25, height);
        paidCheckBox.setBackground(Color.white);
        JLabel lblPaid = new JLabel("Paid");
        lblPaid.setBounds(rightXLabel, y, 140, height);
        lblPaid.setForeground(Color.white);
        lblPaid.setFont(labelFont);
        background.add(lblPaid);
        background.add(paidCheckBox);

        submitBtn = new JButton("Register");
        submitBtn.setBounds(400, 550, 120, 40);
        submitBtn.setFont(labelFont);
        submitBtn.setBackground(Color.WHITE);
        submitBtn.setForeground(Color.BLACK);
        background.add(submitBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(560, 550, 120, 40);
        cancelBtn.setFont(labelFont);
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setForeground(Color.BLACK);
        background.add(cancelBtn);

        cancelBtn.addActionListener(e -> {
            Home.getInstance(role, userEmail).setVisible(true);
            dispose();
        });

        gpaField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calculateWaiver();
            }
        });

        submitBtn.addActionListener(e -> handleSubmit());
        searchBtn.addActionListener(e -> handleSearch());

        ImageIcon windowIcon = new ImageIcon(ClassLoader.getSystemResource("image/icon.png"));
        setIconImage(windowIcon.getImage());
        setVisible(true);
    }

    private JTextField createField(JLabel parent, Font labelFont, Font fieldFont,
                                   String labelText, int lblX, int lblY, int lblWidth, int lblHeight, int fldX, int fldY) {
        JLabel lbl = new JLabel(labelText);
        lbl.setBounds(lblX, lblY, lblWidth, lblHeight);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(labelFont);
        parent.add(lbl);

        JTextField tf = new JTextField();
        tf.setBounds(fldX, fldY, lblWidth, lblHeight);
        tf.setFont(fieldFont);
        tf.setBackground(Color.white);
        tf.setForeground(Color.black);
        parent.add(tf);
        return tf;
    }

    private void calculateWaiver() {
        try {
            double gpa = Double.parseDouble(gpaField.getText());
            if (gpa >= 5.0) waiverAmount = 100.0;
            else if (gpa >= 4.5) waiverAmount = 60.0;
           
            else if (gpa >= 4.0) waiverAmount = 50.0;
            else if (gpa >= 3.5) waiverAmount = 30.0;
            else waiverAmount = 0.0;
            autoWaiverLabel.setText(String.format("%.2f", waiverAmount));
        } catch (Exception ex) {
            autoWaiverLabel.setText("0.0");
        }
    }

    private void handleSubmit() {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_management_system", "root", ""
            );

            String sql = "INSERT INTO Course_Registration " +
                    "(Student_ID, Student_Name, Program, Department, Session, Semester, Course_Name, Retake, Course_Advisor," +
                    "Duration, Tuition_Fee, GPA, Waiver, Offer, Fine, Date, Fathers_Name, Phone, Email, Address, Paid) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(studentIdField.getText()));
            stmt.setString(2, studentNameField.getText());
            stmt.setString(3, programField.getText());
            stmt.setString(4, departmentField.getText());
            stmt.setString(5, sessionField.getText());
            stmt.setString(6, semesterField.getText());
            stmt.setString(7, courseNameField.getText());
            stmt.setString(8, retakeField.getText());
            stmt.setString(9, courseAdvisorField.getText());
            stmt.setString(10, durationField.getText());
            stmt.setDouble(11, Double.parseDouble(feeField.getText()));
            stmt.setDouble(12, Double.parseDouble(gpaField.getText()));
            stmt.setString(13, String.valueOf(waiverAmount));
            stmt.setString(14, offerField.getText());
            stmt.setDouble(15, Double.parseDouble(fineField.getText()));
            stmt.setDate(16, enrollField.getText().isEmpty()
                    ? java.sql.Date.valueOf(LocalDate.now())
                    : java.sql.Date.valueOf(enrollField.getText()));
            stmt.setString(17, fatherNameField.getText());
            stmt.setInt(18, Integer.parseInt(phoneField.getText()));
            stmt.setString(19, emailField.getText());
            stmt.setString(20, addressField.getText());
            stmt.setString(21, paidCheckBox.isSelected() ? "Yes" : "No");

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration");
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error " + ex.getMessage());
        }
    }

    private void handleSearch() {
        String studentID = studentIdField.getText().trim();
        if(studentID.isEmpty()){
            JOptionPane.showMessageDialog(this, "Enter Student ID");
            return;
        }
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_management_system", "root", ""
            );
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Student WHERE Student_ID = ?");
            stmt.setInt(1, Integer.parseInt(studentID));
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                studentNameField.setText(rs.getString("Student_Name"));
                programField.setText(rs.getString("Program"));
                departmentField.setText(rs.getString("Department"));
                sessionField.setText(rs.getString("Session"));
                semesterField.setText(rs.getString("Semester"));
                fatherNameField.setText(rs.getString("Fathers_Name"));
                phoneField.setText(String.valueOf(rs.getInt("Phone")));
                emailField.setText(rs.getString("Email"));
                addressField.setText(rs.getString("Address"));
            } else {
                JOptionPane.showMessageDialog(this, "Not Found");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        new CourseRegistration("CourseAdvisor", "rashed@gmail.com");
    }
}
