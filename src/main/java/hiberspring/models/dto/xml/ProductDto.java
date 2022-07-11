package hiberspring.models.dto.xml;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductDto {

    @XmlAttribute
    private String name;
    @XmlAttribute
    private int clients;
    @XmlElement
    private String branch;

    public ProductDto() {
    }

    @NotNull
    public String getName() {
        return name;
    }

    public ProductDto setName(String name) {
        this.name = name;
        return this;
    }

    @NotNull
    public int getClients() {
        return clients;
    }

    public ProductDto setClients(int clients) {
        this.clients = clients;
        return this;
    }

    public String getBranch() {
        return branch;
    }

    public ProductDto setBranch(String branch) {
        this.branch = branch;
        return this;
    }
}
