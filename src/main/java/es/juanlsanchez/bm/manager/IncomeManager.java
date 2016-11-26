package es.juanlsanchez.bm.manager;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.bm.web.dto.IncomeDTO;
import es.juanlsanchez.bm.web.dto.RangeDTO;
import javassist.NotFoundException;

public interface IncomeManager {

  public Page<IncomeDTO> findAllByPrincipal(Pageable pageable);

  public Optional<IncomeDTO> findOne(Long id);

  public IncomeDTO create(IncomeDTO income);

  public IncomeDTO update(IncomeDTO income, Long id) throws NotFoundException;

  public void delete(Long id) throws NotFoundException;

  public RangeDTO getRangeByPrincipal();

}
