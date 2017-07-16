package com.work.findrools.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.work.findrools.service.RiskProfileService;
import com.work.findrools.web.rest.util.HeaderUtil;
import com.work.findrools.web.rest.util.PaginationUtil;
import com.work.findrools.service.dto.RiskProfileDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RiskProfile.
 */
@RestController
@RequestMapping("/api")
public class RiskProfileResource {

    private final Logger log = LoggerFactory.getLogger(RiskProfileResource.class);

    private static final String ENTITY_NAME = "riskProfile";

    private final RiskProfileService riskProfileService;

    public RiskProfileResource(RiskProfileService riskProfileService) {
        this.riskProfileService = riskProfileService;
    }

    /**
     * POST  /risk-profiles : Create a new riskProfile.
     *
     * @param riskProfileDTO the riskProfileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new riskProfileDTO, or with status 400 (Bad Request) if the riskProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/risk-profiles")
    @Timed
    public ResponseEntity<RiskProfileDTO> createRiskProfile(@RequestBody RiskProfileDTO riskProfileDTO) throws URISyntaxException {
        log.debug("REST request to save RiskProfile : {}", riskProfileDTO);
        if (riskProfileDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new riskProfile cannot already have an ID")).body(null);
        }
        RiskProfileDTO result = riskProfileService.save(riskProfileDTO);
        return ResponseEntity.created(new URI("/api/risk-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /risk-profiles : Updates an existing riskProfile.
     *
     * @param riskProfileDTO the riskProfileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated riskProfileDTO,
     * or with status 400 (Bad Request) if the riskProfileDTO is not valid,
     * or with status 500 (Internal Server Error) if the riskProfileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/risk-profiles")
    @Timed
    public ResponseEntity<RiskProfileDTO> updateRiskProfile(@RequestBody RiskProfileDTO riskProfileDTO) throws URISyntaxException {
        log.debug("REST request to update RiskProfile : {}", riskProfileDTO);
        if (riskProfileDTO.getId() == null) {
            return createRiskProfile(riskProfileDTO);
        }
        RiskProfileDTO result = riskProfileService.save(riskProfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, riskProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /risk-profiles : get all the riskProfiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of riskProfiles in body
     */
    @GetMapping("/risk-profiles")
    @Timed
    public ResponseEntity<List<RiskProfileDTO>> getAllRiskProfiles(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RiskProfiles");
        Page<RiskProfileDTO> page = riskProfileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/risk-profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /risk-profiles/:id : get the "id" riskProfile.
     *
     * @param id the id of the riskProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the riskProfileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/risk-profiles/{id}")
    @Timed
    public ResponseEntity<RiskProfileDTO> getRiskProfile(@PathVariable Long id) {
        log.debug("REST request to get RiskProfile : {}", id);
        RiskProfileDTO riskProfileDTO = riskProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(riskProfileDTO));
    }

    /**
     * DELETE  /risk-profiles/:id : delete the "id" riskProfile.
     *
     * @param id the id of the riskProfileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/risk-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteRiskProfile(@PathVariable Long id) {
        log.debug("REST request to delete RiskProfile : {}", id);
        riskProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
