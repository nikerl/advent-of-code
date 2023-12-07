import java.time.LocalDateTime;
import java.util.ArrayList;

public class day05 {
    public static void main(String[] args) {
        ArrayList<String> input = Functions.readInputFile("AOC2023/input/day05_input.txt");

        long part1 = part1(input);
        System.out.println("Part 1: " + part1);

        System.out.println(LocalDateTime.now());
        long part2 = part2(input);
        System.out.println("Part 2: " + part2);
        System.out.println(LocalDateTime.now());
        
    }
    
    static long part1(ArrayList<String> input) {    
        ArrayList<Long> seeds = new ArrayList<Long>();
        String[] tempSeeds = input.get(0).split(" ");
        for (int i = 1; i < tempSeeds.length; i++) {
            seeds.add(Long.parseLong(tempSeeds[i]));
        }

        ArrayList<ArrayList<ArrayList<Long>>> almanac = parseAlmanac(input);

        long min = getMinLocation1(almanac, seeds);
        return min;      
    }

    static long part2(ArrayList<String> input) {
        int index = 0;
        ArrayList<ArrayList<ArrayList<Long>>> almanac = parseAlmanac(input);
    
        ArrayList<SeedRange> seeds = parseSeeds(input.get(0));

        long min = getMinLocation2(almanac, seeds, index);
        return min;    
    }
    

    static long getMinLocation1(ArrayList<ArrayList<ArrayList<Long>>> input, ArrayList<Long> seeds) {
        long minLocation = Long.MAX_VALUE;
        for (long seed : seeds) {
            long tempSeed = seed;
            for (ArrayList<ArrayList<Long>> map : input) {    
                tempSeed = map(map, tempSeed);
            }
            if (tempSeed < minLocation) {
                minLocation = tempSeed;
            }

        }
        return minLocation;
    }


    static long getMinLocation2(ArrayList<ArrayList<ArrayList<Long>>> input, ArrayList<SeedRange> seeds, int index) {
        long minLocation = Long.MAX_VALUE;
        for (SeedRange seedRange : seeds) {

            for (long seed = seedRange.startSeed; seed < seedRange.startSeed + seedRange.rangeLength + 1; seed++) {
                long tempSeed = seed;
                for (ArrayList<ArrayList<Long>> map : input) {    
                    tempSeed = map(map, tempSeed);
                }
                if (tempSeed < minLocation) {
                    minLocation = tempSeed;
                }
            }
        }
        return minLocation;
    }

    static long map(ArrayList<ArrayList<Long>> map, long seed) {  
        for (ArrayList<Long> line : map) {
            long destinationRange = line.get(0);
            long sourceRange = line.get(1);
            long rangeLength = line.get(2);

            if (seed >= sourceRange && seed < sourceRange + rangeLength) {
                seed = seed - sourceRange + destinationRange;
                return seed;
            }     
        }

        return seed;
    }

    static ArrayList<SeedRange> parseSeeds(String input) {
        ArrayList<SeedRange> seeds = new ArrayList<SeedRange>();
        String[] tempSeeds = input.split(" ");
        for (int i = 1; i < tempSeeds.length; i++) {
            seeds.add(new SeedRange(Long.parseLong(tempSeeds[i++]), Long.parseLong(tempSeeds[i])));
        }
        return seeds;
    }

    static ArrayList<ArrayList<ArrayList<Long>>> parseAlmanac(ArrayList<String> input) {
        ArrayList<ArrayList<ArrayList<Long>>> almanac = new ArrayList<ArrayList<ArrayList<Long>>>();
        int index = 3;

        while (index < input.size()) {
            ArrayList<ArrayList<Long>> map = new ArrayList<ArrayList<Long>>();
            while (index < input.size() && input.get(index).length() != 0) {
                String[] ranges = input.get(index).split(" ");
                ArrayList<Long> line = new ArrayList<Long>();
                line.add(Long.parseLong(ranges[0])); //destinationRange
                line.add(Long.parseLong(ranges[1])); //sourceRange
                line.add(Long.parseLong(ranges[2])); //rangeLength
                map.add(line);
                index++;
            }
            almanac.add(map);
            index += 2;
        }

        return almanac;
    }
}

class SeedRange {
    long startSeed;
    long rangeLength;

    public SeedRange(long startSeed, long rangeLength) {
        this.startSeed = startSeed;
        this.rangeLength = rangeLength;
    }
}
