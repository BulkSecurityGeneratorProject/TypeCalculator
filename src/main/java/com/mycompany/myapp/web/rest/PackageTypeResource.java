package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Box;
import com.mycompany.myapp.domain.PackageType;
import com.mycompany.myapp.service.PackageTypeService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PackageType.
 */
@RestController
@RequestMapping("/api")
public class PackageTypeResource {

    private final Logger log = LoggerFactory.getLogger(PackageTypeResource.class);

    @Inject
    private PackageTypeService packageTypeService;

    /**
     * POST  /package-types : Create a new packageType.
     *
     * @param packageType the packageType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new packageType, or with status 400 (Bad Request) if the packageType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/package-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PackageType> createPackageType(@Valid @RequestBody PackageType packageType) throws URISyntaxException {
        log.debug("REST request to save PackageType : {}", packageType);
        if (packageType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("packageType", "idexists", "A new packageType cannot already have an ID")).body(null);
        }
        PackageType result = packageTypeService.save(packageType);
        return ResponseEntity.created(new URI("/api/package-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("packageType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /package-types : Updates an existing packageType.
     *
     * @param packageType the packageType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated packageType,
     * or with status 400 (Bad Request) if the packageType is not valid,
     * or with status 500 (Internal Server Error) if the packageType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/package-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PackageType> updatePackageType(@Valid @RequestBody PackageType packageType) throws URISyntaxException {
        log.debug("REST request to update PackageType : {}", packageType);
        if (packageType.getId() == null) {
            return createPackageType(packageType);
        }
        PackageType result = packageTypeService.save(packageType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("packageType", packageType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /package-types : get all the packageTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of packageTypes in body
     */
    @RequestMapping(value = "/package-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PackageType> getAllPackageTypes() {
        log.debug("REST request to get all PackageTypes");
        return packageTypeService.findAll();
    }

    /**
     * GET  /package-types/:id : get the "id" packageType.
     *
     * @param id the id of the packageType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the packageType, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/package-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PackageType> getPackageType(@PathVariable Long id) {
        log.debug("REST request to get PackageType : {}", id);
        PackageType packageType = packageTypeService.findOne(id);
        return Optional.ofNullable(packageType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /package-types/:id : delete the "id" packageType.
     *
     * @param id the id of the packageType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/package-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePackageType(@PathVariable Long id) {
        log.debug("REST request to delete PackageType : {}", id);
        packageTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("packageType", id.toString())).build();
    }

    @RequestMapping(value = "/calculation", method = RequestMethod.POST)
    public PackageType calculate(@RequestBody Box box) {
        return packageTypeService.findMatchPacket(box);
    }
}
