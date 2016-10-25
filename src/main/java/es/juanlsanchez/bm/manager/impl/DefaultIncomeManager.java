package es.juanlsanchez.bm.manager.impl;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import es.juanlsanchez.bm.manager.IncomeManager;
import es.juanlsanchez.bm.mapper.IncomeMapper;
import es.juanlsanchez.bm.service.IncomeService;
import es.juanlsanchez.bm.web.dto.IncomeDTO;

@Component
public class DefaultIncomeManager implements IncomeManager {

    private final IncomeMapper incomeMapper;
    private final IncomeService incomeService;

    @Inject
    public DefaultIncomeManager(final IncomeMapper incomeMapper, final IncomeService incomeService) {
	this.incomeMapper = incomeMapper;
	this.incomeService = incomeService;
    }

    @Override
    public Page<IncomeDTO> findAllByPrincipal(Pageable pageable) {
	return incomeService.findAllByPrincipal(pageable)
		.map(income -> incomeMapper.incomeToIncomeDTO(income));
    }

    @Override
    public Optional<IncomeDTO> findOne(Long id) {
	return incomeService.findOne(id)
		.map(income -> incomeMapper.incomeToIncomeDTO(income));
    }

}
