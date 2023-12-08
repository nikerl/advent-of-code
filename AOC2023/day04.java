package AOC2023;

import AOC2023.libraries.Functions;
import java.util.ArrayList;

public class day04 {
    public static void main(String[] args) {
        ArrayList<String> input = Functions.readInputFile("AOC2023/input/day04_input.txt");

        int sum1 = part1(input);
        System.out.println("Part 1: " + sum1);

        int sum2 = part2(input);
        System.out.println("Part 2: " + sum2);
    }



    static int part1(ArrayList<String> input) {
        int sum = 0;
        for (String card : input) {
            sum += cardValue(card);
        }

        return sum;
    }

    static int part2(ArrayList<String> input) {
        @SuppressWarnings("unchecked")
        ArrayList<String>[] scratchCards = new ArrayList[input.size()];
        for (int i = 0; i < scratchCards.length; i++) {
            scratchCards[i] = new ArrayList<String>();
            scratchCards[i].add(input.get(i));
        }

        for (int i = 0; i < input.size(); i++) {
            int numberOfMatches = matchingNumbers(input.get(i));
            for (int j = i+1; j < i+numberOfMatches+1 && j < input.size(); j++) {
                for (int k = 0; k < scratchCards[i].size(); k++) {
                    scratchCards[j].add(input.get(j));
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < scratchCards.length; i++) {
            sum += scratchCards[i].size();
        }
        return sum;
    }




    static int cardValue(String line) {
        int power = matchingNumbers(line);
        int value = 0;
        if (power > 0) {
            value = pow(2, power);
        }
        else {
            value = 0;
        }
        return value;
    }

    static int matchingNumbers(String line) {
        int sum = 0;

        ArrayList<String> card = split(line, ':');
        ArrayList<String> lists = split(card.get(1), '|');
        ArrayList<String> winningNumbers = split(lists.get(0), ' ');
        ArrayList<String> yourNumbers = split(lists.get(1), ' ');

        for (String winningNumber : winningNumbers) {
            if (yourNumbers.contains(winningNumber)) {
                sum++;
            }
        }

        return sum;
    }

    static ArrayList<String> split(String input, char delimiter) {
        ArrayList<String> output = new ArrayList<String>();

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != delimiter) {
                StringBuilder sb = new StringBuilder();
                while (i < input.length() && input.charAt(i) != delimiter) {
                    sb.append(input.charAt(i));
                    i++;
                }
                output.add(sb.toString());
            }
        }
        return output;
    }

    static int pow(int base, int power) {
        int result = 1;
        for (int i = 0; i < power-1; i++) {
            result *= base;
        }
        return result;
    }
}
