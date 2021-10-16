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
import java.util.*;

@Controller
public class SearchController {

    @Qualifier("bobaServiceImpl")
    @Autowired
    private BobaService bobaService;

    @Qualifier("toppingServiceImpl")
    @Autowired
    private ToppingService toppingService;

    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @GetMapping("/searchboba")
    public String getSearch(
            Model model
    ){
        model.addAttribute("listTopping", toppingService.getListTopping());
        model.addAttribute("listBoba", new HashSet<>(bobaService.getListBoba()));
        return "search/search.html";
    }

    @RequestMapping("/search")
    public String postSearch(
            @RequestParam(value = "idBoba", required = true) Long idBoba,
            @RequestParam(value = "idTopping", required = true) Long idTopping,
            Model model
    ) {
        ArrayList<BobaModel> result = new ArrayList<>();
        ArrayList<StoreModel> storeResult = new ArrayList<>();

        for (BobaModel i:bobaService.getListBoba()){
            if (i.getTopping().getIdTopping().equals(idTopping) && i.getName().equals(bobaService.getBobaByIdBoba(idBoba).getName())){
                for (StoreBobaModel j:i.getStoreBoba()){
                    if (!storeService.storeStatus(j.getStore().getIdStore())){
                        result.add(i);
                        storeResult.add(j.getStore());
                    }
                }
            }
        }

        model.addAttribute("listTopping", toppingService.getListTopping());
        model.addAttribute("listBoba", new HashSet<>(bobaService.getListBoba()));
        model.addAttribute("boba", bobaService.getBobaByIdBoba(idBoba));
        model.addAttribute("topping", toppingService.getToppingByIdTopping(idTopping));
        model.addAttribute("storeSearchResult", storeResult);
        model.addAttribute("bobaSearchResult", result);
        return "search/search-result.html";
    }
}
