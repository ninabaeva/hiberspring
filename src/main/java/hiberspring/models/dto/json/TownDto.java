package hiberspring.models.dto.json;

import com.google.gson.annotations.Expose;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TownDto {

    @Expose
    private String name;
    @Expose
    private int population;

    public TownDto() {
    }

    @NotNull
    public String getName() {
        return name;
    }

    public TownDto setName(String name) {
        this.name = name;
        return this;
    }

    @NotNull
    @Min(value = 1)
    public int getPopulation() {
        return population;
    }

    public TownDto setPopulation(int population) {
        this.population = population;
        return this;
    }
}
