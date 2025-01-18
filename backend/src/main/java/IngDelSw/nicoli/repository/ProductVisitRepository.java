
package IngDelSw.nicoli.repository;

import IngDelSw.nicoli.model.Product;
import IngDelSw.nicoli.model.ProductVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductVisitRepository extends JpaRepository<ProductVisit, Long> {
    Optional<ProductVisit> findById(Integer id);
    List<ProductVisit> findByVisitId(Integer visitId);
}
