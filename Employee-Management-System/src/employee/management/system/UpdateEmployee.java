package employee.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateEmployee extends JFrame implements ActionListener {
    JTextField tfname, tfdob, tffather_name, tfaddress, tfphone, tfemail, tfsalary, tfdesignation, tfeducation, tfempId;
    JButton update, back;
    String empId;

    // Constructor
    UpdateEmployee(String empId) {
        this.empId = empId;
        setTitle("Update Employee Details");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Heading
        JLabel heading = new JLabel("Update Employee Details");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);

        // Name
        JLabel labelName = new JLabel("Name:");
        labelName.setBounds(50, 150, 150, 30);
        labelName.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelName);

        tfname = new JTextField();
        tfname.setBounds(200, 150, 150, 30);
        add(tfname);

        // Father's Name
        JLabel labelFName = new JLabel("Father's Name:");
        labelFName.setBounds(400, 150, 150, 30);
        labelFName.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelFName);

        tffather_name = new JTextField();
        tffather_name.setBounds(600, 150, 150, 30);
        add(tffather_name);

        // Date of Birth
        JLabel labelDOB = new JLabel("Date of Birth:");
        labelDOB.setBounds(50, 200, 150, 30);
        labelDOB.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelDOB);

        tfdob = new JTextField();
        tfdob.setBounds(200, 200, 150, 30);
        add(tfdob);

        // Salary
        JLabel labelSalary = new JLabel("Salary:");
        labelSalary.setBounds(400, 200, 150, 30);
        labelSalary.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelSalary);

        tfsalary = new JTextField();
        tfsalary.setBounds(600, 200, 150, 30);
        add(tfsalary);

        // Address
        JLabel labelAddress = new JLabel("Address:");
        labelAddress.setBounds(50, 250, 150, 30);
        labelAddress.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelAddress);

        tfaddress = new JTextField();
        tfaddress.setBounds(200, 250, 150, 30);
        add(tfaddress);

        // Phone
        JLabel labelPhone = new JLabel("Phone:");
        labelPhone.setBounds(400, 250, 150, 30);
        labelPhone.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelPhone);

        tfphone = new JTextField();
        tfphone.setBounds(600, 250, 150, 30);
        add(tfphone);

        // Email
        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setBounds(50, 300, 150, 30);
        labelEmail.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelEmail);

        tfemail = new JTextField();
        tfemail.setBounds(200, 300, 150, 30);
        add(tfemail);

        // Education
        JLabel labelEducation = new JLabel("Education:");
        labelEducation.setBounds(400, 300, 150, 30);
        labelEducation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelEducation);

        tfeducation = new JTextField();
        tfeducation.setBounds(600, 300, 150, 30);
        add(tfeducation);

        // Designation
        JLabel labelDesignation = new JLabel("Designation:");
        labelDesignation.setBounds(50, 350, 150, 30);
        labelDesignation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelDesignation);

        tfdesignation = new JTextField();
        tfdesignation.setBounds(200, 350, 150, 30);
        add(tfdesignation);

        // Employee ID
        JLabel labelEmpId = new JLabel("Employee ID:");
        labelEmpId.setBounds(50, 400, 150, 30);
        labelEmpId.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelEmpId);

        tfempId = new JTextField();
        tfempId.setBounds(200, 400, 150, 30);
        tfempId.setEditable(false); // Make Employee ID non-editable
        add(tfempId);

        // Fetch Employee Details
        fetchEmployeeDetails();

        // Buttons
        update = new JButton("Update Details");
        update.setBounds(250, 550, 150, 40);
        update.addActionListener(this);
        add(update);

        back = new JButton("Back");
        back.setBounds(450, 550, 150, 40);
        back.addActionListener(this);
        add(back);

        setSize(900, 700);
        setLocation(300, 50);
        setVisible(true);
    }

    private void fetchEmployeeDetails() {
        try {
            Conn c = Conn.getInstance();
            String query = "SELECT * FROM employee WHERE empId = ?";
            PreparedStatement pstmt = c.c.prepareStatement(query);
            pstmt.setString(1, empId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tfname.setText(rs.getString("name"));
                tfdob.setText(rs.getString("dob"));
                tffather_name.setText(rs.getString("father_name"));
                tfaddress.setText(rs.getString("address"));
                tfsalary.setText(rs.getString("base_salary"));
                tfphone.setText(rs.getString("phone"));
                tfemail.setText(rs.getString("email"));
                tfeducation.setText(rs.getString("education"));
                tfdesignation.setText(rs.getString("designation"));
                tfempId.setText(rs.getString("empId"));
            } else {
                JOptionPane.showMessageDialog(null, "Employee not found!");
                setVisible(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching employee details.");
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            try {
                // Create Employee object using the data entered in text fields
                Employee updatedEmployee = new Employee.Builder()
                        .setName(tfname.getText())
                        .setFatherName(tffather_name.getText())
                        .setDob(tfdob.getText())
                        .setSalary(tfsalary.getText())
                        .setAddress(tfaddress.getText())
                        .setPhone(tfphone.getText())
                        .setEmail(tfemail.getText())
                        .setEducation(tfeducation.getText())
                        .setDesignation(tfdesignation.getText())
                        .setEmpId(empId)
                        .build();

                // Use Proxy to update employee details
                RealEmployeeData realEmployeeData = new RealEmployeeData();
                ProxyEmployeeData proxyEmployeeData = new ProxyEmployeeData(realEmployeeData);
                proxyEmployeeData.updateEmployee(empId, updatedEmployee);

                JOptionPane.showMessageDialog(null, "Details updated successfully.");
                setVisible(false);
                new Home();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error updating employee details.");
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new UpdateEmployee("E001"); // Replace "E001" with a valid Employee ID for testing
    }
}
