#include <algorithm>
#include <climits>
#include <cmath>
#include <cstdlib>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

struct coords {
    int x;
    int y;
    int dir;
    int score;

    // Overload operator<
    bool operator>(const coords& other) const {
        return score > other.score;
    }

    bool operator==(const coords& other) const {
        return x == other.x && y == other.y;
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

bool isBlocked(coords *next, vector<string> *map) {
    if (map->at(next->y).at(next->x) == '#') return true;
    else return false;
}
bool goingBackwards(coords current, coords next) {
    if (abs((current.dir - next.dir) % 4) == 2) return true;
    else return false;
}
bool isVisited(coords next, vector<coords> *visited) {
    for (int i = 0; i < visited->size(); i++) {
        if (next == visited->at(i)) {
            return true;
        }
    }
    return false;
}

coords calculateScore(coords currentCoord, coords next) {
    int turnCost = (currentCoord.dir != next.dir) ? 1000 : 0;
    next.score = currentCoord.score + 1 + turnCost;
    return next;
}

vector<coords> getCurrentOptions(coords currentCoord, vector<coords> visited, vector<string> *map) {
    vector<coords> currentOptions;
    coords north = {currentCoord.x, currentCoord.y - 1, 0};
    coords east = {currentCoord.x + 1, currentCoord.y, 1};
    coords south = {currentCoord.x, currentCoord.y + 1, 2};
    coords west = {currentCoord.x - 1, currentCoord.y, 3};

    if (!isBlocked(&north, map) && !goingBackwards(currentCoord, north) && !isVisited(north, &visited)) {
        currentOptions.push_back(calculateScore(currentCoord, north));
    }
    if (!isBlocked(&east, map) && !goingBackwards(currentCoord, east) && !isVisited(east, &visited)) {
        currentOptions.push_back(calculateScore(currentCoord, east));
    }
    if (!isBlocked(&south, map) && !goingBackwards(currentCoord, south) && !isVisited(south, &visited)) {
        currentOptions.push_back(calculateScore(currentCoord, south));
    }
    if (!isBlocked(&west, map) && !goingBackwards(currentCoord, west) && !isVisited(west, &visited)) {
        currentOptions.push_back(calculateScore(currentCoord, west));
    }

    return currentOptions;
}

int findAllPaths(vector<string> map, coords start, coords end) {
    int lowestScore = INT_MAX;
    vector<coords> unexplored;
    vector<coords> visited;

    unexplored.push_back(start);
    
    while (!unexplored.empty()) {
        sort(unexplored.begin(), unexplored.end(), greater<>());
        coords currentCoord = unexplored.back();
        unexplored.pop_back();
        visited.push_back(currentCoord);
        map[currentCoord.y][currentCoord.x] = 'x';

        vector<coords> currentOptions = getCurrentOptions(currentCoord, visited, &map);

        if (currentCoord.x == end.x && currentCoord.y == end.y) {
            if (currentCoord.score <= lowestScore) {
                lowestScore = currentCoord.score;
            }
        } else if (!currentOptions.empty()) {
            unexplored.insert(unexplored.end(), currentOptions.begin(), currentOptions.end());
        }
        map[currentCoord.y][currentCoord.x] = '.';
    }

    return lowestScore;
}
coords findPos(vector<string> *map, char c) {
    for (int i = 0; i < map->size(); i++) {
        for (int j = 0; j < map->at(i).size(); j++) {
            if (map->at(i).at(j) == c) return {j, i};
        }
    }
}

int part1(vector<string> map) {
    coords start = findPos(&map, 'S');
    coords end = findPos(&map, 'E');
    start.dir = 1;
    start.score = 0;

    int lowestScore = findAllPaths(map, start, end);

    cout << "Part 1 result: " << lowestScore << endl;

    return 0;
}


int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day16_example.txt");
    part1(input);

    return 0;
}
