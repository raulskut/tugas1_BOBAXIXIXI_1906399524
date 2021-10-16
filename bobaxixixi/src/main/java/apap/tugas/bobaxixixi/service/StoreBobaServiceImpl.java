package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.StoreBobaModel;
import apap.tugas.bobaxixixi.repository.StoreBobaDB;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StoreBobaServiceImpl implements StoreBobaService{

    @Autowired
    StoreBobaDB storeBobaDB;

    @Override
    public void addStoreBoba(StoreBobaModel storeBoba){
        StoreBobaModel newStore = storeBoba;
        newStore.setProductionCode(generateProductionCode(newStore));
        storeBobaDB.save(storeBoba);
    }

    @Override
    public void deleteStoreBoba(StoreBobaModel storeBoba){
        storeBobaDB.delete(storeBoba);
    }

    @Override
    public List<StoreBobaModel> getListStoreBoba(){
        return storeBobaDB.findAll();
    }

    @Override
    public String generateProductionCode(StoreBobaModel storeboba){
        String productionCode = "PC";
        String idStore = (storeboba.getStore().getIdStore()).toString();
        String idBoba = (storeboba.getBoba().getIdBoba()).toString();
        String topping = (storeboba.getBoba().getTopping().getName());

        if (idStore.length() == 1){
            String temp = "00" + idStore;
            productionCode += temp;
        } else {
            String temp = "0" + idStore;
            productionCode += temp;
        }

        if (topping.equals("")){
            productionCode += "0";
        } else {
            productionCode += "1";
        }

        if (idBoba.length() == 1){
            String temp = "00" + idBoba;
            productionCode += temp;
        } else {
            String temp = "0" + idBoba;
            productionCode += temp;
        }

        return productionCode;
    }
}
