package es.juanlsanchez.bm.web.dto;

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
public class SupplierDTO {

  @JsonProperty("id")
  private Long id;
  @JsonProperty("name")
  @NotBlank
  private String name;
  @JsonProperty("nif")
  private String nif;

}
