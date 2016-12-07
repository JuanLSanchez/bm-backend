package es.juanlsanchez.bm.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.juanlsanchez.bm.domain.Operation;
import es.juanlsanchez.bm.domain.User;
import es.juanlsanchez.bm.mapper.OperationMapper;
import es.juanlsanchez.bm.repository.OperationRepository;
import es.juanlsanchez.bm.service.OperationService;
import es.juanlsanchez.bm.service.SectionService;
import es.juanlsanchez.bm.service.UserService;
import javassist.NotFoundException;

@Service
@Transactional
public class DefaultOperationService implements OperationService {

  private final OperationRepository operationRepository;
  private final OperationMapper operationMapper;
  private final UserService userService;
  private final SectionService sectionService;

  @Inject
  public DefaultOperationService(final OperationRepository operationRepository,
      final UserService userService, final OperationMapper operationMapper,
      final SectionService sectionService) {
    this.operationRepository = operationRepository;
    this.userService = userService;
    this.sectionService = sectionService;
    this.operationMapper = operationMapper;
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

  @Override
  public Page<Operation> findAllByPrincipal(Pageable pageable) {
    return this.operationRepository.findAllByPrincipal(pageable);
  }

  @Override
  public Operation create(Operation operation, Long sectionId) throws NotFoundException {
    User principal = this.userService.getPrincipal();

    operation.setSection(this.sectionService.getOne(sectionId));
    operation.setPrincipal(principal);

    return this.operationRepository.save(operation);
  }

  @Override
  public Operation update(Operation operation, Long operationId, Long sectionId)
      throws NotFoundException {

    Operation operationTarget = this.getOne(operationId);

    if (!operationTarget.getSection().getId().equals(sectionId)) {
      operationTarget.setSection(this.sectionService.getOne(sectionId));
    }

    this.operationMapper.updateOperation(operation, operationTarget);

    Operation result = this.operationRepository.save(operationTarget);
    return result;
  }

  @Override
  public void delete(Long id) throws NotFoundException {
    Operation operation = this.getOne(id);
    assertThat("The operation cannot has invoices", operation.getInvoices().isEmpty(),
        is(equalTo(true)));
    this.operationRepository.delete(id);
  }

}
