package es.juanlsanchez.bm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.bm.domain.Income;

public interface IncomeService {

    public Page<Income> findAllByPrincipal(Pageable pageable);

}
