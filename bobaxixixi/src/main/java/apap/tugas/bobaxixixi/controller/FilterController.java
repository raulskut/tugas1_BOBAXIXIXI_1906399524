package apap.tugas.bobaxixixi.controller;

import apap.tugas.bobaxixixi.model.BobaModel;
import apap.tugas.bobaxixixi.model.ManagerModel;
import apap.tugas.bobaxixixi.model.StoreBobaModel;
import apap.tugas.bobaxixixi.service.BobaService;
import apap.tugas.bobaxixixi.service.ManagerService;
import apap.tugas.bobaxixixi.service.StoreBobaService;
import apap.tugas.bobaxixixi.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class FilterController {

    @Qualifier("bobaServiceImpl")
    @Autowired
    private BobaService bobaService;

    @GetMapping("/filters")
    public String filterBobaByManager(
            Model model
    ) {
        model.addAttribute("listBoba", bobaService.getListBoba());
        return "filter/filter.html";
    }

    @GetMapping("/filter")
    public String postFilterBobaByManager(
            @RequestParam(value = "idBoba", required = true) Long idBoba,
            Model model
    ) {
        ArrayList<ManagerModel> result = new ArrayList<>();

        for (BobaModel i:bobaService.getListBoba()){
            if (i.getName().equals(bobaService.getBobaByIdBoba(idBoba).getName())){
                for (StoreBobaModel sb:i.getStoreBoba()){
                    result.add(sb.getStore().getManager());
                }
            }
        }

        System.out.println(result);
        model.addAttribute("listBoba", bobaService.getListBoba());
        model.addAttribute("searchResultManager", result);
        model.addAttribute("selectedBoba", bobaService.getBobaByIdBoba(idBoba).getName());
        return "filter/filter-result.html";
    }
}
