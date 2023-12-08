package AOC2023;

import AOC2023.libraries.Functions;
import java.util.ArrayList;
import java.lang.Math;

public class day06 {
    public static void main(String[] args) {
        ArrayList<String> input = Functions.readInputFile("AOC2023/input/day06_input.txt");

        int part1 = part1(input);
        System.out.println("Part 1: " + part1);

        int part2 = part2(input);
        System.out.println("Part 2: " + part2);
        
    }

    static int part1(ArrayList<String> input) {
        int product = 1;

        ArrayList<String> time = Functions.split(input.get(0).split(":")[1], ' ');
        ArrayList<String> distance = Functions.split(input.get(1).split(":")[1], ' ');

        for (int i = 0; i < time.size(); i++) {
            double raceTime = Integer.parseInt(time.get(i));
            double record = Integer.parseInt(distance.get(i)) + 0.000001;

            product *= waysToWin(raceTime, record);
        }

        return product;      
    }

    static int part2(ArrayList<String> input) {
        ArrayList<String> timeArr = Functions.split(input.get(0).split(":")[1], ' ');
        ArrayList<String> distanceArr = Functions.split(input.get(1).split(":")[1], ' ');

        String raceTimeStr = "";
        String recordStr = "";
        for (int i = 0; i < timeArr.size(); i++) {
            raceTimeStr += timeArr.get(i);
            recordStr += distanceArr.get(i);
        }
        double raceTime = Double.parseDouble(raceTimeStr);
        double record = Double.parseDouble(recordStr) + 0.000001;

        int waysToWin = waysToWin(raceTime, record);
        return waysToWin;
    }

    static int waysToWin(double raceTime, double record) {
        double lowerBound = Math.ceil((raceTime / 2) - Math.sqrt(Math.pow((raceTime / 2), 2) - record));
        double upperBound = Math.floor((raceTime / 2) + Math.sqrt(Math.pow((raceTime / 2), 2) - record));

        return (int) (upperBound - lowerBound + 1);
    }
}
