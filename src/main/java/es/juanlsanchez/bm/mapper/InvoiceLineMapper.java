package es.juanlsanchez.bm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import es.juanlsanchez.bm.domain.InvoiceLine;
import es.juanlsanchez.bm.web.dto.InvoiceLineDTO;

@Mapper(componentModel = "spring")
public interface InvoiceLineMapper {

  @Mappings({@Mapping(target = "invoiceId", source = "invoice.id")})
  public InvoiceLineDTO invoiceLineToInvoiceLineDTO(InvoiceLine invoiceLine);

  @Mappings({@Mapping(target = "id", ignore = true), @Mapping(target = "invoice", ignore = true)})
  public InvoiceLine invoiceLineDTOToInvoiceLine(InvoiceLineDTO invoiceLine);

  @Mappings({@Mapping(target = "id", ignore = true), @Mapping(target = "invoice", ignore = true)})
  public void updateInvoiceLine(InvoiceLine invoice, @MappingTarget InvoiceLine invoiceTarget);

}
