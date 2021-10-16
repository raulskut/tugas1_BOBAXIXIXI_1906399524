package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.StoreModel;
import java.util.List;

public interface StoreService {
    void addStore(StoreModel store);
    void updateStore(StoreModel store);
    void deleteStore(StoreModel store);
    String generateStoreCode(StoreModel store);
    List<StoreModel> getListStore();
    StoreModel getStorebyIdStore(Long idStore);
    Boolean storeStatus(Long idStore);
    Boolean ableToDelete(StoreModel store);
}
