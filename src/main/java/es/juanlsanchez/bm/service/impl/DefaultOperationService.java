package es.juanlsanchez.bm.service.impl;

import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import es.juanlsanchez.bm.domain.Operation;
import es.juanlsanchez.bm.domain.User;
import es.juanlsanchez.bm.mapper.OperationMapper;
import es.juanlsanchez.bm.repository.OperationRepository;
import es.juanlsanchez.bm.service.OperationService;
import es.juanlsanchez.bm.service.UserService;
import javassist.NotFoundException;

@Service
@Transactional
public class DefaultOperationService implements OperationService {

  private final OperationRepository operationRepository;
  private final UserService userService;

  @Inject
  public DefaultOperationService(final OperationRepository operationRepository,
      final UserService userService, final OperationMapper operationMapper) {
    this.operationRepository = operationRepository;
    this.userService = userService;
  }

  @Override
  public Operation getOne(Long id) throws NotFoundException {
    return this.findOne(id)
        .orElseThrow(() -> new NotFoundException("Not found the operation " + id));
  }

  @Override
  public Optional<Operation> findOne(Long id) {
    User principal = userService.getPrincipal();
    return this.operationRepository.findOneByIdAndPrincipal(id, principal);
  }

}
