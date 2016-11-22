package es.juanlsanchez.bm.service.impl;

import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import es.juanlsanchez.bm.domain.Section;
import es.juanlsanchez.bm.domain.User;
import es.juanlsanchez.bm.repository.SectionRepository;
import es.juanlsanchez.bm.service.SectionService;
import es.juanlsanchez.bm.service.UserService;
import javassist.NotFoundException;

@Service
@Transactional
public class DefaultSectionService implements SectionService {


  private final SectionRepository sectionRepository;
  private final UserService userService;

  @Inject
  public DefaultSectionService(final SectionRepository sectionRepository,
      final UserService userService) {
    this.sectionRepository = sectionRepository;
    this.userService = userService;
  }

  @Override
  public Section getOne(Long id) throws NotFoundException {
    return this.findOne(id)
        .orElseThrow(() -> new NotFoundException("Not found the operation " + id));
  }

  @Override
  public Optional<Section> findOne(Long id) {
    User principal = userService.getPrincipal();
    return this.sectionRepository.findOneByIdAndPrincipal(id, principal);
  }


}
