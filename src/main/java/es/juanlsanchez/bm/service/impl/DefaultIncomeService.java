package es.juanlsanchez.bm.service.impl;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.juanlsanchez.bm.domain.Income;
import es.juanlsanchez.bm.repository.IncomeRepository;
import es.juanlsanchez.bm.service.IncomeService;

@Service
public class DefaultIncomeService implements IncomeService {

    private final IncomeRepository incomeRepository;

    @Inject
    public DefaultIncomeService(final IncomeRepository incomeRepository) {
	this.incomeRepository = incomeRepository;
    }

    @Override
    public Page<Income> findAllByPrincipal(Pageable pageable) {
	return incomeRepository.findAllByPrincipal(pageable);
    }

}
