package hiberspring.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "branch")
public class Branch extends BaseEntity {




    private String name;
    private Town town;
    private Set<Product> products;

    public Branch() {
    }

    @Column(unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public Branch setName(String name) {
        this.name = name;
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "town_id")
    public Town getTown() {
        return town;
    }

    public Branch setTown(Town town) {
        this.town = town;
        return this;
    }

    @OneToMany(mappedBy = "branch")
    public Set<Product> getProducts() {
        return products;
    }

    public Branch setProducts(Set<Product> products) {
        this.products = products;
        return this;
    }
}
