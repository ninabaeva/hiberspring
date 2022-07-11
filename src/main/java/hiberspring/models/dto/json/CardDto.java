package hiberspring.models.dto.json;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class CardDto {

    @Expose
    private String number;

    public CardDto() {
    }

    @NotNull
    public String getNumber() {
        return number;
    }

    public CardDto setNumber(String number) {
        this.number = number;
        return this;
    }
}
