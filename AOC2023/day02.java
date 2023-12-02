import java.util.ArrayList;

public class day02 {
    public static void main(String args[]) {
        ArrayList<String> input = Functions.readInputFile("AOC2023/input/day02_input.txt");
        RGB colorLimits = new RGB(12, 13, 14);

        int sum1 = part1(input, colorLimits);
        System.out.println("Part 1: " + sum1);

        int sum2 = part2(input, colorLimits);
        System.out.println("Part 2: " + sum2);
    }


    static ArrayList<RGB> gameCubeColors(String game) {
        ArrayList<RGB> gameRoundColors = new ArrayList<RGB>();

        String[] rounds = game.split(";");
        for (String round : rounds) {
            RGB colors = new RGB(0, 0, 0);
            String[] cubes = round.split(",");
            for (String cube : cubes) {
                String[] cubeColor = cube.trim().split(" ");
                
                switch (cubeColor[1]) {
                    case "red":
                        colors.red = Integer.parseInt(cubeColor[0]);
                        break;
                    case "green":
                        colors.green = Integer.parseInt(cubeColor[0]);
                        break;
                    case "blue":
                        colors.blue = Integer.parseInt(cubeColor[0]);
                        break;
                    default:
                        break;
                }
            }
            gameRoundColors.add(colors);
        }
        return gameRoundColors;
    }


    static int part1(ArrayList<String> input, RGB colorLimits) {
        int sum = 0;
        for (String line: input) {
            String[] game = line.split(":");
            int id = Integer.parseInt(game[0].split(" ")[1]);
            ArrayList<RGB> gameRoundColors = gameCubeColors(game[1]);
            Boolean valid = true;
            for (RGB colors : gameRoundColors) {
                if (colors.red > colorLimits.red || colors.green > colorLimits.green || colors.blue > colorLimits.blue) {
                    valid = false;
                }
            }
            if (valid) {
                sum += id;
            }
        }
        return sum;
    }

    static int part2(ArrayList<String> input, RGB colorLimits) {
        int powerSum = 0;
        for (String line: input) {
            String[] game = line.split(":");
            ArrayList<RGB> gameRoundColors = gameCubeColors(game[1]);

            RGB minNeededColors = new RGB(0, 0, 0);
            
            for (RGB colors : gameRoundColors) {
                if (colors.red > minNeededColors.red) {
                    minNeededColors.red = colors.red;
                }
                if (colors.green > minNeededColors.green) {
                    minNeededColors.green = colors.green;
                }
                if (colors.blue > minNeededColors.blue) {
                    minNeededColors.blue = colors.blue;
                }
            }
            int power = minNeededColors.red * minNeededColors.green * minNeededColors.blue;
            powerSum += power;
        }
        return powerSum;
    }
}




class RGB {
    int red;
    int green;
    int blue;

    public RGB(int red, int green, int blue) {
        this.red= red;
        this.green = green;
        this.blue = blue;
    }
}
