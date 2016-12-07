package es.juanlsanchez.bm.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.bm.domain.Operation;
import javassist.NotFoundException;

public interface OperationService {

  public Operation getOne(Long id) throws NotFoundException;

  public Optional<Operation> findOne(Long id);

  public Page<Operation> findAllByPrincipal(Pageable pageable);

  public Operation create(Operation operation, Long sectionId) throws NotFoundException;

  public Operation update(Operation operation, Long operationId, Long sectionId)
      throws NotFoundException;

  public void delete(Long id) throws NotFoundException;

}
