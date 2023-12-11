package AOC2023;

import AOC2023.libraries.*;
import java.util.ArrayList;

public class day11 {
    public static void main(String[] args) {
        ArrayList<String> input = Functions.readInputFile("AOC2023/input/day11_input.txt");
        System.out.println("Part 1: " + part1(input));
        System.out.println("Part 2: " + part2(input));
    }
    
    static long part1(ArrayList<String> input) {
        Pair<ArrayList<ArrayList<Integer>>, ArrayList<CoordinatePair>> galaxy = findGalaxies(input);
        galaxy.y = expandGalazy(galaxy.x, galaxy.y, 1);

        long sum = sumDistances(galaxy.y);
        return sum;
    }
    
    static long part2(ArrayList<String> input) {
        Pair<ArrayList<ArrayList<Integer>>, ArrayList<CoordinatePair>> galaxy = findGalaxies(input);
        galaxy.y = expandGalazy(galaxy.x, galaxy.y, 1_000_000);

        long sum = sumDistances(galaxy.y);
        return sum;
    }



    static Pair<ArrayList<ArrayList<Integer>>, ArrayList<CoordinatePair>> findGalaxies(ArrayList<String> input) {
        ArrayList<ArrayList<Integer>> galaxyMap = new ArrayList<>();
        ArrayList<CoordinatePair> galaxies = new ArrayList<>();
        for (int row = 0; row < input.size(); row++) {
            ArrayList<Integer> line = new ArrayList<>();
            for (int col = 0; col < input.get(row).length(); col++) {
                char c = input.get(row).charAt(col);
                if (c == '#') {
                    line.add(1);
                    galaxies.add(new CoordinatePair(col, row));
                } else {
                    line.add(0);
                }
            }
            galaxyMap.add(line);
        }
        return new Pair<ArrayList<ArrayList<Integer>>, ArrayList<CoordinatePair>>(galaxyMap, galaxies);
    }


    static ArrayList<CoordinatePair> expandGalazy(ArrayList<ArrayList<Integer>> galaxyMap, ArrayList<CoordinatePair> galaxies, int expantionFactotor) {
        if (expantionFactotor > 1) {
            expantionFactotor--;
        }
        ArrayList<CoordinatePair> newGalaxies = new ArrayList<>();
        for (CoordinatePair galaxy : galaxies) {
            newGalaxies.add(new CoordinatePair(galaxy));
        }

        for (int row = 0; row < galaxyMap.size(); row++) {
            if (sumRow(galaxyMap.get(row)) == 0) {
                for (int i = 0; i < galaxies.size(); i++) {
                    CoordinatePair galaxy = galaxies.get(i);
                    if (galaxy.y > row) {
                        newGalaxies.get(i).y += expantionFactotor;
                    }
                }
            }
        }
        for (int col = 0; col < galaxyMap.get(0).size(); col++) {
            if (sumColumn(galaxyMap, col) == 0) {
                for (int i = 0; i < galaxies.size(); i++) {
                    CoordinatePair galaxy = galaxies.get(i);
                    if (galaxy.x > col) {
                        newGalaxies.get(i).x += expantionFactotor;
                    }
                }
            }
        }
        return newGalaxies;
    }

    
    
    static long sumDistances(ArrayList<CoordinatePair> galaxies) {
        long sum = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                sum += findDistance(galaxies.get(i), galaxies.get(j));
            }
        }
        return sum;
    }
    static long findDistance(CoordinatePair a, CoordinatePair b) {
        return Math.abs(a.y - b.y) + Math.abs(a.x - b.x);
    }
    
    

    public static int sumRow(ArrayList<Integer> arr) {
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        return sum;
    }
    public static int sumColumn(ArrayList<ArrayList<Integer>> arr, int col) {
        int sum = 0;
        for (ArrayList<Integer> row : arr) {
            sum += row.get(col);
        }
        return sum;
    }
}

