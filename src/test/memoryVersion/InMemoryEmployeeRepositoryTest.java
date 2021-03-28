package memoryVersion;

import example.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryEmployeeRepositoryTest {

    InMemoryEmployeeRepository repo = new InMemoryEmployeeRepository(List.of(
            new Employee("John",20000),
            new Employee("Duo",45000),
            new Employee("Nar",30000)
    ));

    @Test
    @DisplayName("test findAll when employee table is not empty")
    void findAll() {
        List<Employee> employees = repo.findAll();
        assertThat(employees.size()).isNotEqualTo(0);
    }

    @Test
    @DisplayName("test save method with a new employee")
    void saveNewEmplyee() {
        Employee toInsertEmployee = new Employee("Baflov",45000.0);
        //
        Employee insertedEmployee =  repo.save(toInsertEmployee);
        //
        List<Employee> all = repo.findAll();
        //
        assertThat(insertedEmployee.toString()).isEqualTo(toInsertEmployee.toString());
        assertThat(all.stream().map(c->c.toString())).contains(toInsertEmployee.toString());
    }

    @Test
    @DisplayName("test save method with a existed employee in db")
    void saveExistedEmployee() {
        // insert a new employee not existed in db
        Employee newEmployee = new Employee("AD",45000.0);
        Employee insertedNewEmployee =  repo.save(newEmployee);
        //insert the employee who is existed in db with different salary
        Employee existedEmployee = new Employee("AD",60000);
        Employee savedExisted = repo.save(existedEmployee);
        //
        List<Employee> all = repo.findAll();

        assertThat(all.stream().map(c->c.toString())).contains(existedEmployee.toString());
        assertThat(all.stream().map(c->c.toString())).doesNotContain(newEmployee.toString());
    }

}