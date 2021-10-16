package apap.tugas.bobaxixixi.controller;

import apap.tugas.bobaxixixi.model.BobaModel;
import apap.tugas.bobaxixixi.model.StoreBobaModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import apap.tugas.bobaxixixi.service.*;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class BobaController {

    @Qualifier("bobaServiceImpl")
    @Autowired
    private BobaService bobaService;

    @Qualifier("toppingServiceImpl")
    @Autowired
    private ToppingService toppingService;

    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @Qualifier("storeBobaServiceImpl")
    @Autowired
    private StoreBobaService storeBobaService;

    @GetMapping("/boba")
    private String storeList(Model model){
        model.addAttribute("listBoba", bobaService.getListBoba());
        return "boba/index.html";
    }

    @RequestMapping("/boba/add")
    private String getFormBoba(Model model){
        model.addAttribute("boba", new BobaModel());
        model.addAttribute("listTopping", toppingService.getListTopping());
        return "boba/form-add-boba.html";
    }

    @PostMapping("/boba/add")
    private String postFormBoba(
            @ModelAttribute BobaModel boba,
            Model model
    ){
        bobaService.addBoba(boba);
        String response = boba.getName() + " successfully added!";
        model.addAttribute("responseText", response);
        return "boba/response.html";
    }

    @GetMapping("/boba/update/{idBoba}")
    private String getUpdateFormBoba(
            @PathVariable Long idBoba,
            Model model
    ){
        BobaModel boba = bobaService.getBobaByIdBoba(idBoba);
        if (bobaService.getStatusBoba(boba)){
            model.addAttribute("boba", boba);
            model.addAttribute("listTopping", toppingService.getListTopping());
            return "boba/form-update-boba.html";
        } else {
            String response = boba.getName() + " can't be updated because there is still an open store that is selling it!";
            model.addAttribute("responseText", response);
            return "boba/response.html";
        }
    }

    @PostMapping("/boba/update/{idBoba}")
    private String postUpdateFormBoba(
            @ModelAttribute BobaModel boba,
            Model model
    ) {
        bobaService.updateBoba(boba);
        String response = boba.getName() + " Succesfully updated!";
        model.addAttribute("responseText", response);
        return "boba/response.html";
    }

    @GetMapping("/boba/delete/{idBoba}")
    public String deleteBoba(
            @PathVariable Long idBoba,
            Model model
    ) {
        BobaModel bobaModel = bobaService.getBobaByIdBoba(idBoba);
        if (bobaService.getStatusJualBoba(bobaModel)){
            bobaService.deleteBoba(bobaModel);
            String response = bobaModel.getName() + " is successfully Deleted!";
            model.addAttribute("responseText", response);
            return "boba/response.html";
        } else {
            String response = bobaModel.getName() + " is still assigned to a store and can't be deleted!";
            model.addAttribute("responseText", response);
            return "boba/response.html";
        }
    }

    @GetMapping("/boba/{idBoba}/assign-store")
    public String getAssignBoba(
            @PathVariable Long idBoba,
            Model model
    ){
        Boolean[] listChecked = new Boolean[storeService.getListStore().size()];
        Arrays.fill(listChecked, Boolean.FALSE);
        BobaModel boba = bobaService.getBobaByIdBoba(idBoba);
        Integer counter = 0;
        for (StoreModel i:storeService.getListStore()){
            for (StoreBobaModel j:i.getStoreBoba()){
                if (j.getBoba().equals(boba)){
                    listChecked[counter] = true;
                }
            }
            counter+=1;
        }

        model.addAttribute("listChecked", listChecked);
        model.addAttribute("boba", boba);
        model.addAttribute("listStore", storeService.getListStore());
        model.addAttribute("bobastore", new StoreBobaModel());
        return "boba/assign-store.html";
    }

    @PostMapping("/boba/{idBoba}/assign-store")
    public String postAssignBoba(
            @ModelAttribute StoreBobaModel bobastore,
            @PathVariable Long idBoba,
            @RequestParam(value="store")Long[] storeList,
            @RequestParam(value="allListId")Long[] allIdStore,
            Model model
    ){
        ArrayList<StoreModel> listStore = new ArrayList<>();
        BobaModel boba = bobaService.getBobaByIdBoba(idBoba);

        if(storeList.length == 1){
            for (Long i:allIdStore){
                for(StoreBobaModel sb:storeBobaService.getListStoreBoba()){
                    if (sb.getStore().getIdStore().equals(i) && sb.getBoba().getIdBoba().equals(idBoba)){
                        storeBobaService.deleteStoreBoba(sb);
                    }
                }
            }
        }

        else if(storeList.length-1 == allIdStore.length){
            for (int i=1;i<storeList.length;i++){
                if (storeBobaService.getListStoreBoba().size()==0){
                    listStore.add(storeService.getStorebyIdStore(storeList[i]));
                    StoreBobaModel temp = new StoreBobaModel();
                    temp.setBoba(bobaService.getBobaByIdBoba(idBoba));
                    temp.setStore(storeService.getStorebyIdStore(storeList[i]));
                    storeBobaService.addStoreBoba(temp);
                } else {
                    for (StoreBobaModel sb : storeBobaService.getListStoreBoba()) {
                        if (!(sb.getStore().getIdStore().equals(storeList[i]) && sb.getBoba().getIdBoba().equals(idBoba))) {
                            listStore.add(storeService.getStorebyIdStore(storeList[i]));
                            StoreBobaModel temp = new StoreBobaModel();
                            temp.setBoba(bobaService.getBobaByIdBoba(idBoba));
                            temp.setStore(storeService.getStorebyIdStore(storeList[i]));
                            storeBobaService.addStoreBoba(temp);
                        }
                    }
                }
            }
        }

        else if(storeList.length-1 != allIdStore.length){
            List<StoreModel> listStoreModelBaru = new ArrayList<>();
            for (int i=1;i<storeList.length;i++){
                listStoreModelBaru.add(storeService.getStorebyIdStore(storeList[i]));
            }

            if (storeBobaService.getListStoreBoba().size()!=0){
                for(StoreBobaModel sb:storeBobaService.getListStoreBoba()){
                    for (StoreModel s:listStoreModelBaru){
                        if (!(s.equals(sb.getStore()))){
                            storeBobaService.deleteStoreBoba(sb);
                        }
                    }
                }
            }

            for (int i=1;i<storeList.length;i++){
                if (storeBobaService.getListStoreBoba().size()==0){
                    listStore.add(storeService.getStorebyIdStore(storeList[i]));
                    StoreBobaModel temp = new StoreBobaModel();
                    temp.setBoba(bobaService.getBobaByIdBoba(idBoba));
                    temp.setStore(storeService.getStorebyIdStore(storeList[i]));
                    storeBobaService.addStoreBoba(temp);
                } else {
                    for (StoreBobaModel sb : storeBobaService.getListStoreBoba()) {
                        if (!(sb.getStore().getIdStore().equals(storeList[i]) && sb.getBoba().getIdBoba().equals(idBoba))) {
                            listStore.add(storeService.getStorebyIdStore(storeList[i]));
                            StoreBobaModel temp = new StoreBobaModel();
                            temp.setBoba(bobaService.getBobaByIdBoba(idBoba));
                            temp.setStore(storeService.getStorebyIdStore(storeList[i]));
                            storeBobaService.addStoreBoba(temp);
                        }
                    }
                }
            }
        }

        String response = "Stores Successfully Updated For Boba Tea "+boba.getName();
        model.addAttribute("listStore", listStore);
        model.addAttribute("responseText", response);
        return "boba/assign-response.html";
    }
}
