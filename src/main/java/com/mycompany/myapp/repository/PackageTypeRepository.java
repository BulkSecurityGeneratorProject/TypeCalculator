package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PackageType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PackageType entity.
 */
@SuppressWarnings("unused")
public interface PackageTypeRepository extends JpaRepository<PackageType,Long> {

}
