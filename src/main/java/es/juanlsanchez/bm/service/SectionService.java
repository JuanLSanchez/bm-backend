package es.juanlsanchez.bm.service;

import java.util.List;
import java.util.Optional;

import es.juanlsanchez.bm.domain.Section;
import javassist.NotFoundException;

public interface SectionService {

  public Section getOne(Long id) throws NotFoundException;

  public Optional<Section> findOne(Long id);

  public List<Section> findAllByPrincipalOrderByOrderAsc();

}
