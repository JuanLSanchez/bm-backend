package es.juanlsanchez.bm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import es.juanlsanchez.bm.domain.Operation;
import es.juanlsanchez.bm.web.dto.OperationDTO;

@Mapper(componentModel = "spring")
public interface OperationMapper {


  @Mappings({@Mapping(target = "sectionId", source = "operation.section.id")})
  public OperationDTO operationToOperationDTO(Operation operation);

  @Mappings({@Mapping(target = "id", ignore = true), @Mapping(target = "section", ignore = true),
      @Mapping(target = "invoices", ignore = true), @Mapping(target = "principal", ignore = true)})
  public Operation operationDTOToOperation(OperationDTO operation);

  @Mappings({@Mapping(target = "id", ignore = true), @Mapping(target = "section", ignore = true),
      @Mapping(target = "invoices", ignore = true), @Mapping(target = "principal", ignore = true)})
  public void updateOperation(Operation operation, @MappingTarget Operation operationTarget);

}
