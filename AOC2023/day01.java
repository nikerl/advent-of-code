import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class day01 {
    public static void main(String[] args) {
        ArrayList<String> input = Functions.readInputFile("AOC2023/input/day01_input.txt");
        
        int sumOfCalibrations1 = part1(input);
        System.out.println("Sum of all calibration values: " + sumOfCalibrations1);

        int sumOfCalibrations2 = part2(input);
        System.out.println("Sum of all calibration values: " + sumOfCalibrations2);
        
    }


    static int part1(ArrayList<String> input) {
        int sum = 0;
        for (int i = 0; i < input.size(); i++) {
            int calibrationValue = 0;
            for (int j = 0; j < input.get(i).length(); j++) {
                char ch = input.get(i).charAt(j);
                if (Character.isDigit(ch)) {
                    int digit = Character.getNumericValue(ch);
                    calibrationValue = digit*10;
                    break;
                }
            }
            for (int j = input.get(i).length()-1; j >= 0 ; j--) {
                char ch = input.get(i).charAt(j);
                if (Character.isDigit(ch)) {
                    int digit = Character.getNumericValue(ch);
                    calibrationValue += digit;
                    break;
                }
            }
            sum += calibrationValue;
        }

        return sum;
    }



    public static int part2(ArrayList<String> input) {
        int sum = 0;

        for (int i = 0; i < input.size(); i++) {
            Map<String, Integer> wordToNumber = createWordToNumberMap();
    
            StringBuilder digits = new StringBuilder();
    
            // regex to find all digits and words
            Pattern pattern = Pattern.compile("(?:\\d+|oneight|twone|eightwo|one|two|three|four|five|six|seven|eight|nine)");
            Matcher matcher = pattern.matcher(input.get(i));

            // appending Digit or String's corresponding Digit
            while (matcher.find()) {
                String match = matcher.group().toLowerCase();
                if (Character.isDigit(match.charAt(0))) {
                    digits.append(match);
                } else {
                    digits.append(wordToNumber.get(match));
                }
            }
            int calibrationValue = 0;
            calibrationValue = Character.getNumericValue(digits.charAt(0)) * 10;
            calibrationValue += Character.getNumericValue(digits.charAt(digits.length() - 1));

            sum += calibrationValue;
        }

        return sum;
    }


  private static Map<String, Integer> createWordToNumberMap(){
      Map<String, Integer> mp = new HashMap<>();
      mp.put("one", 1);
      mp.put("two", 2);
      mp.put("three", 3);
      mp.put("four", 4);
      mp.put("five", 5);
      mp.put("six", 6);
      mp.put("seven", 7);
      mp.put("eight", 8);
      mp.put("nine", 9);
      mp.put("oneight", 18);
      mp.put("twone", 21);
      mp.put("eightwo", 82);
      return mp;
  }
}