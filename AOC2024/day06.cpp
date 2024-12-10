#include <iostream>
#include <fstream>
#include <ostream>
#include <set>
#include <string>
#include <vector>

using namespace std;

struct coords {
    int x;
    int y;
    char dir;

    // Overload operator<
    bool operator<(const coords& other) const {
        if (x != other.x)
            return x < other.x;
        else if (y != other.y)
            return y < other.y;
        else
            return dir < other.dir;
    }
};

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

coords getGuardStartCoords(vector<string> *map) {
    for (int i = 0; i < map->size(); i++) {
        for (int j = 0; j < map->at(i).length(); j++) {
            char pos = map->at(i)[j];
            if (pos == '^' || pos == 'v' || pos == '<' || pos == '>') {
                return {j, i};
            }
        }
    }
    return {-1, -1};
}

bool guardGoingOffMap(vector<string> *map, coords *guardCoords) {
    char guardDir = map->at(guardCoords->y)[guardCoords->x];

    switch (guardDir) {
        case '^':
            if (guardCoords->y == 0) {
                map->at(guardCoords->y)[guardCoords->x] = 'X';
                return true;
            }
            break;
        case '>':
            if (guardCoords->x == map->at(0).length() - 1) {
                map->at(guardCoords->y)[guardCoords->x] = 'X';
                return true;
            }
            break;
        case 'v':
            if (guardCoords->y == map->size() - 1) {
                map->at(guardCoords->y)[guardCoords->x] = 'X';
                return true;
            }
            break;
        case '<':
            if (guardCoords->x == 0) {
                map->at(guardCoords->y)[guardCoords->x] = 'X';
                return true;
            }
            break;
    }

    return false;
}

int moveGuard(vector<string> *map, coords *guardCoords) {
    char guardDir = map->at(guardCoords->y)[guardCoords->x];
    map->at(guardCoords->y)[guardCoords->x] = 'X';

    switch (guardDir) {
        case '^':
            if (map->at(guardCoords->y - 1)[guardCoords->x] == '#') {
                map->at(guardCoords->y)[guardCoords->x + 1] = '>';
                guardCoords->x = guardCoords->x+1;
            } else {
                map->at(guardCoords->y - 1)[guardCoords->x] = '^';
                guardCoords->y = guardCoords->y-1;
            }
            break;
        case '>':
            if (map->at(guardCoords->y)[guardCoords->x + 1] == '#') {
                map->at(guardCoords->y + 1)[guardCoords->x] = 'v'; 
                guardCoords->y = guardCoords->y+1;
            } else {
                map->at(guardCoords->y)[guardCoords->x + 1] = '>';
                guardCoords->x = guardCoords->x+1;
            }
            break;
        case 'v':
            if (map->at(guardCoords->y + 1)[guardCoords->x] == '#') {
                map->at(guardCoords->y)[guardCoords->x - 1] = '<'; 
                guardCoords->x = guardCoords->x-1;
            } else {
                map->at(guardCoords->y + 1)[guardCoords->x] = 'v';
                guardCoords->y = guardCoords->y+1;
            }
            break;
        case '<':
            if (map->at(guardCoords->y)[guardCoords->x - 1] == '#') {
                map->at(guardCoords->y - 1)[guardCoords->x] = '^';
                guardCoords->y = guardCoords->y-1;
            } else {
                map->at(guardCoords->y)[guardCoords->x - 1] = '<';
                guardCoords->x = guardCoords->x-1;
            }
            break;
    }
    return 0;
}

vector<coords> getListOfVisitedCoords(vector<string> *map) {
    vector<coords> visitedCoords;
    for (int i = 0; i < map->size(); i++) {
        for (int j = 0; j < map->at(i).length(); j++) {
            char guardPos = map->at(i)[j];
            if (guardPos == 'X') {
                visitedCoords.push_back({j, i});
            }
        }
    }
    return visitedCoords;
}



vector<coords> part1(vector<string> map) {
    coords guardCoords = getGuardStartCoords(&map);
    while (!guardGoingOffMap(&map, &guardCoords)) {
        moveGuard(&map, &guardCoords);
    }

    vector<coords> visitedCoords = getListOfVisitedCoords(&map);
    int sum = visitedCoords.size();
    cout << "Part 1 result: " << sum << endl;

    return visitedCoords;
}


int part2(vector<string> map, vector<coords> oldvisitedCoords) {
    vector<coords> visitedCoords;
    for (int i = 0; i < map.size(); i++) {
        for (int j = 0; j < map.at(i).length(); j++) {
            visitedCoords.push_back({j, i});
        }
    }
    vector<coords> causesGuardLoop;
    vector<string> tempMap;
    for (int i = 0; i < visitedCoords.size(); i++) {
        tempMap = map;
        coords guardCoords = getGuardStartCoords(&tempMap);
        coords obstacleCoords = visitedCoords.at(i);
        if (guardCoords.x == obstacleCoords.x && guardCoords.y == obstacleCoords.y) {
            continue;
        }
        tempMap.at(visitedCoords[i].y)[visitedCoords[i].x] = '#';
        set<coords> visitedStates;
        bool isLooping = false;

        while (!guardGoingOffMap(&tempMap, &guardCoords)) {
            char guardDir = tempMap.at(guardCoords.y)[guardCoords.x];
            coords guardState = {guardCoords.x, guardCoords.y, guardDir};

            if (visitedStates.count(guardState)) {
                isLooping = true;
                break;
            }
            visitedStates.insert(guardState);
            moveGuard(&tempMap, &guardCoords);
        }
        if (isLooping) {
            causesGuardLoop.push_back(obstacleCoords);
        }
    }

    int sum = causesGuardLoop.size();
    cout << "Part 2 result: " << sum << endl;

    return 0;
}

int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day06_input.txt");
    
    vector<coords> visitedCoords = part1(input);
    part2(input, visitedCoords);
}
