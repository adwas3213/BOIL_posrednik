package com.example.boilposrednik;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InsertDataController {

    @PostMapping("/insertData")
        public void insertData() {
            System.out.println("InsertDataController");
        }
    @GetMapping("/")
        public String welcomePage() {
            return "welcomePage.html";
        }
}
