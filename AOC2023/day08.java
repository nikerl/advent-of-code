package AOC2023;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import AOC2023.libraries.Functions;

public class day08 {
    public static void main(String[] args) {
        ArrayList<String> input = Functions.readInputFile("AOC2023/input/day08_input.txt");

        System.out.println("Part 1: " + part1(input));
        System.out.println("Part 2: " + part2(input));
    }

    static int part1(ArrayList<String> input) {
        char[] directions = input.get(0).toCharArray();
        HashMap<String, String[]> map = parseMap(input);

        return countSteps1(map, directions);
    }

    static int part2(ArrayList<String> input) {
        char[] directions = input.get(0).toCharArray();
        HashMap<String, String[]> map = parseMap(input);

        return countSteps2(map, directions);
    }


    static HashMap<String, String[]> parseMap(ArrayList<String> input) {
        HashMap<String, String[]> map = new HashMap<>();

        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            String node = line.substring(0, 3);
            String[] nextNode = {line.substring(7, 10), line.substring(12, 15)};
            map.put(node, nextNode);
        }
        return map;
    }

    static int countSteps1(HashMap<String, String[]> map, char[] directions) {
        final String START_NODE = "AAA";
        final String END_NODE = "ZZZ";
        int steps = 0;

        String currentNode = START_NODE;
        int dirIndex = 0;
        while (!currentNode.equals(END_NODE)) {
            String[] nextNode = map.get(currentNode);
            switch (directions[dirIndex]) {
                case 'L':
                    currentNode = nextNode[0];
                    break;
                case 'R':
                    currentNode = nextNode[1];
                    break;
            }
            dirIndex = (dirIndex + 1) % directions.length;
            steps++;
        }
        return steps;
    }

    static int countSteps2(HashMap<String, String[]> map, char[] directions) {
        ArrayList<String> startNodes = findStartNodes(map);
        boolean[] finishedNodes = new boolean[startNodes.size()];
        int steps = 0;

        int dirIndex = 0;
        while (true) {
            for (int i = 0; i < startNodes.size(); i++) {
                String[] nextNode = map.get(startNodes.get(i));
                switch (directions[dirIndex]) {
                    case 'L':
                        startNodes.set(i, nextNode[0]);
                        break;
                    case 'R':
                        startNodes.set(i, nextNode[1]);
                        break;
                }
                if (startNodes.get(i).charAt(2) == 'Z') {
                    finishedNodes[i] = true;
                }
                else {
                    finishedNodes[i] = false;
                }
            }
            dirIndex = (dirIndex + 1) % directions.length;
            steps++;

            if (allBoolTrue(finishedNodes)) {
                break;
            }
        }
        return steps;
    }

    static ArrayList<String> findStartNodes(HashMap<String, String[]> map) {
        ArrayList<String> startNodes = new ArrayList<>();
        for (String node : map.keySet()) {
            if (node.charAt(2) == 'A') {
                startNodes.add(node);
            }
        }
        return startNodes;
    }

    static Boolean allBoolTrue(boolean[] arr) {
        for (boolean b : arr) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

}
