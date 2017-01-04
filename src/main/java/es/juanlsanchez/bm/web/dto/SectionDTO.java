package es.juanlsanchez.bm.web.dto;

import javax.validation.constraints.NotNull;

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
public class SectionDTO {

  @JsonProperty("id")
  private Long id;
  @NotBlank
  @JsonProperty("name")
  private String name;
  @JsonProperty("order")
  @NotNull
  private Integer order;

}
