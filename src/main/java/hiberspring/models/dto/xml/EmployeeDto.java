package hiberspring.models.dto.xml;

import hiberspring.models.Branch;
import hiberspring.models.EmployeeCard;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeDto {

    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlAttribute
    private String position;
    @XmlElement
    private String card;
    @XmlElement
    private String branch;

    public EmployeeDto() {
    }

    @NotNull
    public String getFirstName() {
        return firstName;
    }

    public EmployeeDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @NotNull
    public String getLastName() {
        return lastName;
    }

    public EmployeeDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @NotNull
    public String getPosition() {
        return position;
    }

    public EmployeeDto setPosition(String position) {
        this.position = position;
        return this;
    }

    @NotNull
    public String getCard() {
        return card;
    }

    public EmployeeDto setCard(String card) {
        this.card = card;
        return this;
    }

    @NotNull
    public String getBranch() {
        return branch;
    }

    public EmployeeDto setBranch(String branch) {
        this.branch = branch;
        return this;
    }
}
