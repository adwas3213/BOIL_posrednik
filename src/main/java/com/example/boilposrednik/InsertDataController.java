package com.example.boilposrednik;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class InsertDataController {
    private final SolitionService solitionService;

    @PostMapping("/insertData")
    public String insertData(
                            Model model,
                             @RequestParam(value = "supplyList",required = false) List<Integer> supplyList,
                             @RequestParam(value= "demandList",required = false) List<Integer> demandList,
                             @RequestParam(value="buyCost",required = false) List<String> buyCost,
                             @RequestParam(value="sellCost",required = false) List<String> sellCost,
                             @RequestParam LinkedHashMap<String, Integer> transportCost) {
//        log.info("dostawcy: " + supplyList.size());
//        log.info("odbiorcy: " + demandList.size());
        log.info("podaż: " + supplyList);
        log.info("popyt: " + demandList);
        log.info("cena_zakupu: " + buyCost);
        log.info("cena_sprzedaży: " + sellCost);

        int[][] transportCostsArray = parseTransportCosts(transportCost.entrySet().stream()
                .filter(x -> x.getKey().matches("^transportCost_(\\d+);(\\d+)$")).toList(), supplyList.size(), demandList.size());
        Arrays.stream(transportCostsArray).forEach(x -> System.out.println(Arrays.toString(x)));
        log.info("koszty_transportu: " + transportCost.keySet());


       var transportCostList = Arrays.stream(transportCostsArray)
                .map(x -> Arrays
                        .stream(x)
                        .boxed()
                        .collect(Collectors.toList()))
                .toList();
        solitionService.solve(model,supplyList, demandList, buyCost.stream().map(Integer::parseInt).toList(), sellCost.stream().map(Integer::parseInt).toList(), transportCostList);
        System.out.println("InsertDataController");
        return "inputProducer";
    }

    public static int[][] parseTransportCosts(List<Map.Entry<String, Integer>> transportCost, int suppliers, int receiver) {
        int[][] matrix = new int[suppliers][receiver];
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
