package es.juanlsanchez.bm.service.impl;

import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.juanlsanchez.bm.domain.Income;
import es.juanlsanchez.bm.domain.User;
import es.juanlsanchez.bm.mapper.IncomeMapper;
import es.juanlsanchez.bm.repository.IncomeRepository;
import es.juanlsanchez.bm.service.IncomeService;
import es.juanlsanchez.bm.service.UserService;
import javassist.NotFoundException;

@Service
@Transactional
public class DefaultIncomeService implements IncomeService {

  private final IncomeRepository incomeRepository;
  private final UserService userService;
  private final IncomeMapper incomeMapper;

  @Inject
  public DefaultIncomeService(final IncomeRepository incomeRepository,
      final UserService userService, final IncomeMapper incomeMapper) {
    this.incomeRepository = incomeRepository;
    this.userService = userService;
    this.incomeMapper = incomeMapper;
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

  @Override
  public Income update(Income income, Long incomeId) throws NotFoundException {
    Income incomeTarget = this.findOne(incomeId)
        .orElseThrow(() -> new NotFoundException("Not found the income " + incomeId));

    incomeMapper.updateIncome(income, incomeTarget);

    Income result = incomeRepository.save(incomeTarget);
    return result;
  }

  @Override
  public void delete(Long id) throws NotFoundException {
    this.findOne(id).orElseThrow(() -> new NotFoundException("Not found the income " + id));
    incomeRepository.delete(id);
  }

}
