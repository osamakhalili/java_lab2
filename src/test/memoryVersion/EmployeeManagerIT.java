package memoryVersion;

import example.BankService;
import example.Employee;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeManagerIT {
@Test
    void payEmployees() {

    InMemoryEmployeeRepository repo = new InMemoryEmployeeRepository(List.of(
            new Employee("John",20000),
            new Employee("Duo",45000),
            new Employee("Nar",30000)
    ));

    BankService bankService = new BankService() {
        @Override
        public void pay(String id, double amount) {

        }
    };
        List<Employee> employees = repo.findAll();
        int payments = 0;
        for (Employee employee : employees) {
            try {
                bankService.pay(employee.getId(), employee.getSalary());
                employee.setPaid(true);
                payments++;
            } catch (RuntimeException e) {
                employee.setPaid(false);
            }
        }
        int actual =  payments;
        int expected = 3;

        assertThat(actual).isEqualTo(expected);

    }
}
