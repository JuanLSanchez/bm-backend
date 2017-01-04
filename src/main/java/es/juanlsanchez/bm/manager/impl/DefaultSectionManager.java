package es.juanlsanchez.bm.manager.impl;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import es.juanlsanchez.bm.manager.SectionManager;
import es.juanlsanchez.bm.mapper.SectionMapper;
import es.juanlsanchez.bm.service.SectionService;
import es.juanlsanchez.bm.web.dto.SectionDTO;

@Component
public class DefaultSectionManager implements SectionManager {

  private final SectionMapper sectionMapper;
  private final SectionService sectionService;

  @Inject
  public DefaultSectionManager(final SectionMapper sectionMapper,
      final SectionService sectionService) {
    this.sectionMapper = sectionMapper;
    this.sectionService = sectionService;
  }

  @Override
  public Page<SectionDTO> findAllByPrincipal(Pageable pageable) {
    return sectionService.findAllByPrincipal(pageable)
        .map(section -> sectionMapper.sectionToSectionDTO(section));
  }

}
