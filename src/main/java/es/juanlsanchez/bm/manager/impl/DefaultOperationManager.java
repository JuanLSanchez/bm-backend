package es.juanlsanchez.bm.manager.impl;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import es.juanlsanchez.bm.manager.OperationManager;
import es.juanlsanchez.bm.mapper.OperationMapper;
import es.juanlsanchez.bm.service.OperationService;
import es.juanlsanchez.bm.web.dto.OperationDTO;
import javassist.NotFoundException;

@Component
public class DefaultOperationManager implements OperationManager {

  private final OperationMapper operationMapper;
  private final OperationService operationService;

  @Inject
  public DefaultOperationManager(final OperationMapper operationMapper,
      final OperationService operationService) {
    this.operationMapper = operationMapper;
    this.operationService = operationService;
  }

  @Override
  public Page<OperationDTO> findAllByPrincipal(Pageable pageable) {
    return operationService.findAllByPrincipal(pageable)
        .map(operation -> operationMapper.operationToOperationDTO(operation));
  }

  @Override
  public Optional<OperationDTO> findOne(Long id) {
    return operationService.findOne(id)
        .map(operation -> operationMapper.operationToOperationDTO(operation));
  }

  @Override
  public OperationDTO create(OperationDTO operation) throws NotFoundException {
    return operationMapper.operationToOperationDTO(operationService
        .create(operationMapper.operationDTOToOperation(operation), operation.getSectionId()));
  }

  @Override
  public OperationDTO update(OperationDTO operation, Long operationId) throws NotFoundException {
    return operationMapper.operationToOperationDTO(operationService.update(
        operationMapper.operationDTOToOperation(operation), operationId, operation.getSectionId()));
  }

  @Override
  public void delete(Long id) throws NotFoundException {
    operationService.delete(id);
  }

  @Override
  public OperationDTO getOne(Long id) throws NotFoundException {
    return operationMapper.operationToOperationDTO(operationService.getOne(id));
  }

}
