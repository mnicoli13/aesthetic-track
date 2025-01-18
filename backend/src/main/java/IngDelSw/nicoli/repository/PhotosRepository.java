
package IngDelSw.nicoli.repository;

import IngDelSw.nicoli.model.Photo;
import IngDelSw.nicoli.model.ProductVisit;
import IngDelSw.nicoli.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotosRepository extends JpaRepository<Photo, Long> {
    Optional<Photo> findById(Integer id);
    List<Photo> findByVisit(Visit visit);
}
