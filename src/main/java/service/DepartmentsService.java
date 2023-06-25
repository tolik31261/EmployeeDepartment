package service;

import employee.Employee;
import exceptions.DepartmentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentsService {

    private final EmployeeService employeeService;
    private static int currentDepartmentsCount = 5;

    public DepartmentsService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public List<Employee> getEmployeesInDep(int dep){
        if(dep == 0 || dep > currentDepartmentsCount){
            throw new DepartmentNotFoundException("Wrong department number");
        }
        return employeeService.getAll().stream()
                .filter(e -> e.getDepartmentId() == dep)
                .collect(Collectors.toList());
    }

    public int getSalarySumInDep(int dep){
        if(dep == 0 || dep > currentDepartmentsCount){
            throw new DepartmentNotFoundException("Wrong department number");
        }
        int sum = 0;
        for (Employee employee : employeeService.getAll()) {
            if(employee.getDepartmentId() == dep) {
                sum = sum + employee.getSalary();
            }
        }
        return sum;
    }

    public int getSalarySumOfAllEmployees(){
        int sum = 0;
        for (Employee employee : employeeService.getAll()) {
            sum = sum + employee.getSalary();
        }
        return sum;
    }

    public int getMinSalaryInDep(int dep){
        if(dep == 0 || dep > currentDepartmentsCount){
            throw new DepartmentNotFoundException("Wrong department number");
        }
        Employee employee = employeeService.getAll().stream()
                .filter(e -> e.getDepartmentId() == dep)
                .min(Comparator.comparing(Employee::getSalary))
                .get();
        return employee.getSalary();
    }

    public int getMaxSalaryInDep(int dep){
        if(dep == 0 || dep > currentDepartmentsCount){
            throw new DepartmentNotFoundException("Wrong department number");
        }
        int max = 0;
        for (Employee employee : employeeService.getAll()) {
            if (employee.getDepartmentId() == dep){
                if(employee.getSalary() > max){
                    max = employee.getSalary();
                }
            }
        }
        return max;
    }

    public Map<Integer, List<Employee>> getEmployeesGroupedByDeps(){
        return employeeService.getAll().stream()
                .collect(Collectors.groupingBy(Employee::getDepartmentId));
    }

    public static int getCurrentDepartmentsCount() {
        return currentDepartmentsCount;
    }

    private void createNewDepartment(){
        currentDepartmentsCount++;
    }
}