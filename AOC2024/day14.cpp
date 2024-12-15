#include <cmath>
#include <cmath>
#include <filesystem>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <sstream>

using namespace std;

#define MAP_X_SIZE 101
#define MAP_Y_SIZE 103

struct RobotVector {
    int x;
    int y;
    int Vx;
    int Vy;
};

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

vector<RobotVector> parseInput(vector<string> input) {
    vector<RobotVector> robots;
    for (string line : input) {
        vector<string> substr = split(line, ' ');
        vector<string> pos = split(split(substr[0], '=')[1], ',');
        vector<string> vel = split(split(substr[1], '=')[1], ',');
        robots.push_back({stoi(pos[0]), stoi(pos[1]), stoi(vel[0]), stoi(vel[1])});
    }
    return robots;
}

inline int positiveModulo(int value, int modulus) {
    return (value % modulus + modulus) % modulus;
}

void moveRobot(RobotVector *robot) {
    robot->x = positiveModulo(robot->x + robot->Vx, MAP_X_SIZE);
    robot->y = positiveModulo(robot->y + robot->Vy, MAP_Y_SIZE);
}

int calculateSafetyFactor(vector<RobotVector> *robots) {
    int xpivot = MAP_X_SIZE / 2;
    int ypivot = MAP_Y_SIZE / 2;

    vector<int> quadrants = {0, 0, 0, 0};
    for (int i = 0; i < robots->size(); i++) {
        RobotVector robot = robots->at(i);
        
        // Quadrant 1
        if ((robot.x >= 0 && robot.x < xpivot) && (robot.y >= 0 && robot.y < ypivot)) {
            quadrants[0]++;
        } 
        // Quadrant 2
        else if ((robot.x > xpivot && robot.x < MAP_X_SIZE) && (robot.y >= 0 && robot.y < ypivot)) {
            quadrants[1]++;
        }
        // Quadrant 3
        if ((robot.x >= 0 && robot.x < xpivot) && (robot.y > ypivot && robot.y < MAP_Y_SIZE)) {
            quadrants[2]++;
        } 
        // Quadrant 4
        else if ((robot.x > xpivot && robot.x < MAP_X_SIZE) && (robot.y > ypivot && robot.y < MAP_Y_SIZE)) {
            quadrants[3]++;
        }
    }

    int safetyFactor = 1;
    for (int i = 0; i < quadrants.size(); i++) {
        if (quadrants[i] != 0) {
            safetyFactor *= quadrants[i];
        }
    }

    return safetyFactor;
}

vector<string> createMap() {
    vector<string> map;
    for (int i = 0; i < MAP_Y_SIZE; i++) {
        string row = "";
        for (int j = 0; j < MAP_X_SIZE; j++) {
            row.push_back('.');
        }
        map.push_back(row);
    }
    return map;
}

bool checkForTree(vector<string> map) {
    bool treeFound = false;
    for (int i = 0; i < map.at(0).size(); i++) {
        for (int j = 0; j < map.size() - 10; j++) {
            for (int k = j; k < j + 10; k++) {
                if (map[k][i] != '#') {
                    treeFound = false;
                    break;
                }
                else {
                    treeFound = true;
                }
            }
            if (treeFound) return true;
        }
    }
    return false;
}

bool isTree(vector<string> map, vector<RobotVector> robots, int seconds) {
    for (RobotVector robot : robots) {
        map.at(robot.y).at(robot.x) = '#';
    }
    if (checkForTree(map)) {
        cout << "---------------------" << seconds << "---------------------" << endl;
        for (string row : map) {
            cout << row << endl;
        }
        return true;
    }
    return false;
}


int part1(vector<RobotVector> robots, int seconds) {
    for (int i = 0; i < seconds; i++) {
        for (int j = 0; j < robots.size(); j++) {
            moveRobot(&robots[j]);
        }
    }
    int safetyFactor = calculateSafetyFactor(&robots);
    cout << "Part 1 result: " << safetyFactor << endl;

    return 0;
}

int part2(vector<RobotVector> robots) {
    vector<string> map = createMap();
    int seconds = 1;
    while(true) {
        for (int j = 0; j < robots.size(); j++) {
            moveRobot(&robots[j]);
        }

        if (isTree(map, robots, seconds)) break;
        seconds++;
    }

    return 0;
}

int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day14_input.txt");
    vector<RobotVector> robots = parseInput(input);
    
    part1(robots, 100);
    part2(robots);

    return 0;
}