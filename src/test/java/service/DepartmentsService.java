package service;

import employee.Employee;
import exceptions.DepartmentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DepartmentsServiceTest {

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    DepartmentsService departmentsService;
    List<Employee> employees = Arrays.asList(
            new Employee("Egor", "Irt", 500, 1),
            new Employee("Vlad", "Ten", 1500, 1),
            new Employee("Grisha", "Ops", 1000, 2),
            new Employee("Oleg", "N", 9000, 3),
            new Employee("Nikolai", "Nik", 2500, 2),
            new Employee("Pavel", "Pav", 10000, 2),
            new Employee("Maria", "Mare", 1000, 3)
    );

    @BeforeEach
    void setup(){
        Mockito.when(employeeService.getAll()).thenReturn(employees);
    }

    @Test
    void getEmployeesOfDep(){
        List<Employee> expected = Arrays.asList(
                new Employee("Egor", "Irt", 500, 1),
                new Employee("Vlad", "Ten", 1500, 1));
        List<Employee> actual = departmentsService.getEmployeesInDep(1);

        assertEquals(expected, actual);
        assertThrows(DepartmentNotFoundException.class,
                () -> departmentsService.getEmployeesInDep(DepartmentsService.getCurrentDepartmentsCount() + 1));
    }

    @Test
    void getSalarySumInDep() {
        int expected = 2000;
        int actual = departmentsService.getSalarySumInDep(1);

        assertEquals(expected, actual);
        assertThrows(DepartmentNotFoundException.class,
                () -> departmentsService.getEmployeesInDep(DepartmentsService.getCurrentDepartmentsCount() + 1));
    }

    @Test
    void getMinSalaryInDep(){
        int expected = 500;
        int actual = departmentsService.getMinSalaryInDep(1);

        assertEquals(expected, actual);
        assertThrows(DepartmentNotFoundException.class,
                () -> departmentsService.getEmployeesInDep(DepartmentsService.getCurrentDepartmentsCount() + 1));
    }

    @Test
    void getMaxSalaryInDep(){
        int expected = 1500;
        int actual = departmentsService.getMaxSalaryInDep(1);

        assertEquals(expected, actual);
        assertThrows(DepartmentNotFoundException.class,
                () -> departmentsService.getEmployeesInDep(DepartmentsService.getCurrentDepartmentsCount() + 1));
    }

    @Test
    void getEmployeesGroupedByDeps() {
        List<Integer> expected = employees.stream()
                .map(Employee::getDepartmentId)
                .distinct()
                .collect(Collectors.toList());
        Map<Integer, List<Employee>> actual = departmentsService.getEmployeesGroupedByDeps();
        assertEquals(expected.size(), actual.keySet().size());
        assertTrue(expected.containsAll(actual.keySet()));
    }
}