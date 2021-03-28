package memoryVersion;

import example.Employee;
import example.EmployeeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryEmployeeRepository implements EmployeeRepository {
    private static Connection c;

   public InMemoryEmployeeRepository(){
        try {
            if(c!=null) return;

            c = DriverManager.getConnection("jdbc:h2:mem:mymemdb.db", "SA", "");
            c.prepareStatement("create table employee (id varchar(100) , salary double)").execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public InMemoryEmployeeRepository(List<Employee> employees){
        try {
            if(c!=null) return;

            c = DriverManager.getConnection("jdbc:h2:mem:mymemdb.db", "SA", "");
            c.prepareStatement("create table employee (id varchar(100) , salary double)").execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Employee emp:employees){
            this.save(emp);
        }
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> allEmployees = new ArrayList<>();
        try {
            PreparedStatement ps = c.prepareStatement("select * from employee");

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String id = rs.getString("id");
                double salary = rs.getDouble("salary");
                allEmployees.add(new Employee(id, salary));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return allEmployees;
        }
    }

    @Override
    public Employee save(Employee e) {
       // check employee is exist in the db
       boolean isExist = false;
       List<Employee> allEmployees =this.findAll();
        for (Employee emp:allEmployees) {
            if(emp.getId().equals(e.getId())){
                isExist = true;
                break;
            }
        }
         String query = isExist?"UPDATE employee SET salary=? WHERE id=?":"insert into employee (salary,id) values (?,?)";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setDouble(1, e.getSalary());
            ps.setString(2, e.getId());
            ps.execute();
            c.commit();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return this.findById(e.getId());
    }

    public void close() {
        try {
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Employee findById(String id) {
        try {
            PreparedStatement ps = c.prepareStatement("select * from employee where id=?");
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            String empId="";
            double empSalary = 0;
            while(rs.next()) {
              empId = rs.getString("id");
               empSalary = rs.getDouble("salary");
            }
          Employee employee = new Employee(empId,empSalary);
        return  employee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
