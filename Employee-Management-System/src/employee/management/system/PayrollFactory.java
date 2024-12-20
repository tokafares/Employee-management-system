/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employee.management.system;

public class PayrollFactory {
    public static PayrollStrategy getPayrollStrategy(String employeeType) {
        if (employeeType.equalsIgnoreCase("full time")) {
            return new PermanentStrategy();
        } else if (employeeType.equalsIgnoreCase("part time")) {
            return new ContractStrategy();
        }
        throw new IllegalArgumentException("Invalid Employee Type");
    }
}

