package hiberspring.models;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    private String name;
    private int clients;
    private Branch branch;

    public Product() {
    }

    @Column(unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    @Column(nullable = false)
    public int getClients() {
        return clients;
    }

    public Product setClients(int clients) {
        this.clients = clients;
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "branch_id")
    public Branch getBranch() {
        return branch;
    }

    public Product setBranch(Branch branch) {
        this.branch = branch;
        return this;
    }
}
