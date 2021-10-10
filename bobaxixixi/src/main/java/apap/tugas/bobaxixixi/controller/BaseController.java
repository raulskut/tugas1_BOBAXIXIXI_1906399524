package apap.tugas.bobaxixixi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping("/")
    private String home(Model model){
        return "index.html";
    }
}
