package es.juanlsanchez.bm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import es.juanlsanchez.bm.domain.Invoice;
import es.juanlsanchez.bm.web.dto.InvoiceDTO;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

  @Mappings({@Mapping(target = "operationId", source = "operation.id"),
      @Mapping(target = "supplierId", source = "supplier.id")})
  public InvoiceDTO invoiceToInvoiceDTO(Invoice invoice);

  @Mappings({@Mapping(target = "principal", ignore = true),
      @Mapping(target = "invoiceLines", ignore = true),
      @Mapping(target = "operation", ignore = true), @Mapping(target = "supplier", ignore = true)})
  public Invoice invoiceDTOToInvoice(InvoiceDTO invoice);

  @Mappings({@Mapping(target = "id", ignore = true), @Mapping(target = "principal", ignore = true),
      @Mapping(target = "supplier", ignore = true), @Mapping(target = "operation", ignore = true),
      @Mapping(target = "invoiceLines", ignore = true)})
  public void updateInvoice(Invoice invoice, @MappingTarget Invoice invoiceTarget);

}
