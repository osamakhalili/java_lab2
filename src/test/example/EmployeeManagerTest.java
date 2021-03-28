package example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class EmployeeManagerTest {
    @Test
    @DisplayName("Test payEmployees when Employees array is Empty")
    void testPayEmployeeForEmptyList(){
        EmployeeRepository repo = mock(EmployeeRepository.class);
        when(repo.findAll()).thenReturn(List.of());
        BankService bankService = mock(BankService.class);

        doNothing().when(bankService).pay(isA(String.class),isA(Double.class));
        int payments = new EmployeeManager(repo,bankService).payEmployees();
        // check number of calls to bankService.pay method
        verify(bankService,times(0)).pay(isA(String.class),isA(Double.class));
        // check number of calls to repo.findAll()
        verify(repo,times(1)).findAll();

        assertThat(payments).isEqualTo(0);
    }
    @Test
    @DisplayName("Test payEmployees when employee repo have more than one and the connection with bankService is ok")
    void testPayEmployee(){
        EmployeeRepository repo = mock(EmployeeRepository.class);
        when(repo.findAll()).thenReturn(List.of(
                new Employee("123",25000),
                new Employee("223",30000)
        ));
        BankService bankService = mock(BankService.class);
        doNothing().when(bankService).pay(isA(String.class),isA(Double.class));
        int payments = (new EmployeeManager(repo,bankService)).payEmployees();
        verify(bankService,times(2)).pay(isA(String.class),isA(Double.class));
        verify(repo,times(1)).findAll();

        assertThat(repo.findAll().stream().filter(c->c.isPaid()==true)
                .collect(Collectors.toList())
                .size())
                .isEqualTo(repo.findAll().size());

        assertThat(payments).isEqualTo(2);
    }
    @Test
    @DisplayName("Test payEmployees when employee repo have more than one and the connection with bankService is failed")
    void testPayEmployeeWithFailBankServiceConnection(){
        EmployeeRepository repo = mock(EmployeeRepository.class);
        when(repo.findAll()).thenReturn(List.of(
                new Employee("123",25000),
                new Employee("223",30000)
        ));
        BankService bankService = mock(BankService.class);

        doThrow(RuntimeException.class).when(bankService).pay(isA(String.class),isA(Double.class));

        int payments = (new EmployeeManager(repo,bankService)).payEmployees();
        verify(bankService,times(2)).pay(isA(String.class),isA(Double.class));
        verify(repo,times(1)).findAll();

        assertThat(repo.findAll().stream().filter(c->c.isPaid()==false)
                .collect(Collectors.toList())
                .size())
                .isEqualTo(repo.findAll().size());
        assertThat(payments).isEqualTo(0);
    }

    @Test
    @DisplayName("Test payEmployees when Employees array is Empty using Test doubles")
    void testPayMethodUsingDoublesWhenEmployeesIsEmpty(){
        EmployeeRepositoryImp employeeRepositoryTest = new EmployeeRepositoryImp(new ArrayList<Employee>());
        BankServiceImp bankServiceImp = new BankServiceImp();

        EmployeeManager employeeManager = new EmployeeManager(employeeRepositoryTest, bankServiceImp);

        int  actualPayment = employeeManager.payEmployees();
        int expectedPayment = 0;

        assertThat(actualPayment).isEqualTo(expectedPayment);
    }

    @Test
    @DisplayName("Test payEmployees when Employees array is  not Empty and connection with Bank service is ok using Test doubles")
    void testPayMethodUsingDoublesWhenEmployeesIsNotEmpty(){
        List<Employee> employeesList = new ArrayList<Employee>();
        employeesList.add(new Employee("1",30000));
        employeesList.add(new Employee("2",45000));
        employeesList.add(new Employee("3",80000));

        EmployeeRepositoryImp employeeRepositoryTest = new EmployeeRepositoryImp(employeesList);
        BankServiceImp bankServiceImp = new BankServiceImp();

        EmployeeManager employeeManager = new EmployeeManager(employeeRepositoryTest, bankServiceImp);

        int  actualPayment = employeeManager.payEmployees();
        int expectedPayment = 3;

        assertThat(actualPayment).isEqualTo(expectedPayment);
        assertThat(employeesList.get(0).isPaid()).isEqualTo(true);
    }

}