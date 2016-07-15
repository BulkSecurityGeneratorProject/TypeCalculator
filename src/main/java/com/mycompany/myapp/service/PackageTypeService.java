package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Box;
import com.mycompany.myapp.domain.PackageType;
import com.mycompany.myapp.repository.PackageTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PackageType.
 */
@Service
@Transactional
public class PackageTypeService {

    private final Logger log = LoggerFactory.getLogger(PackageTypeService.class);

    @Inject
    private PackageTypeRepository packageTypeRepository;

    /**
     * Save a packageType.
     *
     * @param packageType the entity to save
     * @return the persisted entity
     */
    public PackageType save(PackageType packageType) {
        log.debug("Request to save PackageType : {}", packageType);
        PackageType result = packageTypeRepository.save(packageType);
        return result;
    }

    /**
     *  Get all the packageTypes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PackageType> findAll() {
        log.debug("Request to get all PackageTypes");
        List<PackageType> result = packageTypeRepository.findAll();
        return result;
    }

    /**
     *  Get one packageType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PackageType findOne(Long id) {
        log.debug("Request to get PackageType : {}", id);
        PackageType packageType = packageTypeRepository.findOne(id);
        return packageType;
    }

    /**
     *  Delete the  packageType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PackageType : {}", id);
        packageTypeRepository.delete(id);
    }

    public PackageType findMatchPacket(Box box) {
        return packageTypeRepository.findAll().stream().filter(p -> p.matches(box)).findFirst().orElseThrow(() -> new PackageNotFoundException());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class PackageNotFoundException extends RuntimeException {

    }
}
