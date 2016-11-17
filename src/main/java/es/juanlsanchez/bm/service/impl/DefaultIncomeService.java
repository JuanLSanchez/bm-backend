package es.juanlsanchez.bm.service.impl;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.juanlsanchez.bm.domain.Income;
import es.juanlsanchez.bm.domain.User;
import es.juanlsanchez.bm.repository.IncomeRepository;
import es.juanlsanchez.bm.service.IncomeService;
import es.juanlsanchez.bm.service.UserService;

@Service
public class DefaultIncomeService implements IncomeService {

  private final IncomeRepository incomeRepository;
  private final UserService userService;

  @Inject
  public DefaultIncomeService(final IncomeRepository incomeRepository,
      final UserService userService) {
    this.incomeRepository = incomeRepository;
    this.userService = userService;
  }

  @Override
  public Page<Income> findAllByPrincipal(Pageable pageable) {
    return incomeRepository.findAllByPrincipal(pageable);
  }

  @Override
  public Optional<Income> findOne(Long id) {
    User principal = userService.getPrincipal();
    return incomeRepository.findOneByIdAndPrincipal(id, principal);
  }

  @Override
  public Income create(Income income) {
    User principal = userService.getPrincipal();
    income.setPrincipal(principal);

    Income result = incomeRepository.save(income);

    return result;
  }

}
