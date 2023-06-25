package service;

import employee.Employee;
import exceptions.BadRequestException;
import exceptions.EmployeeAlreadyAddedException;
import exceptions.EmployeeNotFoundException;
import exceptions.EmployeeStorageIsFullException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope("singleton")
public class EmployeeService {
    List<Employee> employees = new ArrayList<>();
    List<Employee> firedEmployees = new ArrayList<>();
    private int allowedEmployeesCount = 10;


    public void addEmployee(String firstName, String lastName, int salary, int departamentId){
        checkEmployeeData(firstName, lastName);
        if(employees.size() >= allowedEmployeesCount){
            throw new EmployeeStorageIsFullException("Нельзя добавить сотрудника. Коллекция переполнена");
        }
        for (Employee value : employees) {
            if (value.getFirstName().equals(firstName) && value.getLastName().equals(lastName)) {
                throw new EmployeeAlreadyAddedException("Сотрудник с таким именем уже есть в коллекции");
            }
        }
        Employee employee = new Employee(StringUtils.capitalize(firstName), StringUtils.capitalize(lastName), salary, departamentId);
        employees.add(employee);
    }

    public void addEmployee(Employee employee){
        if(employees.size() >= allowedEmployeesCount){
            throw new EmployeeStorageIsFullException("Нельзя добавить сотрудника. Коллекция переполнена");
        }
        employees.add(employee);
    }

    public Employee removeEmployee(String firstName, String lastName) {
        Employee employee = null;
        for (Employee value : employees) {
            if (value.getFirstName().equals(firstName) && value.getLastName().equals(lastName)) {
                employee = value;
            }
        }
        if (employee == null){
            throw new EmployeeNotFoundException("Сотрудник не найден в коллекции");
        } else {
            employees.remove(employee);
        }
        return employee;
    }


    public Employee findEmployee(String firstName, String lastName){
        Employee employee = null;
        for (Employee value : employees) {
            if (value.getFirstName().equals(firstName) && value.getLastName().equals(lastName)) {
                employee = value;
            }
        }
        if (employee == null){
            throw new EmployeeNotFoundException("Сотрудник не найден в коллекции");
        }
        return employee;
    }

    public Employee salaryMinInDepartament(int departament) {
        return employees.stream()
                .filter(e -> e.getDepartmentId() == departament)
                .min(Comparator.comparing(Employee::getSalary))
                .get();
    }

    public Employee salaryMaxInDepartament(int departament) {
        return employees.stream()
                .filter(e -> e.getDepartmentId() == departament)
                .max(Comparator.comparing(Employee::getSalary))
                .get();
    }
    public List<Employee> getAll() {
        return this.employees;
    }


    public List<Employee> printEmployeesOfDep(int departamentId){
        return employees.stream()
                .filter(e -> e.getDepartmentId() == departamentId)
                .collect(Collectors.toList());
    }


    public void checkEmployeeData(String firstName, String lastName){
        if(StringUtils.containsWhitespace(firstName)
                || StringUtils.containsWhitespace(lastName)) {
            throw new BadRequestException();
        } else if(StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
            throw new BadRequestException();
        }
    }

    public void changeEmployeesCountAllowed(int newCountAllowed){
        if(employees.size() > newCountAllowed){
            for(int i = employees.size(); i > newCountAllowed; i--){
                firedEmployees.add(employees.get(i));
                employees.remove(i);
            }
        }
        allowedEmployeesCount = newCountAllowed;
    }


}