package com.example.boilposrednik;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
@Slf4j
public class SolitionService {
    private ArrayList<Integer> supply = new ArrayList<>(List.of(20, 30));
    private ArrayList<Integer> unitPurchaseCost = new ArrayList<>(List.of(10, 12));
    private ArrayList<Integer> demand = new ArrayList<>(List.of(10, 28, 27));
    private ArrayList<Integer> unitSalePrice = new ArrayList<>(List.of(30, 25, 30));
    private ArrayList<ArrayList<Integer>> transportCost = new ArrayList<>(List.of(
            new ArrayList<>(List.of(8, 14, 17)),
            new ArrayList<>(List.of(12, 9, 19))
    ));
    private static double[][] result = new double[3][4];

    public static void main(String[] args) {
        SolitionService solveService = new SolitionService();
        solveService.solve();
    }

    public static int findLargestElement(List<List<Integer>> matrix, List<Integer> supply, List<Integer> demand) {
        int largestElement = Integer.MIN_VALUE;
        int k = -1, l = -1;
        for (int i = 0; i < matrix.size(); i++) {
            if (supply.get(i) > 0) {

                for (int j = 0; j < matrix.get(i).size(); j++) {
                    if (demand.get(j) > 0) {
                        int currentValue = matrix.get(i).get(j);
                        if (currentValue > largestElement && supply.get(i) > 0 && demand.get(j) > 0) {
                            k = i;
                            l = j;
                            largestElement = currentValue;
                        }
                    }
                }
            }
        }
        log.info("k = " + k + " l = " + l);
        if (supply.get(k) > demand.get(l)) {
            result[k][l] = result[k][l]+ demand.get(l);
            supply.set(k, supply.get(k) - demand.get(l));
            demand.set(l, 0);
        } else if (supply.get(k) < demand.get(l)) {
            result[k][l] = result[k][l]+ supply.get(k);
            demand.set(l, demand.get(l) - supply.get(k));
            supply.set(k, 0);
        } else if (Objects.equals(supply.get(k), demand.get(l))) {
            result[k][l] = result[k][l]+ supply.get(k);
            supply.set(k, 0);
            demand.set(l, 0);
        }


        return largestElement;
    }

    public void solve() {
        int unitProfit = unitSalePrice.get(0) - (unitPurchaseCost.get(0) + transportCost.get(0).get(0));
        log.info("{}",unitProfit);

        List<List<Integer>> profit = new ArrayList<>();
        for (int i = 0; i < supply.size(); i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < demand.size(); j++) {
                int profitValue = unitSalePrice.get(j) - unitPurchaseCost.get(i) - transportCost.get(i).get(j);
                row.add(profitValue);
            }
            profit.add(row);
        }
        profit.forEach(x->log.info("{}",x));
        log.info("-------------------");

        if (supply.stream().flatMapToInt(IntStream::of).sum()!=demand.stream().flatMapToInt(IntStream::of).sum())
        {
            result = new double[3][4];
            log.info("Zagadnienie niezbalansowane");
            for (int i = 0; i < profit.size(); i++) {
                profit.get(i).add(0);
            }
//            var notBalancedSupply=
            profit.add(new ArrayList<>(Collections.nCopies(demand.size()+1, 0)));
            supply.add(demand.stream().flatMapToInt(IntStream::of).sum());
            demand.add(supply.stream().flatMapToInt(IntStream::of).sum()-supply.get(supply.size()-1));
            log.info("supply");
            log.info("{}",supply);
            log.info("demand");
            log.info("{}",demand);
            log.info("profit");
            profit.forEach(x->log.info("{}",x));

        }
        else
        {
            log.info("Zagadnienie zbalansowane");
        }

        do {
            findLargestElement(profit, supply, demand);
//            findLargestElement(profit, supply, demand);
//            findLargestElement(profit, supply, demand);
//            findLargestElement(profit, supply, demand);
//            findLargestElement(profit, supply, demand);
//            findLargestElement(profit, supply, demand);
//            findLargestElement(profit, supply, demand);
//            findLargestElement(profit, supply, demand);
//            findLargestElement(profit, supply, demand);
        } while (supply.stream().flatMapToInt(IntStream::of).sum() != 0 && demand.stream().flatMapToInt(IntStream::of).sum() != 0);
        for (double[] doubles : result) {
            log.info(Arrays.toString(doubles));
        }
//        profit.forEach(System.out::println);
        log.info("supply");
        log.info("{}",supply);
        log.info("demand");
        log.info("{}",demand);


    }
}
