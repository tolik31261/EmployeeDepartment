package controller;

import exceptions.DepartmentNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.DepartmentsService;

@RestController
@RequestMapping("/department")
public class DepartmentsController {

    private final DepartmentsService departmentsService;

    public DepartmentsController(DepartmentsService departmentsService) {
        this.departmentsService = departmentsService;
    }

    @RequestMapping(value = "/{id}/employees", method = RequestMethod.GET)
    public String employeesInDep(@PathVariable String id){
        try{
            return departmentsService.getEmployeesInDep(Integer.parseInt(id)).toString();
        } catch (DepartmentNotFoundException e){
            return "Department with this \"id\" does not exists";
        }
    }

    @RequestMapping(value = "/{id}/salary/sum", method = RequestMethod.GET)
    public String salarySumInDep(@PathVariable String id){
        try{
            return Integer.toString(departmentsService.getSalarySumInDep(Integer.parseInt(id)));
        } catch (DepartmentNotFoundException e){
            return "Department with this \"id\" does not exists";
        }
    }

    @RequestMapping(value = "/{id}/salary/min", method = RequestMethod.GET)
    public String minSalaryInDep(@PathVariable String id){
        try{
            return Integer.toString(departmentsService.getMinSalaryInDep(Integer.parseInt(id)));
        } catch (DepartmentNotFoundException e){
            return "Department with this \"id\" does not exists";
        }
    }

    @RequestMapping(value = "/{id}/salary/max", method = RequestMethod.GET)
    public String maxSalaryInDep(@PathVariable String id){
        try{
            return Integer.toString(departmentsService.getMaxSalaryInDep(Integer.parseInt(id)));
        } catch (DepartmentNotFoundException e){
            return "Department with this \"id\" does not exists";
        }
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public String employeesGroupedByDeps(){
        return departmentsService.getEmployeesGroupedByDeps().toString();
    }
}
