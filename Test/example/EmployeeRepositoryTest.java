package example;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryTest implements EmployeeRepository{

    public static List<Employee> employees = new ArrayList<Employee>();

    public EmployeeRepositoryTest (List<Employee> employees){

        this.employees=employees;

}
    @Override
    public List<Employee> findAll() {
        return employees;
    }


    @Override
    public Employee save(Employee e) {
        employees.add(e);
        return employees.get(employees.indexOf(e));
    }
}