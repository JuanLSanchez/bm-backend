package es.juanlsanchez.bm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import es.juanlsanchez.bm.domain.Income;
import es.juanlsanchez.bm.web.dto.IncomeDTO;

@Mapper(componentModel = "spring")
public interface IncomeMapper {

    @Mappings({ @Mapping(target = "incomeDate", source = "incomeDate"),
	    @Mapping(target = "name", source = "name"),
	    @Mapping(target = "nif", source = "nif"),
	    @Mapping(target = "base", source = "base"),
	    @Mapping(target = "iva", source = "iva") })
    public IncomeDTO incomeToIncomeDTO(Income income);

}
