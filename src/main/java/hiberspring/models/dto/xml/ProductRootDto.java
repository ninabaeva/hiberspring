package hiberspring.models.dto.xml;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductRootDto {


    @XmlElement(name = "product")
    private List<ProductDto> products;

    public ProductRootDto() {
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public ProductRootDto setProducts(List<ProductDto> products) {
        this.products = products;
        return this;
    }
}
