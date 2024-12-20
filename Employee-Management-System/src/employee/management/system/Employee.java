/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employee.management.system;

public class Employee implements Cloneable {
    private String name;
    private String fatherName;
    private String dob;
    private String salary;
    private String address;
    private String phone;
    private String email;
    private String education;
    private String designation;
    private String empId;

    private Employee(Builder builder) {
        this.name = builder.name;
        this.fatherName = builder.fatherName;
        this.dob = builder.dob;
        this.salary = builder.salary;
        this.address = builder.address;
        this.phone = builder.phone;
        this.email = builder.email;
        this.education = builder.education;
        this.designation = builder.designation;
        this.empId = builder.empId;
    }

    // Prototype method - clone the current object
    @Override
    public Employee clone() {
        try {
            // Perform a shallow copy for simple objects
            return (Employee) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    String getFatherName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static class Builder {
        private String name;
        private String fatherName;
        private String dob;
        private String salary;
        private String address;
        private String phone;
        private String email;
        private String education;
        private String designation;
        private String empId;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setFatherName(String fatherName) {
            this.fatherName = fatherName;
            return this;
        }

        public Builder setDob(String dob) {
            this.dob = dob;
            return this;
        }

        public Builder setSalary(String salary) {
            this.salary = salary;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setEducation(String education) {
            this.education = education;
            return this;
        }

        public Builder setDesignation(String designation) {
            this.designation = designation;
            return this;
        }


        public Builder setEmpId(String empId) {
            this.empId = empId;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }

    @Override
    public String toString() {
        return "Employee [name=" + name + ", fatherName=" + fatherName + ", dob=" + dob + ", salary=" + salary
                + ", address=" + address + ", phone=" + phone + ", email=" + email + ", education=" + education
                + ", designation=" + designation + ", empId=" + empId + "]";
    }
}