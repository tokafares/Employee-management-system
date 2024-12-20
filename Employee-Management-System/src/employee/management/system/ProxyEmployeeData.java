/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employee.management.system;

/**
 *
 * @author Toka Fares
 */
public class ProxyEmployeeData implements EmployeeData {
    private RealEmployeeData realEmployeeData;

    public ProxyEmployeeData(RealEmployeeData realEmployeeData) {
        this.realEmployeeData = realEmployeeData;
    }

    @Override
    public void updateEmployee(String empId, Employee updatedEmployee) {
        // Any validation or additional logic before forwarding to the real employee data
        realEmployeeData.updateEmployee(empId, updatedEmployee);
    }
}
