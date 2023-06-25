package controller;

import exceptions.BadRequestException;
import exceptions.EmployeeAlreadyAddedException;
import exceptions.EmployeeNotFoundException;
import exceptions.EmployeeStorageIsFullException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.EmployeeService;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/employee/add")
    public String addEmployee(@RequestParam("firstName") String firstName,
                              @RequestParam("lastName") String lastName,
                              @RequestParam("salary") int salary,
                              @RequestParam("departamentId") int departamentId) {
        try {
            employeeService.addEmployee(firstName, lastName, salary, departamentId);
            return "Employee " + firstName + lastName + " added.";
        } catch (EmployeeStorageIsFullException e) {
            return "Невозможно добавить сотрудника. Список переполнен.";
        } catch (EmployeeAlreadyAddedException e) {
            return "Невозможно добавить сотрудника. Сотруник с таким именем уже есть в списке.";
        } catch (BadRequestException e){
            return "Bad request";
        }
    }

    @GetMapping(path = "/employee/remove")
    public String removeEmployee(@RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName) {
        try {
            return "Deleted " + employeeService.removeEmployee(firstName, lastName).toString();
        } catch (EmployeeNotFoundException e) {
            return "Сотрудник с именем " + firstName + " " + lastName + " не найден.";
        }
    }

    @GetMapping(path = "/employee/find")
    public String findEmployee(@RequestParam("firstName") String firstName,
                               @RequestParam("lastName") String lastName) {
        try {
            return "Результат поиска: " + employeeService.findEmployee(firstName, lastName).toString();
        } catch (EmployeeNotFoundException e) {
            return "Результат поиска: сотрудник с именем " + firstName + " " + lastName + " не найден.";
        }
    }

    @GetMapping(path = "/employee/all")
    public String printEmployees(){
        return  employeeService.getAll().toString();
    }

    @GetMapping(path = "/employee/departaments/max-salary")
    public String employeeWithMaxSalaryInDep(@RequestParam ("departamentId") int departamentId){
        return employeeService.salaryMaxInDepartament(departamentId).toString();
    }

    @GetMapping(path = "/employee/departaments/min-salary")
    public String employeeWithMinSalaryInDep(@RequestParam ("departamentId") int departamentId){
        return employeeService.salaryMinInDepartament(departamentId).toString();
    }

    @GetMapping(path = "/employee/departaments/all")
    public String printEmployeesOfDep(@RequestParam("departamentId") int departamentId){
        return employeeService.printEmployeesOfDep(departamentId).toString();
    }

}

