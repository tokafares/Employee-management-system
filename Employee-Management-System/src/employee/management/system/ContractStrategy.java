/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employee.management.system;

public class ContractStrategy implements PayrollStrategy {
    @Override
    public double calculateSalary(double baseSalary) {
        return baseSalary + getBonus(baseSalary) - getDeductions(baseSalary);
    }

    private double getBonus(double baseSalary) {
        return 0.1 * baseSalary; // 10% bonus
    }

    private double getDeductions(double baseSalary) {
        return 0.05 * baseSalary; // 5% deductions
    }
}