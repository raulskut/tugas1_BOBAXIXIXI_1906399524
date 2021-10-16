package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.ManagerModel;
import apap.tugas.bobaxixixi.model.StoreBobaModel;
import apap.tugas.bobaxixixi.repository.BobaDB;
import apap.tugas.bobaxixixi.repository.ManagerDB;
import apap.tugas.bobaxixixi.repository.StoreBobaDB;
import apap.tugas.bobaxixixi.repository.StoreDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService{
    @Autowired
    ManagerDB managerDB;

    @Autowired
    StoreDB storeDB;

    @Autowired
    BobaDB bobaDB;

    @Autowired
    StoreBobaDB storeBobaDB;

    @Override
    public void addManager(ManagerModel manager){
        managerDB.save(manager);
    }

    @Override
    public List<ManagerModel> getListManager(){
        return managerDB.findAll();
    }

    @Override
    public List<ManagerModel> getManagerKosong() {
        List<ManagerModel> managerKosong = managerDB.findAll();
        for (int i = 0; i < managerKosong.size(); i++) {
            if (managerKosong.get(i).getStore() != null)
                managerKosong.remove(managerKosong.get(i));
        }
        return managerKosong;
    }

}
