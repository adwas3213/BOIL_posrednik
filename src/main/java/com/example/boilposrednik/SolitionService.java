package com.example.boilposrednik;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class SolitionService {
    //    private ArrayList<Integer> supply = new ArrayList<>(List.of(20, 30));
//    private ArrayList<Integer> unitPurchaseCost = new ArrayList<>(List.of(10, 12));
//    private ArrayList<Integer> demand = new ArrayList<>(List.of(10, 28, 27));
//    private ArrayList<Integer> unitSalePrice = new ArrayList<>(List.of(30, 25, 30));
//    private ArrayList<ArrayList<Integer>> transportCost = new ArrayList<>(List.of(
//            new ArrayList<>(List.of(8, 14, 17)),
//            new ArrayList<>(List.of(12, 9, 19))
//    ));
    private static double[][] result;

    public static void main(String[] args) {
        SolitionService solveService = new SolitionService();
//        solveService.solve();
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
            result[k][l] = result[k][l] + demand.get(l);
            supply.set(k, supply.get(k) - demand.get(l));
            demand.set(l, 0);
        } else if (supply.get(k) < demand.get(l)) {
            result[k][l] = result[k][l] + supply.get(k);
            demand.set(l, demand.get(l) - supply.get(k));
            supply.set(k, 0);
        } else if (Objects.equals(supply.get(k), demand.get(l))) {
            result[k][l] = result[k][l] + supply.get(k);
            supply.set(k, 0);
            demand.set(l, 0);
        }


        return largestElement;
    }

    public double calculateTotalProfit(List<Integer> buyCost, List<Integer> sellCost, List<List<Integer>> transportCostList, List<List<Double>> resultList) {
        double totalProfit = 0;
        for (int i = 0; i < resultList.size() && i < buyCost.size(); i++) {
            for (int j = 0; j < resultList.get(i).size() && j < sellCost.size(); j++) {
                int profitValue = sellCost.get(j) - buyCost.get(i) - transportCostList.get(i).get(j);
                totalProfit += profitValue * resultList.get(i).get(j);
            }
        }
        return totalProfit;
    }


    public double calculateTotalTransportCost(List<List<Integer>> transportCostList, List<List<Double>> resultList) {
        double totalTransportCost = 0;
        for (int i = 0; i < resultList.size() && i < transportCostList.size(); i++) {
            for (int j = 0; j < resultList.get(i).size() && j < transportCostList.get(i).size(); j++) {
                totalTransportCost += transportCostList.get(i).get(j) * resultList.get(i).get(j);
            }
        }
        return totalTransportCost;
    }

    public double calculateTotalPurchaseCost(List<Integer> buyCost, List<List<Double>> resultList) {
        double totalPurchaseCost = 0;
        for (int i = 0; i < resultList.size() && i < buyCost.size(); i++) {
            for (int j = 0; j < resultList.get(i).size(); j++) {
                totalPurchaseCost += buyCost.get(i) * resultList.get(i).get(j);
            }
        }
        return totalPurchaseCost;
    }

    public double calculateTotalRevenue(List<Integer> sellCost, List<List<Double>> resultList) {
        double totalRevenue = 0;
        for (int i = 0; i < resultList.size(); i++) {
            for (int j = 0; j < resultList.get(i).size() && j < sellCost.size(); j++) {
                totalRevenue += sellCost.get(j) * resultList.get(i).get(j);
            }
        }
        return totalRevenue;
    }


    public void solve(Model model, List<Integer> supplyList, List<Integer> demandList, List<Integer> buyCost, List<Integer> sellCost, List<List<Integer>> transportCostList) {
        {
            boolean notBalanced = supplyList.stream().flatMapToInt(IntStream::of).sum() != demandList.stream().flatMapToInt(IntStream::of).sum();
            List<List<Integer>> profit = new ArrayList<>();
            for (int i = 0; i < supplyList.size(); i++) {
                List<Integer> row = new ArrayList<>();
                for (int j = 0; j < demandList.size(); j++) {
                    int profitValue = sellCost.get(j) - buyCost.get(i) - transportCostList.get(i).get(j);
                    row.add(profitValue);
                }
                profit.add(row);
            }
            model.addAttribute("profit", profit);
            model.addAttribute("notBalanced", notBalanced);
            profit.forEach(x -> log.info("{}", x));
            log.info("-------------------");

            if (notBalanced) {
                result = new double[transportCostList.size() + 1][transportCostList.get(0).size() + 1];
                log.info("Zagadnienie niezbalansowane");
                for (int i = 0; i < profit.size(); i++) {
                    profit.get(i).add(0);
                }
//            var notBalancedSupply=
                profit.add(new ArrayList<>(Collections.nCopies(demandList.size() + 1, 0)));
                supplyList.add(demandList.stream().flatMapToInt(IntStream::of).sum());
                demandList.add(supplyList.stream().flatMapToInt(IntStream::of).sum() - supplyList.get(supplyList.size() - 1));
                log.info("supplyList");
                log.info("{}", supplyList);
                log.info("demandList");
                log.info("{}", demandList);
                log.info("profit");
                profit.forEach(x -> log.info("{}", x));

            } else {
                result = new double[transportCostList.size()][transportCostList.get(0).size()];
                log.info("Zagadnienie zbalansowane");
            }

            do {
                findLargestElement(profit, supplyList, demandList);

            } while (supplyList.stream().flatMapToInt(IntStream::of).sum() != 0 && demandList.stream().flatMapToInt(IntStream::of).sum() != 0);
            for (double[] doubles : result) {
                log.info(Arrays.toString(doubles));
            }
            //618 565278
//        profit.forEach(System.out::println);
            log.info("supplyList");
            log.info("{}", supplyList);
            log.info("demandList");
            log.info("{}", demandList);

            var resultList = Arrays.stream(result)
                    .map(x -> Arrays
                            .stream(x)
                            .boxed()
                            .collect(Collectors.toList()))
                    .toList();
            model.addAttribute("result", resultList);
            // Obliczenia
            double totalProfit = calculateTotalProfit(buyCost, sellCost, transportCostList, resultList);
            double totalTransportCost = calculateTotalTransportCost(transportCostList, resultList);
            double totalPurchaseCost = calculateTotalPurchaseCost(buyCost, resultList);
            double totalRevenue = calculateTotalRevenue(sellCost, resultList);

// Dodajemy wartości do modelu
            model.addAttribute("totalProfit", totalProfit);
            model.addAttribute("totalTransportCost", totalTransportCost);
            model.addAttribute("totalPurchaseCost", totalPurchaseCost);
            model.addAttribute("totalRevenue", totalRevenue);

        }

    }
}
