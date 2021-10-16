package apap.tugas.bobaxixixi.repository;

import apap.tugas.bobaxixixi.model.StoreBobaModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreBobaDB extends JpaRepository<StoreBobaModel, Long>{
    Optional<StoreBobaModel> findByProductionCode(String code);
}
