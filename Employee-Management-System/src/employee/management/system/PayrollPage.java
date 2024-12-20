package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PayrollPage extends JFrame implements ActionListener {

    JTextField empIdField, baseSalaryField, nameField, bonusField, deductionField;
    JComboBox<String> employeeTypeBox;
    JButton calculate, save, back;

    PayrollPage() {
        setTitle("Payroll Management");
        setLayout(null);

        // Labels and Input Fields
        JLabel heading = new JLabel("Payroll Management");
        heading.setBounds(150, 20, 250, 30);
        heading.setFont(new Font("Raleway", Font.BOLD, 20));
        add(heading);

        JLabel empId = new JLabel("Employee ID:");
        empId.setBounds(50, 70, 150, 30);
        add(empId);

        empIdField = new JTextField();
        empIdField.setBounds(200, 70, 150, 30);
        add(empIdField);

        JLabel name = new JLabel("Employee Name:");
        name.setBounds(50, 110, 150, 30);
        add(name);

        nameField = new JTextField();
        nameField.setBounds(200, 110, 150, 30);
        add(nameField);

        JLabel baseSalary = new JLabel("Base Salary:");
        baseSalary.setBounds(50, 150, 150, 30);
        add(baseSalary);

        baseSalaryField = new JTextField();
        baseSalaryField.setBounds(200, 150, 150, 30);
        add(baseSalaryField);

        // Add Labels and Input Fields for Bonus and Deduction
        JLabel bonusLabel = new JLabel("Bonus:");
        bonusLabel.setBounds(50, 190, 150, 30);
        add(bonusLabel);

        bonusField = new JTextField();
        bonusField.setBounds(200, 190, 150, 30);
        add(bonusField);

        JLabel deductionLabel = new JLabel("Deduction:");
        deductionLabel.setBounds(50, 230, 150, 30);
        add(deductionLabel);

        deductionField = new JTextField();
        deductionField.setBounds(200, 230, 150, 30);
        add(deductionField);

        JLabel type = new JLabel("Employee Type:");
        type.setBounds(50, 270, 150, 30);
        add(type);

        String[] employeeTypes = {"full time", "part time"};
        employeeTypeBox = new JComboBox<>(employeeTypes);
        employeeTypeBox.setBounds(200, 270, 150, 30);
        add(employeeTypeBox);

        // Buttons
        calculate = new JButton("Calculate Salary");
        calculate.setBounds(50, 320, 150, 30);
        calculate.addActionListener(this);
        add(calculate);

        save = new JButton("Save Payroll");
        save.setBounds(210, 320, 150, 30);
        save.addActionListener(this);
        add(save);

        back = new JButton("Back");
        back.setBounds(130, 370, 100, 30);
        back.addActionListener(this);
        add(back);

        setSize(400, 450);
        setLocation(500, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == calculate) {
            if (isValidInput()) {
                String employeeType = (String) employeeTypeBox.getSelectedItem();
                double baseSalary = Double.parseDouble(baseSalaryField.getText());
                double bonus = Double.parseDouble(bonusField.getText());
                double deduction = Double.parseDouble(deductionField.getText());

                // Using PayrollFactory to get the strategy based on selected employee type
                PayrollStrategy strategy = PayrollFactory.getPayrollStrategy(employeeType);
                double totalSalary = strategy.calculateSalary(baseSalary);

                // Calculate the net salary
                double netSalary = totalSalary + bonus - deduction;

                JOptionPane.showMessageDialog(null, "Calculated Salary: " + netSalary);
            }
        } else if (ae.getSource() == save) {
            if (isValidInput()) {
                String empId = empIdField.getText();
                String baseSalary = baseSalaryField.getText();
                String bonus = bonusField.getText();
                String deduction = deductionField.getText();

                try {
                    // Check if the employee exists in the database
                    Conn c = Conn.getInstance();
                    String checkQuery = "SELECT * FROM employee WHERE empId = ?";
                    PreparedStatement checkStmt = c.c.prepareStatement(checkQuery);
                    checkStmt.setString(1, empId);
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next()) {
                        // If employee exists, get and set the employee type
                        String existingType = rs.getString("employee_type");
                        employeeTypeBox.setSelectedItem(existingType);  // Set the employee type from DB

                        // If employee exists, update the salary, bonus, deduction, and net_salary
                        double newBonus = Double.parseDouble(bonus);
                        double newDeduction = Double.parseDouble(deduction);
                        double newBaseSalary = Double.parseDouble(baseSalary);
                        double netSalary = newBaseSalary + newBonus - newDeduction;

                        String updateQuery = "UPDATE employee SET base_salary = ?, bonus = ?,  deductions = ?, net_salary = ?, employee_type = ? WHERE empId = ?";
                        PreparedStatement updateStmt = c.c.prepareStatement(updateQuery);
                        updateStmt.setDouble(1, newBaseSalary);
                        updateStmt.setDouble(2, newBonus);
                        updateStmt.setDouble(3, newDeduction);
                        updateStmt.setDouble(4, netSalary);
                        updateStmt.setString(5, (String) employeeTypeBox.getSelectedItem()); // Update employee type
                        updateStmt.setString(6, empId);
                        updateStmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Salary updated successfully.");
                    } else {
                        // If employee does not exist, insert a new record
                        String insertQuery = "INSERT INTO employee (empId, name, base_salary, bonus,  deductions, net_salary, employee_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement insertStmt = c.c.prepareStatement(insertQuery);
                        insertStmt.setString(1, empId);
                        insertStmt.setString(2, nameField.getText());
                        insertStmt.setDouble(3, Double.parseDouble(baseSalary));
                        insertStmt.setDouble(4, Double.parseDouble(bonus));
                        insertStmt.setDouble(5, Double.parseDouble(deduction));
                        double netSalary = Double.parseDouble(baseSalary) + Double.parseDouble(bonus) - Double.parseDouble(deduction);
                        insertStmt.setDouble(6, netSalary);
                        insertStmt.setString(7, (String) employeeTypeBox.getSelectedItem()); // Set the employee type
                        insertStmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Payroll Saved Successfully");
                    }

                    clearFields();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error saving payroll.");
                    e.printStackTrace();
                }
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    private boolean isValidInput() {
        if (empIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Employee ID cannot be empty.");
            return false;
        }
        if (nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Employee Name cannot be empty.");
            return false;
        }
        if (baseSalaryField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Base Salary cannot be empty.");
            return false;
        }
        try {
            Double.parseDouble(baseSalaryField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Base Salary must be a valid number.");
            return false;
        }

        // Validate bonus and deduction fields
        try {
            Double.parseDouble(bonusField.getText());
            Double.parseDouble(deductionField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Bonus and Deduction must be valid numbers.");
            return false;
        }

        return true;
    }

    private void clearFields() {
        empIdField.setText("");
        nameField.setText("");
        baseSalaryField.setText("");
        bonusField.setText("");
        deductionField.setText("");
        employeeTypeBox.setSelectedIndex(0); // Default to first type
    }

    public static void main(String[] args) {
        new PayrollPage();
    }
}
