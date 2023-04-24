package com.example.boilposrednik;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SolutionRecord {
    private int k;
    private int l;
    private List<Integer> supply;
    private List<Integer> demand;
    private int[][] result;
}
