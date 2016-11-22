package es.juanlsanchez.bm.manager;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.bm.web.dto.OperationDTO;
import javassist.NotFoundException;

public interface OperationManager {

  public Page<OperationDTO> findAllByPrincipal(Pageable pageable);

  public OperationDTO create(OperationDTO operation) throws NotFoundException;

  public Optional<OperationDTO> findOne(Long id);

  public OperationDTO update(OperationDTO operation, Long id) throws NotFoundException;

  public void delete(Long id) throws NotFoundException;

  public OperationDTO getOne(Long id) throws NotFoundException;

}
