package es.juanlsanchez.bm.mapper;

import java.time.LocalDate;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import es.juanlsanchez.bm.domain.Supplier;
import es.juanlsanchez.bm.web.dto.SupplierDTO;
import es.juanlsanchez.bm.web.dto.SupplierWithEvolutionDTO;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

  public SupplierDTO supplierToSupplierDTO(Supplier supplier);

  @Mappings({@Mapping(target = "id", ignore = true), @Mapping(target = "principal", ignore = true),
      @Mapping(target = "invoices", ignore = true)})
  public Supplier supplierDTOToSupplier(SupplierDTO supplier);

  @Mappings({@Mapping(target = "name", source = "supplier.name"),
      @Mapping(target = "id", source = "supplier.id"),
      @Mapping(target = "nif", source = "supplier.nif")})
  public SupplierWithEvolutionDTO supplierToSupplierWithEvolutionDTO(Supplier supplier,
      Map<LocalDate, Double> evolution);

  @Mappings({@Mapping(target = "id", ignore = true), @Mapping(target = "invoices", ignore = true),
      @Mapping(target = "principal", ignore = true)})
  public void update(Supplier supplier, @MappingTarget Supplier supplierTarget);

}
