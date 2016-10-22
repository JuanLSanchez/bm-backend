package es.juanlsanchez.bm.web.dto;

import java.time.Instant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDTO {

    @NotNull
    @Past
    @JsonProperty("income_date")
    private Instant incomeDate;
    @NotBlank
    @JsonProperty("name")
    private String name;
    @JsonProperty("nif")
    private String nif;
    @JsonProperty("base")
    private double base;
    @Range(min = 0, max = 100)
    @JsonProperty("iva")
    private int iva;

}