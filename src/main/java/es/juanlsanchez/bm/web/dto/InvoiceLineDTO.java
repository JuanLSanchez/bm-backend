package es.juanlsanchez.bm.web.dto;

import javax.validation.constraints.NotNull;

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
public class InvoiceLineDTO {

  @JsonProperty("id")
  private Long id;
  @JsonProperty("iva")
  @Range(min = 0, max = 100)
  @NotNull
  private Integer iva;
  @JsonProperty("base")
  @NotNull
  private Double base;
  @JsonProperty("invoice_id")
  @NotNull
  private Long invoiceId;

}
