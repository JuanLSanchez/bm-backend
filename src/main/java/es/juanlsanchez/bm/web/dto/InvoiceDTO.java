package es.juanlsanchez.bm.web.dto;

import java.time.Instant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {

  @JsonProperty("id")
  private Long id;
  @JsonProperty("number")
  private String number;
  @JsonProperty("date_buy")
  @NotNull
  @Past
  private Instant dateBuy;
  @JsonProperty("supplier_id")
  @NotNull
  private Long supplierId;
  @JsonProperty("operation_id")
  @NotNull
  private Long operationId;

}
