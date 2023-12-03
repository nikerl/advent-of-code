import java.util.ArrayList;

public class day03 {
    public static void main(String[] args) {
        ArrayList<String> input = Functions.readInputFile("AOC2023/input/day03_input.txt");

        int sum1 = part1(input);
        System.out.println("Part 1: " + sum1);

        int sum2 = part2(input);
        System.out.println("Part 2: " + sum2);
        
    }



    static int part1(ArrayList<String> input) {
        String all = "[@&*$#=/+%\\-]+";
        return findPartNumbers(input, all).sumPartNumbers;
    }



    static int part2(ArrayList<String> input) {
        String regex = "[*]+";
        ArrayList<PartNumber> partNumbers = findPartNumbers(input, regex).partNumbers;

        int sum = 0;

        for (int i = 0; i < partNumbers.size(); i++) {
            int gearRatio = 1;
            int numberOfGears = 0;
            for (int j = i+1; j < partNumbers.size(); j++) {
                if (partNumbers.get(i).gear.col == partNumbers.get(j).gear.col && partNumbers.get(i).gear.row == partNumbers.get(j).gear.row) {
                    numberOfGears++;
                    gearRatio *= partNumbers.get(i).number * partNumbers.get(j).number;
                }
            }
            
            if (numberOfGears == 1) {
                sum += gearRatio;
            }
        }
        
        return sum;
    }


    static EngineSchematic findPartNumbers(ArrayList<String> input, String regex) {
        int sum = 0;
        ArrayList<PartNumber> partNumbers = new ArrayList<PartNumber>();
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                int numberStart = 0;
                int numberEnd = 0;
                PartNumber partNumber = new PartNumber(-1);

                if (Character.isDigit(input.get(i).charAt(j))) {
                    numberStart = j;
                    while (j < input.get(i).length() && Character.isDigit(input.get(i).charAt(j))) {
                        j++;
                    }
                    numberEnd = j;
                    partNumber.number = Integer.parseInt(input.get(i).substring(numberStart, numberEnd));
                }

                if (partNumber.number != -1) {                                        
                    for (int row = i-1; row < i+2; row++) {
                        if (row >= 0 && row < input.size()) {
                            for (int col = numberStart-1; col < numberEnd+1; col++) {
                                if (col >= 0 && col < input.get(i).length()) {
                                    if (Character.toString(input.get(row).charAt(col)).matches(regex)) {
                                        partNumber.gear.col = col;
                                        partNumber.gear.row = row;
                                        sum += partNumber.number;
                                        partNumbers.add(partNumber);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return new EngineSchematic(partNumbers, sum);
    } 

}

class PartNumber {
    int number;
    Coords gear = new Coords(-1, -1);

    public PartNumber(int number) {
        this.number = number;
    }
}

class Coords {
    int col;
    int row;

    public Coords(int col, int row) {
        this.col = col;
        this.row = row;
    }
}

class EngineSchematic  {
    ArrayList<PartNumber> partNumbers;
    int sumPartNumbers;

    public EngineSchematic(ArrayList<PartNumber> partNumbers, int sumPartNumbers) {
        this.partNumbers = partNumbers;
        this.sumPartNumbers = sumPartNumbers;
    }
}
