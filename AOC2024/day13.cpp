#include <cmath>
#include <cmath>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <sstream>

using namespace std;

vector<string> split (const string &s, char delim) {
    vector<string> result;
    stringstream ss (s);
    string item;

    while (getline (ss, item, delim)) {
        result.push_back (item);
    }

    return result;
}

vector<string> readFile(string path) {
    ifstream file(path);
    vector<string> lines;
    string line;

    if (file.is_open()) {
        while (getline(file, line)) {
            lines.push_back(line);
        }
        file.close();
    } else {
        cout << "Unable to open file" << endl;
    }

    return lines;
}

vector<vector<vector<double>>> parseInput(vector<string> input) {
    vector<vector<vector<double>>> systems;
    for(int i = 0; i < input.size(); i += 4) {
        vector<double> equations;
        vector<string> buttonA = split(input[i], ' ');
        vector<string> buttonB = split(input[i + 1], ' ');
        vector<string> price = split(input[i + 2], ' ');

        buttonA[2].pop_back();
        double ax = stod(split(buttonA[2], '+')[1]);
        double ay = stod(split(buttonA[3], '+')[1]);

        buttonB[2].pop_back();
        double bx = stod(split(buttonB[2], '+')[1]);
        double by = stod(split(buttonB[3], '+')[1]);

        price[1].pop_back();
        double px = stod(split(price[1], '=')[1]);
        double py = stod(split(price[2], '=')[1]);

        vector<vector<double>> system = {{ax, bx, px}, {ay, by, py}};
        systems.push_back(system);
    }
    return systems;
}   

vector<double> solveSystemOfEquations(vector<vector<double>> system) {
    double D = (system[0][0] * system[1][1]) - (system[1][0] * system[0][1]);
    double Dx = (system[0][2] * system[1][1]) - (system[1][2] * system[0][1]);
    double Dy = (system[0][0] * system[1][2]) - (system[1][0] * system[0][2]);

    double x = Dx / D;
    double y = Dy / D;

    return {x, y};
}


int part1(vector<vector<vector<double>>> systems) {
    int totalTokens = 0;
    for (int i = 0; i < systems.size(); i++) {
        vector<double> result = solveSystemOfEquations(systems[i]);
        double a = result[0];
        double b = result[1];
        
        if ((a < 100 && b < 100) && ((int) a == a && (int) b == b)) {
            totalTokens += (a * 3) + (b * 1);
        }
    }
    
    cout << "Part 1 result: " << totalTokens << endl;

    return 0;
}

int part2(vector<vector<vector<double>>> systems) {
    unsigned long long totalTokens = 0;
    for (int i = 0; i < systems.size(); i++) {
        vector<vector<double>> system = systems[i];
        system[0][2] = system[0][2]+10000000000000;
        system[1][2] = system[1][2]+10000000000000;
        vector<double> result = solveSystemOfEquations(system);
        double a = result[0];
        double b = result[1];
        
        if (((unsigned long long) a == a && (unsigned long long) b == b)) {
            totalTokens += (a * 3) + (b * 1);
        }
    }
    
    cout << "Part 2 result: " << totalTokens << endl;

    return 0;
}

int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day13_input.txt");
    vector<vector<vector<double>>> systems = parseInput(input);
    part1(systems);
    part2(systems);

    return 0;
}