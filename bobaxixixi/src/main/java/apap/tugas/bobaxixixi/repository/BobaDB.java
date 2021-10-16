package apap.tugas.bobaxixixi.repository;

import apap.tugas.bobaxixixi.model.BobaModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BobaDB extends JpaRepository<BobaModel, Long> {
    Optional<BobaModel> findByIdBoba(Long idBoba);
}
