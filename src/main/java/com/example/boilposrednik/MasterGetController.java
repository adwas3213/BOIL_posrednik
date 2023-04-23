package com.example.boilposrednik;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

@Controller
public class MasterGetController {
    @GetMapping("/")
    public String welcomePage() {
        return "welcomePage";
    }
    @GetMapping("/wprowadzanieProducenta")
    public String inputProducerById(@RequestParam String id, @ModelAttribute Model model) {
        model.addAttribute("id", id);
        return "inputProducer";
    }
}
