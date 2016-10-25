package es.juanlsanchez.bm.web.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.juanlsanchez.bm.config.Constants;
import es.juanlsanchez.bm.manager.IncomeManager;
import es.juanlsanchez.bm.web.dto.IncomeDTO;
import es.juanlsanchez.bm.web.util.pagination.PaginationUtil;

@RestController
@RequestMapping(Constants.START_URL_API
	+ "/income")
public class IncomeResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final IncomeManager incomeManager;

    @Inject
    public IncomeResource(final IncomeManager incomeManager) {
	this.incomeManager = incomeManager;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IncomeDTO>> findAllByPrincipal(Pageable pageable) throws URISyntaxException {
	log.debug("REST request to list income");

	Page<IncomeDTO> page = incomeManager.findAllByPrincipal(pageable);
	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pruebas");

	return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IncomeDTO> findOne(@PathVariable Long id) throws URISyntaxException {
	log.debug("REST request to find income: {}", id);
	return incomeManager.findOne(id)
		.map(incomeDto -> new ResponseEntity<>(incomeDto, HttpStatus.OK))
		.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
