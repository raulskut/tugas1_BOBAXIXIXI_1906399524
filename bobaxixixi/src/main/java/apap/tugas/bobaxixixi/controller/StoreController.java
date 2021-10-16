package apap.tugas.bobaxixixi.controller;

import apap.tugas.bobaxixixi.model.BobaModel;
import apap.tugas.bobaxixixi.model.ManagerModel;
import apap.tugas.bobaxixixi.model.StoreBobaModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import apap.tugas.bobaxixixi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class StoreController {

    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @Qualifier("managerServiceImpl")
    @Autowired
    private ManagerService managerService;

    @Qualifier("bobaServiceImpl")
    @Autowired
    private BobaService bobaService;

    @Qualifier("storeBobaServiceImpl")
    @Autowired
    private StoreBobaService storeBobaService;

    @GetMapping("/store")
    private String storeList(Model model){
        model.addAttribute("listStore", storeService.getListStore());
        return "store/index.html";
    }

    @RequestMapping("/store/add")
    private String getFormStore(Model model){
        model.addAttribute("store", new StoreModel());
        model.addAttribute("listManager", managerService.getManagerKosong());
        return "store/form-add-store.html";
    }

    @PostMapping("/store/add")
    private String postFormStore(
            @ModelAttribute StoreModel store,
            Model model
    ){
        storeService.addStore(store);
        String response = store.getName() + " Store with store code " + storeService.generateStoreCode(store) + " successfully added!";
        model.addAttribute("responseText", response);
        return "store/response.html";
    }

    @RequestMapping("/store/{idStore}")
    private String viewDetailStore(
            @PathVariable Long idStore,
            Model model
    ) {
        StoreModel store = storeService.getStorebyIdStore(idStore);
        ArrayList<BobaModel> listBoba = new ArrayList<>();
        for (int i=0;i<store.getStoreBoba().size();i++){
            System.out.print(store.getStoreBoba().get(i).getBoba().getName());
            listBoba.add(store.getStoreBoba().get(i).getBoba());
        }
        model.addAttribute("store", store);
        model.addAttribute("manager", store.getManager());
        model.addAttribute("listBoba", listBoba);
        return "store/view-store.html";
    }

    @GetMapping("/store/update/{idStore}")
    private String getUpdateFormStore(
            @PathVariable Long idStore,
            Model model
    ){
        StoreModel store = storeService.getStorebyIdStore(idStore);
        if (storeService.storeStatus(store.getIdStore())){
            model.addAttribute("store", store);
            model.addAttribute("listManager", managerService.getManagerKosong());
            model.addAttribute("namaManager", store.getManager().getFullName());
            model.addAttribute("idManager", store.getManager().getIdManager());
            return "store/form-update-store.html";
        } else {
            String response = store.getName() + " Store is currently open and can't be updated!";
            model.addAttribute("responseText", response);
            return "store/response.html";
        }
    }

    @PostMapping("/store/update/{idStore}")
    private String postUpdateFormStore(
            @ModelAttribute StoreModel store,
            Model model
    ) {
        storeService.updateStore(store);
        String response = store.getName() + " Store with store code " + storeService.generateStoreCode(store) + " successfully updated!";
        model.addAttribute("responseText", response);
        return "store/response.html";
    }

    @GetMapping("/store/delete/{idStore}")
    private String deleteStore(
            @PathVariable Long idStore,
            Model model
    ) {
        StoreModel store = storeService.getStorebyIdStore(idStore);
        ManagerModel manager = store.getManager();
        if(storeService.ableToDelete(store)){
            storeService.deleteStore(store);
            managerService.addManager(manager);
            String response = store.getName() + " Store is succesfully Deleted!";
            model.addAttribute("responseText", response);
        } else {
            String response = store.getName() + " Store still has Boba Tea and can't be deleted";
            model.addAttribute("responseText", response);
        }

        return "store/response.html";
    }

    @GetMapping("/store/{idStore}/assign-boba")
    public String getAssignBoba(
            @PathVariable Long idStore,
            Model model
    ){
        Boolean[] listChecked = new Boolean[bobaService.getListBoba().size()];
        Arrays.fill(listChecked, Boolean.FALSE);
        StoreModel store = storeService.getStorebyIdStore(idStore);
        Integer counter = 0;
        for (BobaModel i:bobaService.getListBoba()){
            for (StoreBobaModel j:i.getStoreBoba()){
                if (j.getStore().equals(store)){
                    listChecked[counter] = true;
                }
            }
            counter+=1;
        }

        model.addAttribute("listChecked", listChecked);
        model.addAttribute("store", store);
        model.addAttribute("listBoba", bobaService.getListBoba());
        model.addAttribute("storeboba", new StoreBobaModel());
        return "store/assign-boba.html";
    }

    @PostMapping("/store/{idStore}/assign-boba")
    public String postAssignBoba(
            @ModelAttribute StoreBobaModel storeboba,
            @PathVariable Long idStore,
            @RequestParam(value="boba")Long[] bobaList,
            @RequestParam(value="allIdBoba")Long[] allIdBoba,
            Model model
    ){
        ArrayList<BobaModel> listBoba = new ArrayList<>();
        StoreModel store = storeService.getStorebyIdStore(idStore);

        if(bobaList.length == 1){
            for (Long i:allIdBoba){
                for(StoreBobaModel sb:storeBobaService.getListStoreBoba()){
                    if (sb.getBoba().getIdBoba().equals(i) && sb.getStore().getIdStore().equals(idStore)){
                        storeBobaService.deleteStoreBoba(sb);
                    }
                }
            }
        }

        else if(bobaList.length-1 == allIdBoba.length){
            for (int i=1;i<bobaList.length;i++){
                if (storeBobaService.getListStoreBoba().size()==0){
                    listBoba.add(bobaService.getBobaByIdBoba(bobaList[i]));
                    StoreBobaModel temp = new StoreBobaModel();
                    temp.setBoba(bobaService.getBobaByIdBoba(bobaList[i]));
                    temp.setStore(storeService.getStorebyIdStore(idStore));
                    storeBobaService.addStoreBoba(temp);
                } else {
                    for (StoreBobaModel sb : storeBobaService.getListStoreBoba()) {
                        if (!(sb.getBoba().getIdBoba().equals(bobaList[i]) && sb.getStore().getIdStore().equals(idStore))) {
                            listBoba.add(bobaService.getBobaByIdBoba(bobaList[i]));
                            StoreBobaModel temp = new StoreBobaModel();
                            temp.setBoba(bobaService.getBobaByIdBoba(bobaList[i]));
                            temp.setStore(storeService.getStorebyIdStore(idStore));
                            storeBobaService.addStoreBoba(temp);
                        }
                    }
                }
            }
        }

        else if(bobaList.length-1 != allIdBoba.length){
            List<BobaModel> listBobaModelBaru = new ArrayList<>();
            for (int i=1;i<bobaList.length;i++){
                listBobaModelBaru.add(bobaService.getBobaByIdBoba(bobaList[i]));
            }

            if (storeBobaService.getListStoreBoba().size()!=0){
                for(StoreBobaModel sb:storeBobaService.getListStoreBoba()){
                    for (BobaModel s:listBobaModelBaru){
                        if (!(s.equals(sb.getBoba()))){
                            storeBobaService.deleteStoreBoba(sb);
                        }
                    }
                }
            }

            for (int i=1;i<bobaList.length;i++){
                if (storeBobaService.getListStoreBoba().size()==0){
                    listBoba.add(bobaService.getBobaByIdBoba(bobaList[i]));
                    StoreBobaModel temp = new StoreBobaModel();
                    temp.setBoba(bobaService.getBobaByIdBoba(bobaList[i]));
                    temp.setStore(storeService.getStorebyIdStore(idStore));
                    storeBobaService.addStoreBoba(temp);
                } else {
                    for (StoreBobaModel sb : storeBobaService.getListStoreBoba()) {
                        if (!(sb.getBoba().getIdBoba().equals(bobaList[i]) && sb.getStore().getIdStore().equals(idStore))) {
                            listBoba.add(bobaService.getBobaByIdBoba(bobaList[i]));
                            StoreBobaModel temp = new StoreBobaModel();
                            temp.setBoba(bobaService.getBobaByIdBoba(bobaList[i]));
                            temp.setStore(storeService.getStorebyIdStore(idStore));
                            storeBobaService.addStoreBoba(temp);
                        }
                    }
                }
            }
        }

        String response = "Boba Tea's Successfully Updated For Store " + store.getName();
        model.addAttribute("listBoba", listBoba);
        model.addAttribute("responseText", response);
        return "store/assign-response.html";
    }
}
