package hiberspring.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "branch")
@XmlAccessorType(XmlAccessType.FIELD)
public class BranchXmlDto {

    private String name;


    public String getName() {
        return name;
    }

    public BranchXmlDto setName(String name) {
        this.name = name;
        return this;
    }
}
