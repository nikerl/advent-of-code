import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class day05 {
    public static void main(String[] args) {
        ArrayList<String> input = Functions.readInputFile("AOC2023/input/day05_input.txt");

        long part1 = part1(input);
        System.out.println("Part 1: " + part1);

        long part2 = part2(input);
        System.out.println("Part 2: " + part2);
        
    }
    
    
    static long part1(ArrayList<String> input) {
        int index = 0;
    
        ArrayList<Long> seeds = new ArrayList<Long>();
        String[] tempSeeds = input.get(index).split(" ");
        index += 3;
        for (int i = 1; i < tempSeeds.length; i++) {
            seeds.add(Long.parseLong(tempSeeds[i]));
        }

        long min = getMinLocation(input, seeds, index);
        return min;      
    }

    static long part2(ArrayList<String> input) {
        /* int index = 0;

        ArrayList<Long> seeds = new ArrayList<Long>();
        String[] tempSeeds = input.get(index).split(" ");
        index += 3;
        for (int i = 1; i < tempSeeds.length; i++) {
            long startRange = Long.parseLong(tempSeeds[i++]);
            long rangeLength = Long.parseLong(tempSeeds[i]);
            for (long j = startRange; j < startRange + rangeLength; j++) {
                seeds.add(j);
            }
        }

        long min = getMinLocation(input, seeds, index);
        return min; */

        int index = 0;
    
        ArrayList<Long> seeds = new ArrayList<Long>();
        String[] tempSeeds = input.get(index).split(" ");
        index += 3;
        for (int i = 1; i < tempSeeds.length; i++) {
            seeds.add(Long.parseLong(tempSeeds[i]));
        }

        long min = getMinLocation(input, seeds, index);
        return min;    
    }


    static long getMinLocation(ArrayList<String> input, ArrayList<Long> seeds, int index) {
        while (index < input.size()) {
            ArrayList<String> map = new ArrayList<String>();
            while (index < input.size() && input.get(index).length() != 0) {
                map.add(input.get(index));
                index++;
            }
            index += 2;
    
            ArrayList<Long> mappedSeeds = map(map, seeds);
            seeds = mappedSeeds;
        }
    
        long min = Long.MAX_VALUE;
        for (long seed : seeds) {
            if (seed < min) {
                min = seed;
            }
        }
    
        return min;
    }


    static ArrayList<Long> map(ArrayList<String> map, ArrayList<Long> seeds) {  
        ArrayList<Long> mappedSeeds = new ArrayList<Long>();

        for (String line : map) {
            String[] ranges = line.split(" ");
            long destinationRange = Long.parseLong(ranges[0]);
            long sourceRange = Long.parseLong(ranges[1]);
            long rangeLength = Long.parseLong(ranges[2]);

            Iterator<Long> i = seeds.iterator();
            while (i.hasNext()) {
                long seed = i.next();
                if (seed >= sourceRange && seed <= sourceRange + rangeLength) {
                    long mappedSeed = seed - sourceRange + destinationRange;
                    mappedSeeds.add(mappedSeed);
                    i.remove();
                }
            }
        }

        if (seeds.size() > 0) {
            for (long seed : seeds) {
                mappedSeeds.add(seed);
            }
        }

        return mappedSeeds;
    }

    static ArrayList<Long> map2(ArrayList<String> map, ArrayList<Long> seeds) {  
        ArrayList<Long> mappedSeeds = new ArrayList<Long>();
        ArrayList<ArrayList<Long>> mapList = new ArrayList<ArrayList<Long>>();

        for (int i = 0; i < map.size(); i++) {
            String line = map.get(i);
            mapList.add(new ArrayList<Long>());
            String[] ranges = line.split(" ");
            mapList.get(i).add(Long.parseLong(ranges[0])); // destination range
            mapList.get(i).add(Long.parseLong(ranges[1])); // source range
            mapList.get(i).add(Long.parseLong(ranges[2])); // range length
        }

        
        for (int i = 0; i < seeds.size(); i++) {
            long seed = seeds.get(i++);
            long seedLength = seeds.get(i);

            for (int j = 0; j < mapList.size(); j++) {
                long destinationRange = mapList.get(j).get(0);
                long sourceRange = mapList.get(j).get(1);
                long rangeLength = mapList.get(j).get(2);

                if (seed >= sourceRange && seed <= sourceRange + rangeLength) {
                    long mappedSeed = seed - sourceRange + destinationRange;
                    mappedSeeds.add(mappedSeed);
                }
                if (seed + seedLength > sourceRange + rangeLength) {
                    long mappedSeed = seed;
                    mappedSeeds.add(mappedSeed);
                }
            }
        }


        if (seeds.size() > 0) {
            for (long seed : seeds) {
                mappedSeeds.add(seed);
            }
        }

        return mappedSeeds;
    }
}
