package es.juanlsanchez.bm.service;

import java.util.Optional;

import es.juanlsanchez.bm.domain.Operation;
import javassist.NotFoundException;

public interface OperationService {

  public Operation getOne(Long id) throws NotFoundException;

  public Optional<Operation> findOne(Long id);

}
