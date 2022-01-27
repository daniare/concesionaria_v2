package com.mycompany.myapp.repository;

import java.util.List;

import com.mycompany.myapp.domain.Coche;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Coche entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CocheRepository extends JpaRepository<Coche, Long>, JpaSpecificationExecutor<Coche>{

    List <Coche> findAllByExposicionTrue();
}
