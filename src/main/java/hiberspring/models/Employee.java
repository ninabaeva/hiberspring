package hiberspring.models;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity{


    //•	Card – an EmployeeCard, could be any EmployeeCard. Must be UNIQUE though.



    private String firstName;
    private String lastName;
    private String position;
    private EmployeeCard card;
    private Branch branch;

    public Employee() {
    }

    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public Employee setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public Employee setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Column(nullable = false)
    public String getPosition() {
        return position;
    }

    public Employee setPosition(String position) {
        this.position = position;
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "card_id")
    public EmployeeCard getCard() {
        return card;
    }

    public Employee setCard(EmployeeCard card) {
        this.card = card;
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "branch_id")
    public Branch getBranch() {
        return branch;
    }

    public Employee setBranch(Branch branch) {
        this.branch = branch;
        return this;
    }
}
