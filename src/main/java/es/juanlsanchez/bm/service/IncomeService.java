package es.juanlsanchez.bm.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.bm.domain.Income;

public interface IncomeService {

  public Page<Income> findAllByPrincipal(Pageable pageable);

  public Optional<Income> findOne(Long id);

  public Income create(Income income);

}
