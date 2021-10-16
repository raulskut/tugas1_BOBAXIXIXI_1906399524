package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.StoreBobaModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import apap.tugas.bobaxixixi.repository.StoreDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StoreServiceImpl implements StoreService{

    @Autowired
    StoreDB storeDB;

    @Override
    public void addStore(StoreModel store){
        StoreModel newStore = store;
        newStore.setStoreCode(generateStoreCode(store));
        storeDB.save(store);
    }

    @Override
    public void updateStore(StoreModel store){
        StoreModel newStore = store;
        newStore.setStoreCode(generateStoreCode(store));
        storeDB.save(store);
    }

    @Override
    public void deleteStore(StoreModel store){
        storeDB.delete(store);
    }

    @Override
    public String generateStoreCode(StoreModel store){
        String storeCode = "";
        String twoFirst = "SC";
        storeCode+=twoFirst;
        String subNameRev = new StringBuilder(store.getName().toUpperCase().substring(0, 3)).reverse().toString();
        storeCode+=subNameRev;
        String openHourOnly = store.getOpenHour().toString().substring(0,2);
        storeCode+=openHourOnly;
        String modCloseHour = String.valueOf(Math.floorDiv(Integer.parseInt(store.getCloseHour().toString().substring(0,2)),10));
        storeCode+=modCloseHour;
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder random = new StringBuilder(2);
        for (int i = 0; i < 2; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            random.append(AlphaNumericString.charAt(index));
        }
        storeCode+=random.toString();
        return storeCode;
    }

    @Override
    public List<StoreModel> getListStore(){
        return storeDB.findAll();
    }

    @Override
    public StoreModel getStorebyIdStore(Long idStore){
        Optional<StoreModel> store = storeDB.findByIdStore(idStore);
        if (store.isPresent()){
            return store.get();
        }
        return null;
    }

    @Override
    public Boolean storeStatus(Long idStore){
        Optional<StoreModel> store = storeDB.findByIdStore(idStore);
        LocalTime open = store.get().getOpenHour();
        LocalTime close = store.get().getCloseHour();
        LocalTime now = LocalTime.now();

        if (store.isPresent()){
            if (now.isBefore(open) || now.isAfter(close)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean ableToDelete(StoreModel store){
        List<StoreBobaModel> boba = store.getStoreBoba();
        if (storeStatus(store.getIdStore())){
            if (boba.size() == 0){
                return true;
            }
        }
        return false;
    }
}
