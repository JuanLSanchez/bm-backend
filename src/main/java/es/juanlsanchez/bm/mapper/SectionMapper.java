package es.juanlsanchez.bm.mapper;

import org.mapstruct.Mapper;

import es.juanlsanchez.bm.domain.Section;
import es.juanlsanchez.bm.web.dto.SectionDTO;

@Mapper(componentModel = "spring")
public interface SectionMapper {

  public SectionDTO sectionToSectionDTO(Section section);

}
