package com.example.boilposrednik;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class SolveService {
    private ArrayList<Integer> supply = new ArrayList<>(List.of(20, 30));
    private ArrayList<Integer> unitPurchaseCost = new ArrayList<>(List.of(10, 12));
    private ArrayList<Integer> demand = new ArrayList<>(List.of(10, 28, 27));
    private ArrayList<Integer> unitSalePrice = new ArrayList<>(List.of(30, 25, 30));
    private ArrayList<ArrayList<Integer>> transportCost = new ArrayList<>(List.of(
            new ArrayList<>(List.of(8, 14, 17)),
            new ArrayList<>(List.of(12, 9, 19))
    ));

    public static void main(String[] args) {
        SolveService solveService = new SolveService();
        solveService.solve();
    }
    public void solve() {

        ArrayList<ArrayList<Integer>> profit = new ArrayList<>();
        for (int i = 0; i < supply.size(); i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < demand.size(); j++) {
                int profitValue = unitSalePrice.get(j) - unitPurchaseCost.get(i) - transportCost.get(i).get(j);
                row.add(profitValue);
            }
            profit.add(row);
        }
        ArrayList<ArrayList<Integer>> allocation = new ArrayList<>();
        for (int i = 0; i < supply.size(); i++) {
            ArrayList<Integer> row = new ArrayList<>(Collections.nCopies(demand.size(), 0));
            allocation.add(row);
        }
        while (true) {
            int maxProfit = Integer.MIN_VALUE;
            int supplier = -1;
            int customer = -1;
            for (int i = 0; i < supply.size(); i++) {
                for (int j = 0; j < demand.size(); j++) {
                    if (profit.get(i).get(j) > maxProfit) {
                        maxProfit = profit.get(i).get(j);
                        supplier = i;
                        customer = j;
                    }
                }
            }
            if (maxProfit <= 0) {
                break;
            }
            int quantity = Math.min(supply.get(supplier), demand.get(customer));
            allocation.get(supplier).set(customer, quantity);
            supply.set(supplier, supply.get(supplier) - quantity);
            demand.set(customer, demand.get(customer) - quantity);
            profit.get(supplier).set(customer, 0);
        }
        int totalProfit = 0;
        for (int i = 0; i < supply.size(); i++) {
            for (int j = 0; j < demand.size(); j++) {
                totalProfit += allocation.get(i).get(j) * (unitSalePrice.get(j) - unitPurchaseCost.get(i) - transportCost.get(i).get(j));
            }
        }
        System.out.println("Total profit: " + totalProfit);
    }


}
