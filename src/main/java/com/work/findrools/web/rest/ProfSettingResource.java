package com.work.findrools.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.work.findrools.service.ProfSettingService;
import com.work.findrools.web.rest.util.HeaderUtil;
import com.work.findrools.web.rest.util.PaginationUtil;
import com.work.findrools.service.dto.ProfSettingDTO;
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
 * REST controller for managing ProfSetting.
 */
@RestController
@RequestMapping("/api")
public class ProfSettingResource {

    private final Logger log = LoggerFactory.getLogger(ProfSettingResource.class);

    private static final String ENTITY_NAME = "profSetting";

    private final ProfSettingService profSettingService;

    public ProfSettingResource(ProfSettingService profSettingService) {
        this.profSettingService = profSettingService;
    }

    /**
     * POST  /prof-settings : Create a new profSetting.
     *
     * @param profSettingDTO the profSettingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profSettingDTO, or with status 400 (Bad Request) if the profSetting has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prof-settings")
    @Timed
    public ResponseEntity<ProfSettingDTO> createProfSetting(@RequestBody ProfSettingDTO profSettingDTO) throws URISyntaxException {
        log.debug("REST request to save ProfSetting : {}", profSettingDTO);
        if (profSettingDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new profSetting cannot already have an ID")).body(null);
        }
        ProfSettingDTO result = profSettingService.save(profSettingDTO);
        return ResponseEntity.created(new URI("/api/prof-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prof-settings : Updates an existing profSetting.
     *
     * @param profSettingDTO the profSettingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profSettingDTO,
     * or with status 400 (Bad Request) if the profSettingDTO is not valid,
     * or with status 500 (Internal Server Error) if the profSettingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prof-settings")
    @Timed
    public ResponseEntity<ProfSettingDTO> updateProfSetting(@RequestBody ProfSettingDTO profSettingDTO) throws URISyntaxException {
        log.debug("REST request to update ProfSetting : {}", profSettingDTO);
        if (profSettingDTO.getId() == null) {
            return createProfSetting(profSettingDTO);
        }
        ProfSettingDTO result = profSettingService.save(profSettingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profSettingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prof-settings : get all the profSettings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of profSettings in body
     */
    @GetMapping("/prof-settings")
    @Timed
    public ResponseEntity<List<ProfSettingDTO>> getAllProfSettings(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ProfSettings");
        Page<ProfSettingDTO> page = profSettingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prof-settings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prof-settings/:id : get the "id" profSetting.
     *
     * @param id the id of the profSettingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profSettingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/prof-settings/{id}")
    @Timed
    public ResponseEntity<ProfSettingDTO> getProfSetting(@PathVariable Long id) {
        log.debug("REST request to get ProfSetting : {}", id);
        ProfSettingDTO profSettingDTO = profSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profSettingDTO));
    }

    /**
     * DELETE  /prof-settings/:id : delete the "id" profSetting.
     *
     * @param id the id of the profSettingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prof-settings/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfSetting(@PathVariable Long id) {
        log.debug("REST request to delete ProfSetting : {}", id);
        profSettingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
