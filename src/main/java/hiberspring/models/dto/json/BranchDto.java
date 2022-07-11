package hiberspring.models.dto.json;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class BranchDto {

    @Expose
    private String name;
    @Expose
    private String town;

    public BranchDto() {
    }


    @NotNull
    public String getName() {
        return name;
    }

    public BranchDto setName(String name) {
        this.name = name;
        return this;
    }

    @NotNull
    public String getTown() {
        return town;
    }

    public BranchDto setTown(String town) {
        this.town = town;
        return this;
    }
}
