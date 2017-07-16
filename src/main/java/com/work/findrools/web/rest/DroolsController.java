package com.work.findrools.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Person.
 */
@RestController
@RequestMapping("/api")
public class DroolsController {
//
//    private final Logger log = LoggerFactory.getLogger(DroolsController.class);
//
//    private final DroolsService droolsService;
//
//    public DroolsController(DroolsService droolsService) {
//        this.droolsService = droolsService;
//    }
//
//    @PostMapping("/getRisk")
//    @Timed
//    public ResponseEntity<String> createPerson(@RequestBody String String) throws URISyntaxException {
//        log.debug("REST request to save Person : {}", String);
//        if (String.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new person cannot already have an ID")).body(null);
//        }
//        String result = personService.save(String);
//        return ResponseEntity.created(new URI("/api/people/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }
//
//    @PostMapping("/getRiskTemplate")
//    @Timed
//    public ResponseEntity<String> getRiskTemplate(@RequestBody String String) throws URISyntaxException {
//        log.debug("REST request to save Person : {}", String);
//        if (String.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new person cannot already have an ID")).body(null);
//        }
//        String result = personService.save(String);
//        return ResponseEntity.created(new URI("/api/people/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }
//
//    @PostMapping("/getRiskDecision")
//    @Timed
//    public ResponseEntity<String> getRiskDecision(@RequestBody String String) throws URISyntaxException {
//        log.debug("REST request to save Person : {}", String);
//        if (String.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new person cannot already have an ID")).body(null);
//        }
//        String result = personService.save(String);
//        return ResponseEntity.created(new URI("/api/people/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }
}
