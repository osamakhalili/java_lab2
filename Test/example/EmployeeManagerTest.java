package example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

;
class EmployeeManagerTest {



    @Test
    @DisplayName("test PayEmployee method For  EmptyList ")
    void PayEmployeeForEmptyList  () {
        EmployeeRepository repo =  mock(EmployeeRepository.class);
        List <Employee> list = new ArrayList<Employee>();

        when(repo.findAll()).thenReturn(list);

        BankService bankService = mock(BankService.class);
        doNothing().when(bankService).pay(isA(String.class),isA(Double.class));
        int payments = (new EmployeeManager(repo,bankService)).payEmployees();
        verify(bankService,times(0)).pay(isA(String.class),isA(Double.class));
        verify(repo,times(1)).findAll();
        assertThat(payments).isEqualTo(0);
    }

    @Test
    @DisplayName("test PayEmployee method ")
    void PayEmployee  () {
        EmployeeRepository repo =  mock(EmployeeRepository.class);
        List <Employee> list = new ArrayList<Employee>();
        Employee e1 = new Employee("123",40000);
        Employee e2 = new Employee("133",30000);
        list.add(e1);
        list.add(e2);

        when(repo.findAll()).thenReturn(list);

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
        EmployeeRepository repo =  mock(EmployeeRepository.class);
        List <Employee> list = new ArrayList<Employee>();
        Employee e1 = new Employee("123",40000);
        Employee e2 = new Employee("133",30000);
        list.add(e1);
        list.add(e2);

        when(repo.findAll()).thenReturn(list);
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
 @DisplayName("test PayEmployee method For  is Not Empty using Test double")
void testPayMethodUsingDoublesWhenEmployeesIsNotEmpty () {
     List<Employee> employeesList = new ArrayList<Employee>();
     employeesList.add(new Employee("1",40000));
     employeesList.add(new Employee("2",30000));
     employeesList.add(new Employee("4",20000));

        EmployeeRepositoryTest employeeRepositoryTest = new EmployeeRepositoryTest(employeesList);
        BankServiceTest bankServiceTest = new BankServiceTest() ;
        EmployeeManager employeeManager = new EmployeeManager(employeeRepositoryTest,bankServiceTest);

        int actualPayment = employeeManager.payEmployees();
        int expectedPayment = 3;
        assertThat(actualPayment).isEqualTo(expectedPayment);
        assertThat(employeesList.get(0).isPaid()).isEqualTo(true);
}


}