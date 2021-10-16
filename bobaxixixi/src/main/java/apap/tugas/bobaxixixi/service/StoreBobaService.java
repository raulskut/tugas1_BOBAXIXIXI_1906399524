package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.StoreBobaModel;

import java.util.List;

public interface StoreBobaService {
    void addStoreBoba(StoreBobaModel storeBoba);
    void deleteStoreBoba(StoreBobaModel storeBoba);
    List<StoreBobaModel> getListStoreBoba();
    String generateProductionCode(StoreBobaModel storeboba);
}
