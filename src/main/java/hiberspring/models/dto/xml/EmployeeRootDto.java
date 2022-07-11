package hiberspring.models.dto.xml;

import hiberspring.models.Employee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeRootDto {

    @XmlElement(name = "employee")
    private List<EmployeeDto> employees;

    public EmployeeRootDto() {
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public EmployeeRootDto setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
        return this;
    }
}
