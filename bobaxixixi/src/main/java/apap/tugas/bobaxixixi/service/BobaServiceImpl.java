package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.BobaModel;
import apap.tugas.bobaxixixi.model.StoreBobaModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import apap.tugas.bobaxixixi.repository.BobaDB;
import apap.tugas.bobaxixixi.repository.StoreBobaDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BobaServiceImpl implements BobaService{
    @Autowired
    BobaDB bobaDB;

    @Autowired
    StoreBobaDB storeBobaDB;

    @Override
    public void addBoba(BobaModel boba){
        boba.setPrice(boba.getPrice()+boba.getTopping().getPrice());
        bobaDB.save(boba);
    }

    @Override
    public void updateBoba(BobaModel boba){
        boba.setPrice(boba.getPrice()+boba.getTopping().getPrice());
        bobaDB.save(boba);
    }

    @Override
    public void deleteBoba(BobaModel boba){
        bobaDB.delete(boba);
    }

    @Override
    public List<BobaModel> getListBoba(){
        return bobaDB.findAll();
    }

    @Override
    public BobaModel getBobaByIdBoba(Long idBoba){
        Optional<BobaModel> boba = bobaDB.findByIdBoba(idBoba);
        if (boba.isPresent()){
            return boba.get();
        }
        return null;
    }

    @Override
    public Boolean getStatusBoba(BobaModel boba){
        Optional<BobaModel> bobaModel = bobaDB.findByIdBoba(boba.getIdBoba());
        LocalTime now = LocalTime.now();
        ArrayList<Integer> statusList = new ArrayList<>();
        if (bobaModel.isPresent()){
            for (StoreBobaModel sb:storeBobaDB.findAll()){
                if (bobaModel.get().getIdBoba().equals(sb.getBoba().getIdBoba())){
                    if (now.isBefore(sb.getStore().getOpenHour()) || now.isAfter(sb.getStore().getCloseHour())){
                        statusList.add(1);
                    } else {
                        statusList.add(0);
                    }
                }
            }
        }

        if (statusList.contains(0)){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean getStatusJualBoba(BobaModel boba){
        Optional<BobaModel> bobaModel = bobaDB.findByIdBoba(boba.getIdBoba());
        if (bobaModel.isPresent()){
            if (boba.getStoreBoba().isEmpty()){
                return true;
            }
        }
        return false;
    }
}
