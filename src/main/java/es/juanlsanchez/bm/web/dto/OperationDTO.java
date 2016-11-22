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
public class OperationDTO {

  private Long id;
  @NotBlank
  @JsonProperty("name")
  private String name;
  @NotBlank
  @JsonProperty("section")
  private Long section;

}
