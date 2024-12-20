/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employee.management.system;

import javax.swing.JOptionPane;

/**
 *
 * @author Toka Fares
 */
public class RealEmployeeData implements EmployeeData {
    @Override
    public void updateEmployee(String empId, Employee updatedEmployee) {
        // Code to update the employee in the database or wherever it needs to be updated
        System.out.println("Updating Employee with ID: " + empId);
        // You can access the updatedEmployee properties like updatedEmployee.getName(), updatedEmployee.getSalary(), etc.
    }
}



