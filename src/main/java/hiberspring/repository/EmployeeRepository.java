package hiberspring.repository;

import hiberspring.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.branch.products.size > 0" +
            "ORDER BY concat(e.firstName, ' ', e.lastName) ASC, length(e.position) DESC")
    Set<Employee> export();
}
