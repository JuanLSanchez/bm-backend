package es.juanlsanchez.bm.service.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  @Override
  public List<Section> findAllByPrincipalOrderByOrderAsc() {
    User principal = this.userService.getPrincipal();
    return this.sectionRepository.findAllByPrincipalOrderByOrderAsc(principal);
  }

  @Override
  public Page<Section> findAllByPrincipal(Pageable pageable) {
    return this.sectionRepository.findAllByPrincipal(pageable);
  }


}
