#include <cmath>
#include <cmath>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <sstream>

using namespace std;

struct coords {
    int x;
    int y;
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

vector<string> parseMap(vector<string> input) {
    vector<string> map;
    for (string line : input) {
        if (line == "") break;
        else {
            map.push_back(line);
        }
    }
    return map;
}

string parseMoves(vector<string> input) {
    string moves = "";
    int i = 0;
    while (true) {
        if (input[i] == "") break;
        i++;
    }
    i++;
    for (int j = i; j < input.size(); j++) {
        moves += input[j];
    }
    return moves;
}

coords getRobotStartCoords(vector<string> *map) {
    for (int i = 0; i < map->size(); i++) {
        for (int j = 0; j < map->at(i).size(); j++) {
            if (map->at(i)[j] == '@') {
                return {j, i};
            }
        }
    }
}

vector<string> moveRobot(vector<string> map, coords *robotPos, char dir) {
    bool unmovable;
    int stop;
    switch (dir) {
        case '^':
            unmovable = false;
            stop = robotPos->y;
            for (int i = robotPos->y; i >= 0; i--) {
                if (map[i][robotPos->x] == '#') {
                    unmovable = true;
                    break;
                }
                else if (map[i][robotPos->x] == '.') {
                    stop--;
                    break;
                }
                else if (map[i][robotPos->x] == 'O') {
                    stop--;
                }
            }
            if (!unmovable) {
                for (int i = stop; i < robotPos->y; i++) {
                    if (map[i][robotPos->x] == '#') continue;
                    map[i][robotPos->x] = map[i+1][robotPos->x];
                }
                map[robotPos->y][robotPos->x] = '.';
                robotPos->y--;
            }
            break;
        case '>':
            unmovable = false;
            stop = robotPos->x;
            for (int i = robotPos->x; i < map[0].length(); i++) {
                if (map[robotPos->y][i] == '#') {
                    unmovable = true;
                    break;
                }
                else if (map[robotPos->y][i] == '.') {
                    stop++;
                    break;
                }
                else if (map[robotPos->y][i] == 'O') {
                    stop++;
                }
            }
            if (!unmovable) {
                for (int i = stop; i > robotPos->x; i--) {
                    if (map[robotPos->y][i] == '#') continue;
                    map[robotPos->y][i] = map[robotPos->y][i-1];
                }
                map[robotPos->y][robotPos->x] = '.';
                robotPos->x++;
            }
            break;
        case 'v':
            unmovable = false;
            stop = robotPos->y;
            for (int i = robotPos->y; i < map.size(); i++) {
                if (map[i][robotPos->x] == '#') {
                    unmovable = true;
                    break;
                }
                else if (map[i][robotPos->x] == '.') {
                    stop++;
                    break;
                }
                else if (map[i][robotPos->x] == 'O') {
                    stop++;
                }
            }
            if (!unmovable) {
                for (int i = stop; i > robotPos->y; i--) {
                    if (map[i][robotPos->x] == '#') continue;
                    map[i][robotPos->x] = map[i-1][robotPos->x];
                }
                map[robotPos->y][robotPos->x] = '.';
                robotPos->y++;
            }
            break;
        case '<':
            unmovable = false;
            stop = robotPos->x;
            for (int i = robotPos->x; i >= 0; i--) {
                if (map[robotPos->y][i] == '#') {
                    unmovable = true;
                    break;
                }
                else if (map[robotPos->y][i] == '.') {
                    stop--;
                    break;
                }
                else if (map[robotPos->y][i] == 'O') {
                    stop--;
                }
            }
            if (!unmovable) {
                for (int i = stop; i < robotPos->x; i++) {
                    if (map[robotPos->y][i] == '#') continue;
                    map[robotPos->y][i] = map[robotPos->y][i+1];
                }
                map[robotPos->y][robotPos->x] = '.';
                robotPos->x--;
            }
            break;
    }
    return map;
}

int calculateSumGPS(vector<string> map) {
    int sum = 0;
    for (int i = 0; i < map.size(); i++) {
        for (int j = 0; j < map[i].size(); j++) {
            if (map[i][j] == 'O') {
                sum += 100*i + j;
            }
        }
    }
    return sum;
}

int part1(vector<string> map, string moves) {
    coords robotPos = getRobotStartCoords(&map);
    for (char dir : moves) {
        map = moveRobot(map, &robotPos, dir);
    }
    int sumGPS = calculateSumGPS(map);
    cout << "Part 1 result: " << sumGPS << endl;

    return 0;
}

int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day15_input.txt");
    vector<string> map = parseMap(input);
    string moves = parseMoves(input);
    
    part1(map, moves);

    return 0;
}