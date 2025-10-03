package student_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class Library extends JFrame {

    JTextField titleField, authorField, deptField, searchField;
    JTable table;
    DefaultTableModel model;
    String filePath = "";
    String role; 
    String userEmail;

    public Library(String role, String userEmail) 
    {
        this.role = role;
        this.userEmail = userEmail;

        setTitle("Library");
        setSize(1200, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        Font labelFont = new Font("Alice", Font.BOLD, 18);
        Font fieldFont = new Font("Alice", Font.PLAIN, 18);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.GRAY);

        JMenuItem booksItem = new JMenuItem("BOOK");
        booksItem.setForeground(Color.BLACK);
        booksItem.setBackground(Color.WHITE);
        booksItem.setOpaque(true);
        menuBar.add(booksItem);

        JMenuItem registerItem = new JMenuItem("REGISTER");
        registerItem.setForeground(Color.black);
        registerItem.setBackground(Color.white);
        registerItem.setOpaque(true);
        menuBar.add(registerItem);

        JMenuItem studentItem = new JMenuItem("STUDENT");
        studentItem.setForeground(Color.black);
        studentItem.setBackground(Color.white);
        studentItem.setOpaque(true);
        menuBar.add(studentItem);
        setJMenuBar(menuBar);

        JLabel heading = new JLabel("LIBRARY");
        heading.setFont(new Font("Alice", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        heading.setBounds(450, 8, 180, 40);
        add(heading);

        JButton backBtn = new JButton("⬅Home");
        backBtn.setBounds(30, 10, 110, 35);
        backBtn.setFont(labelFont);
        backBtn.setBackground(Color.GRAY);
        backBtn.setForeground(Color.WHITE);
        add(backBtn);

        JButton openBtn = new JButton("READ NOW");
        openBtn.setBounds(80, 60, 140, 35);
        openBtn.setFont(labelFont);
        openBtn.setBackground(Color.ORANGE);
        add(openBtn);
        openBtn.addActionListener(e -> openPDF());

        searchField = new JTextField();
        searchField.setBounds(850, 60, 150, 35);
        searchField.setFont(fieldFont);
        add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(1000, 60, 100, 35);
        searchBtn.setFont(labelFont);
        searchBtn.setBackground(Color.orange);
        searchBtn.setForeground(Color.WHITE);
        add(searchBtn);

        model = new DefaultTableModel(new String[]
        {
            "Book ID", "Name", "Author", "Department", "File"
        }, 0);

        table = new JTable(model);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Alice", Font.BOLD, 16));
        header.setBackground(Color.ORANGE);
        header.setForeground(Color.BLACK);
        table.setFont(new Font("Alice", Font.PLAIN, 16));
        table.setRowHeight(28);
        table.setBackground(new Color(255, 235, 205)); 
        table.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(80, 130, 1020, 200);
        add(scrollPane);

        JLabel lblTitle = new JLabel("Name");
        lblTitle.setBounds(80, 377, 100, 27);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(labelFont);
        add(lblTitle);

        titleField = new JTextField();
        titleField.setBounds(200, 377, 200, 35);
        titleField.setFont(fieldFont);
        add(titleField);

        JLabel lblAuthor = new JLabel("Author");
        lblAuthor.setBounds(80, 427, 100, 27);
        lblAuthor.setForeground(Color.WHITE);
        lblAuthor.setFont(labelFont);
        add(lblAuthor);

        authorField = new JTextField();
        authorField.setBounds(200, 427, 200, 35);
        authorField.setFont(fieldFont);
        add(authorField);

        JLabel lblDept = new JLabel("Department");
        lblDept.setBounds(80, 477, 120, 27);
        lblDept.setForeground(Color.WHITE);
        lblDept.setFont(labelFont);
        add(lblDept);

        deptField = new JTextField();
        deptField.setBounds(200, 477, 200, 35);
        deptField.setFont(fieldFont);
        add(deptField);

        JButton uploadBtn = new JButton("UPLOAD");
        uploadBtn.setBounds(420, 377, 120, 35);
        uploadBtn.setFont(labelFont);
        uploadBtn.setBackground(Color.ORANGE);
        add(uploadBtn);

        JButton addBtn = new JButton("STORE");
        addBtn.setBounds(420, 427, 120, 35);
        addBtn.setFont(labelFont);
        addBtn.setBackground(Color.orange.darker());
        add(addBtn);

        JButton deleteBtn = new JButton("DELETE");
        deleteBtn.setBounds(420, 475, 120, 35);
        deleteBtn.setFont(labelFont);
        deleteBtn.setBackground(Color.orange.darker());
        add(deleteBtn);

        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("image/books.jpg"));
        Image i22 = i11.getImage().getScaledInstance(1200, 640, Image.SCALE_DEFAULT);
        ImageIcon i33 = new ImageIcon(i22);
        JLabel background = new JLabel(i33);
        background.setBounds(0, 0, 1200, 640);
        add(background);

        ImageIcon windowIcon = new ImageIcon(ClassLoader.getSystemResource("image/icon.png"));
        setIconImage(windowIcon.getImage());
        loadBooks("");

        uploadBtn.addActionListener(e -> 
        {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) 
            {
                filePath = fileChooser.getSelectedFile().getAbsolutePath();
            }
        });

        addBtn.addActionListener(e -> addBook());
        deleteBtn.addActionListener(e -> deleteBook());

        searchBtn.addActionListener(e -> loadBooks(searchField.getText()));

        booksItem.addActionListener(e -> 
        {
            dispose();
            new Book(role, userEmail).setVisible(true);
        });

        registerItem.addActionListener(e -> 
        {
            dispose();
            new LibraryRegistration(role, userEmail).setVisible(true);
        });

        studentItem.addActionListener(e -> 
        {
            dispose();
            new LibraryStudent(role, userEmail).setVisible(true);
        });

        backBtn.addActionListener(e -> 
        {
            dispose();
            Home.getInstance(role, userEmail).setVisible(true);
        });
    }

    void openPDF() 
    {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) 
        {
            JOptionPane.showMessageDialog(this, "Take One");
            return;
        }

        String path = (String) model.getValueAt(selectedRow, 4);
        try {
            if (path != null && !path.isEmpty()) 
            {
                Desktop.getDesktop().open(new java.io.File(path));
            } else 
            {
                JOptionPane.showMessageDialog(this, "No File");
            }
        } catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(this, "Unable" + ex.getMessage());
        }
    }

    void loadBooks(String keyword) 
    {
        model.setRowCount(0);
        boolean searchByID = false;
        int bookID = -1;
        try {
            bookID = Integer.parseInt(keyword);
            searchByID = true;
        } catch (NumberFormatException e) {
            searchByID = false;
        }

        try (Connection con = ConnectionProvider.getConnection()) 
        {
            if (searchByID) {
                String query = "SELECT * FROM Book WHERE Book_ID = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, bookID);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("Book_ID"),
                        rs.getString("Name"),
                        rs.getString("Author"),
                        rs.getString("Department"),
                        rs.getString("File")
                    });
                }
            } else {
                String query = "SELECT * FROM Book WHERE Name LIKE ? OR Author LIKE ? OR Department LIKE ?";
                PreparedStatement ps = con.prepareStatement(query);
                String like = "%" + keyword + "%";
                ps.setString(1, like);
                ps.setString(2, like);
                ps.setString(3, like);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("Book_ID"),
                        rs.getString("Name"),
                        rs.getString("Author"),
                        rs.getString("Department"),
                        rs.getString("File")
                    });
                }
            }

            if (searchByID && table.getRowCount() > 0) {
                table.setRowSelectionInterval(0, 0);
                table.scrollRectToVisible(table.getCellRect(0, 0, true));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error " + e.getMessage());
        }
    }

    void addBook() 
    {
        String title = titleField.getText();
        String author = authorField.getText();
        String dept = deptField.getText();

        if (title.isEmpty() || author.isEmpty() || dept.isEmpty() || filePath.isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Fill in All");
            return;
        }

        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO Book (Name, Author, Department, File) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, dept);
            ps.setString(4, filePath);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "STORE");
            clearFields();
            loadBooks("");
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error " + e.getMessage());
        }
    }

    void deleteBook() 
    {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) 
        {
            JOptionPane.showMessageDialog(this, "Take One");
            return;
        }
        int id = (int) model.getValueAt(selectedRow, 0);
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM Book WHERE Book_ID = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "DELETE");
            loadBooks("");
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error " + e.getMessage());
        }
    }

    void clearFields() 
    {
        titleField.setText("");
        authorField.setText("");
        deptField.setText("");
        filePath = "";
    }
    public static void main(String[] args) 
    {
        new Library("LibraryOfficer", "mizan@gmail.com").setVisible(true);
    }
    public class getInstance {

        public void setVisible(boolean b) {
            throw new UnsupportedOperationException("Unimplemented method 'setVisible'");
        }
    }
}
