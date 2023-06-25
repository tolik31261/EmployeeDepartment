package employee;

import exceptions.DepartmentNotFoundException;
import service.DepartmentsService;

import java.util.Objects;

public class Employee {
    private String firstName;
    private String lastName;
    private int salary;
    private int departmentId;

    public Employee(String firstName, String lastName, int salary, int departmentId) {
        this.firstName = firstName;
        this.lastName = lastName;

        if(salary < 0){
            throw new IllegalArgumentException("Salary could not be less than 0");
        } else {
            this.salary = salary;
        }

        if(departmentId <= 0 || departmentId > DepartmentsService.getCurrentDepartmentsCount()){
            throw new DepartmentNotFoundException("Department with this number does not exist");
        } else {
            this.departmentId = departmentId;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }


    @Override
    public String toString() {
        return "{" +
                "Имя = '" + firstName + '\'' +
                ", Фамилия = '" + lastName + '\'' +
                "} ";
    }
}