package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    // JUnit test for saveEmployee
    @Test
    @Order(1)
    @Rollback(value = false)
    public void testSaveEmployee(){

        Employee employee = Employee.builder()
                .firstName("Lahiru")
                .lastName("Viet")
                .email("lahiru@gmail.com")
                .build();

        employeeRepository.save(employee);
        Assertions.assertThat(employee.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void testGetEmployee(){

        Employee employee = employeeRepository.findById(1L).get();
        Assertions.assertThat(employee.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    public void testGetListOfEmployees(){

        List<Employee> employees = employeeRepository.findAll();
        Assertions.assertThat(employees.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    public void testUpdateEmployee(){

        Employee employee = employeeRepository.findById(1L).get();
        employee.setEmail("viet@gmail.com");

        Employee employeeUpdated =  employeeRepository.save(employee);
        Assertions.assertThat(employeeUpdated.getEmail()).isEqualTo("viet@gmail.com");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteEmployeeTest(){

        Employee employee = employeeRepository.findById(1L).get();
        employeeRepository.delete(employee);
        //employeeRepository.deleteById(1L);
        Employee deletedEmployee = null;

        Optional<Employee> optionalEmployee = employeeRepository.findByEmail("lahiru@gmail.com");
        if(optionalEmployee.isPresent()){
            deletedEmployee = optionalEmployee.get();
        }
        Assertions.assertThat(deletedEmployee).isNull();
    }

}
