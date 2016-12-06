package es.juanlsanchez.bm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.juanlsanchez.bm.domain.Section;
import es.juanlsanchez.bm.domain.User;

public interface SectionRepository extends JpaRepository<Section, Long> {

  public Optional<Section> findOneByIdAndPrincipal(Long id, User principal);

  public List<Section> findAllByPrincipalOrderByOrderAsc(User principal);

}
