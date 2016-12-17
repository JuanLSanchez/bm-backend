package es.juanlsanchez.bm.manager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.bm.web.dto.SectionDTO;

public interface SectionManager {

  public Page<SectionDTO> findAllByPrincipal(Pageable pageable);

}
