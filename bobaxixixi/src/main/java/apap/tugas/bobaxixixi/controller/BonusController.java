package apap.tugas.bobaxixixi.controller;

import apap.tugas.bobaxixixi.model.BobaModel;
import apap.tugas.bobaxixixi.model.StoreBobaModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import apap.tugas.bobaxixixi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class BonusController {
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

    @GetMapping("/bonuses")
    public String formKeywordBoba(){
        return "bonus/bonus.html";
    }

    @GetMapping("/bonus")
    public String getKeywordBoba(
            @RequestParam(value = "keyword", required = true) String keyword,
            Model model
    ){
        ArrayList<BobaModel> result = new ArrayList<>();
        ArrayList<StoreModel> storeResult = new ArrayList<>();

        for (BobaModel i:bobaService.getListBoba()){
            if (i.getName().toLowerCase().contains(keyword.toLowerCase())){
                result.add(i);
            }
        }

        for (StoreModel i:storeService.getListStore()){
            if (i.getName().toLowerCase().contains(keyword.toLowerCase())){
                storeResult.add(i);
            }
        }

        model.addAttribute("myKeyword", keyword);
        model.addAttribute("bobaResult", result);
        model.addAttribute("storeResult", storeResult);
        return "bonus/bonus-result.html";
    }
}
