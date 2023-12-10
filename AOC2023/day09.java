package AOC2023;

import java.util.ArrayList;

import AOC2023.libraries.Functions;

public class day09 {
    public static void main(String[] args) {
        ArrayList<String> input = Functions.readInputFile("AOC2023/input/day09_input.txt");

        System.out.println("Part 1: " + part1(input));
        System.out.println("Part 2: " + part2(input));
    }

    static int part1(ArrayList<String> input) {
        
        ArrayList<ArrayList<ArrayList<Integer>>> oasisReport = parseInput(input);

        makeDifferenceMatrix(oasisReport);

        int sum = 0;
        for (ArrayList<ArrayList<Integer>> line : oasisReport) {
            int extrapolatedNumber = extrapolate1(line);
            sum += extrapolatedNumber;
        }

        return sum;
    }
    


    static int part2(ArrayList<String> input) {

        ArrayList<ArrayList<ArrayList<Integer>>> oasisReport = parseInput(input);

        makeDifferenceMatrix(oasisReport);

        int sum = 0;
        for (ArrayList<ArrayList<Integer>> line : oasisReport) {
            int extrapolatedNumber = extrapolate2(line);
            sum += extrapolatedNumber;
        }

        return sum;
    }


    static void makeDifferenceMatrix(ArrayList<ArrayList<ArrayList<Integer>>> oasisReport) {
        
        for (ArrayList<ArrayList<Integer>> line : oasisReport) {
            int index = 0;
            while (!arrayAllZero(line.get(line.size() - 1))) {
                ArrayList<Integer> differenceLine = new ArrayList<>();
                for (int i = 0; i < line.get(index).size() - 1; i++) {
                    int difference = line.get(index).get(i + 1) - line.get(index).get(i);
                    differenceLine.add(difference);
                }
                line.add(differenceLine);
                index++;
            }
        }

    }

    static int extrapolate1(ArrayList<ArrayList<Integer>> lines) {
        lines.get(lines.size() - 1).add(0);
        for (int i = lines.size() - 2; i >= 0; i--) {
            ArrayList<Integer> currentLine = lines.get(i);
            ArrayList<Integer> previousLine = lines.get(i + 1);
            int extrapolatedNumber = currentLine.get(currentLine.size() - 1) + previousLine.get(previousLine.size() - 1);
            currentLine.add(extrapolatedNumber);
        }
        return lines.get(0).get(lines.get(0).size() - 1);
    }


    static int extrapolate2(ArrayList<ArrayList<Integer>> lines) {
        lines.get(lines.size() - 1).add(0, 0);
        for (int i = lines.size() - 2; i >= 0; i--) {
            ArrayList<Integer> currentLine = lines.get(i);
            ArrayList<Integer> previousLine = lines.get(i + 1);
            int extrapolatedNumber = currentLine.get(0) - previousLine.get(0);
            currentLine.add(0, extrapolatedNumber);
        }
        return lines.get(0).get(0);
    }


    static boolean arrayAllZero(ArrayList<Integer> array) {
        for (int number : array) {
            if (number != 0) {
                return false;
            }
        }
        return true;
    }


    static ArrayList<ArrayList<ArrayList<Integer>>>parseInput(ArrayList<String> input) {
        ArrayList<ArrayList<ArrayList<Integer>>> parsedInput = new ArrayList<>();

        for (String line : input) {
            ArrayList<ArrayList<Integer>> parsedLine = new ArrayList<>();
            ArrayList<String> lineSplit = Functions.split(line, ' ');

            ArrayList<Integer> parsedNumbers = new ArrayList<>();
            for (String number : lineSplit) {
                parsedNumbers.add(Integer.parseInt(number));
            }
            parsedLine.add(parsedNumbers);
            parsedInput.add(parsedLine);
        }
        return parsedInput;
    }
}
