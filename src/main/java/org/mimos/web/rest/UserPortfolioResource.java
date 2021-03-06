package org.mimos.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mimos.domain.UserPortfolio;

import org.mimos.repository.UserPortfolioRepository;
import org.mimos.repository.search.UserPortfolioSearchRepository;
import org.mimos.web.rest.errors.BadRequestAlertException;
import org.mimos.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing UserPortfolio.
 */
@RestController
@RequestMapping("/api")
public class UserPortfolioResource {

    private final Logger log = LoggerFactory.getLogger(UserPortfolioResource.class);

    private static final String ENTITY_NAME = "userPortfolio";

    private final UserPortfolioRepository userPortfolioRepository;

    private final UserPortfolioSearchRepository userPortfolioSearchRepository;

    public UserPortfolioResource(UserPortfolioRepository userPortfolioRepository, UserPortfolioSearchRepository userPortfolioSearchRepository) {
        this.userPortfolioRepository = userPortfolioRepository;
        this.userPortfolioSearchRepository = userPortfolioSearchRepository;
    }

    /**
     * POST  /user-portfolios : Create a new userPortfolio.
     *
     * @param userPortfolio the userPortfolio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userPortfolio, or with status 400 (Bad Request) if the userPortfolio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-portfolios")
    @Timed
    public ResponseEntity<UserPortfolio> createUserPortfolio(@RequestBody UserPortfolio userPortfolio) throws URISyntaxException {
        log.debug("REST request to save UserPortfolio : {}", userPortfolio);
        if (userPortfolio.getId() != null) {
            throw new BadRequestAlertException("A new userPortfolio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPortfolio result = userPortfolioRepository.save(userPortfolio);
        userPortfolioSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/user-portfolios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-portfolios : Updates an existing userPortfolio.
     *
     * @param userPortfolio the userPortfolio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userPortfolio,
     * or with status 400 (Bad Request) if the userPortfolio is not valid,
     * or with status 500 (Internal Server Error) if the userPortfolio couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-portfolios")
    @Timed
    public ResponseEntity<UserPortfolio> updateUserPortfolio(@RequestBody UserPortfolio userPortfolio) throws URISyntaxException {
        log.debug("REST request to update UserPortfolio : {}", userPortfolio);
        if (userPortfolio.getId() == null) {
            return createUserPortfolio(userPortfolio);
        }
        UserPortfolio result = userPortfolioRepository.save(userPortfolio);
        userPortfolioSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userPortfolio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-portfolios : get all the userPortfolios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userPortfolios in body
     */
    @GetMapping("/user-portfolios")
    @Timed
    public List<UserPortfolio> getAllUserPortfolios() {
        log.debug("REST request to get all UserPortfolios");
        return userPortfolioRepository.findAll();
        }

    /**
     * GET  /user-portfolios/:id : get the "id" userPortfolio.
     *
     * @param id the id of the userPortfolio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userPortfolio, or with status 404 (Not Found)
     */
    @GetMapping("/user-portfolios/{id}")
    @Timed
    public ResponseEntity<UserPortfolio> getUserPortfolio(@PathVariable Long id) {
        log.debug("REST request to get UserPortfolio : {}", id);
        UserPortfolio userPortfolio = userPortfolioRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userPortfolio));
    }

    /**
     * DELETE  /user-portfolios/:id : delete the "id" userPortfolio.
     *
     * @param id the id of the userPortfolio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-portfolios/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserPortfolio(@PathVariable Long id) {
        log.debug("REST request to delete UserPortfolio : {}", id);
        userPortfolioRepository.delete(id);
        userPortfolioSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-portfolios?query=:query : search for the userPortfolio corresponding
     * to the query.
     *
     * @param query the query of the userPortfolio search
     * @return the result of the search
     */
    @GetMapping("/_search/user-portfolios")
    @Timed
    public List<UserPortfolio> searchUserPortfolios(@RequestParam String query) {
        log.debug("REST request to search UserPortfolios for query {}", query);
        return StreamSupport
            .stream(userPortfolioSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
