package AOC2023;

import java.math.BigInteger;
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

        return loopLength(map, directions, "AAA");
    }

    static BigInteger part2(ArrayList<String> input) {
        char[] directions = input.get(0).toCharArray();
        HashMap<String, String[]> map = parseMap(input);
        ArrayList<String> startNodes = findStartNodes(map);

        int[] loopLengths = new int[startNodes.size()];

        for (int i = 0; i < startNodes.size(); i++) {
            loopLengths[i] = loopLength(map, directions, startNodes.get(i));
        }

        return lcm(loopLengths);
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

    static int loopLength(HashMap<String, String[]> map, char[] directions, String startNode) {
        int length = 0;

        int dirIndex = 0;
        String currentNode = startNode;
        while (true) {
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
            length++;
            
            if (currentNode.charAt(2) == 'Z') {
                break;
            }
        }
        return length;
    }



    static BigInteger lcm(int[] numbers) {
        BigInteger[] products = new BigInteger[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            BigInteger product = BigInteger.ONE;
            for (int j = 0; j < numbers.length; j++) {
                if (i != j) {
                    product = product.multiply(BigInteger.valueOf(numbers[j]));
                }
            }
            products[i] = product;
        }
        
        BigInteger gcd = products[0];
        for (int i = 1; i < products.length; i++) {
            gcd = gcd(gcd, products[i]);
            if (gcd.equals(BigInteger.ONE)) {
                break;
            }
        }
        
        BigInteger lcm = (products[0].multiply(BigInteger.valueOf(numbers[0]))).divide(gcd);
        return lcm;
    }
    
    static BigInteger gcd(BigInteger a, BigInteger b) {
        BigInteger t;
        while (!b.equals(BigInteger.ZERO)) {
            t = b;
            b = a.mod(b);
            a = t;
        }
        return a;
    }
}
