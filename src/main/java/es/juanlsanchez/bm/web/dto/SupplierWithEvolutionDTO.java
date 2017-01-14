package es.juanlsanchez.bm.web.dto;

import java.time.LocalDate;
import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierWithEvolutionDTO {

  @JsonProperty("id")
  private Long id;
  @JsonProperty("name")
  @NotBlank
  private String name;
  @JsonProperty("nif")
  private String nif;
  @JsonProperty("evolution")
  private Map<LocalDate, Double> evolution;

}
