package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.BobaModel;
import apap.tugas.bobaxixixi.model.StoreBobaModel;

import java.util.List;

public interface BobaService {
    void addBoba(BobaModel boba);
    void updateBoba(BobaModel boba);
    void deleteBoba(BobaModel boba);
    List<BobaModel> getListBoba();
    BobaModel getBobaByIdBoba(Long idBoba);
    Boolean getStatusBoba(BobaModel boba);
    Boolean getStatusJualBoba(BobaModel boba);
}
