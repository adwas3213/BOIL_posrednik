package com.example.boilposrednik;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class InsertDataController {

    @PostMapping("/insertData")
    public String insertData(@RequestParam int suppliers,
                             @RequestParam int receiver,
                             @RequestParam("suppliers") List<Integer> podaż,
                             @RequestParam("receiver") List<Integer> popyt,
                             @RequestParam("buy") List<String> cena_zakupu,
                             @RequestParam("sell") List<String> cena_sprzedaży,
                             @RequestParam LinkedHashMap<String, Integer> transportCost) {
        log.info("dostawcy: " + suppliers);
        log.info("odbiorcy: " + receiver);
        log.info("podaż: " + podaż);
        log.info("popyt: " + popyt);
        log.info("cena_zakupu: " + cena_zakupu);
        log.info("cena_sprzedaży: " + cena_sprzedaży);

        int[][] test = parseTransportCosts(transportCost.entrySet().stream()
                .filter(x -> x.getKey().matches("^transportCost_(\\d+);(\\d+)$")).toList(), suppliers, receiver);
        Arrays.stream(test).forEach(x -> System.out.println(Arrays.toString(x)));
        log.info("koszty_transportu: " + transportCost.keySet());


        System.out.println("InsertDataController");
        return "inputProducer";
    }

    public static int[][] parseTransportCosts(List<Map.Entry<String, Integer>> transportCost, int suppliers, int receiver) {
        // tworzymy pustą macierz o rozmiarze 2x2
        int[][] matrix = new int[receiver][suppliers];
        System.out.println(transportCost.size());
        // iterujemy po elementach mapy
        for (Map.Entry<String, Integer> entry : transportCost) {
            // pobieramy klucz i wartość
            System.out.println(entry.getKey() + " " + entry.getValue());
            String key = entry.getKey();
            int value = Integer.parseInt(String.valueOf(entry.getValue()));
            String[] parts = key.split(";");
            int rowIndex = Integer.parseInt(parts[0].substring(14)) - 1;
            int colIndex = Integer.parseInt(parts[1]) - 1;

            // ustawiamy wartość w macierzy
            matrix[rowIndex][colIndex] = value;
        }

        // zwracamy macierz
        return matrix;
    }


}
