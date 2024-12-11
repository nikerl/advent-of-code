#include <cmath>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

struct coords {
    int x;
    int y;

    // Overload operator<
    bool operator<(const coords& other) const {
        if (x != other.x)
            return x < other.x;
        else if (y != other.y)
            return y < other.y;
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

vector<vector<int>> parseMap(vector<string> *map) {
    vector<vector<int>> newMap;
    for (int i = 0; i < map->size(); i++) {
        vector<int> row;
        for (int j = 0; j < map->at(i).length(); j++) {
            row.push_back(map->at(i).at(j) - '0');
        }
        newMap.push_back(row);
    }
    return newMap;
}

vector<coords> findTrailHeads(vector<vector<int>> *topologyMap) {
    vector<coords> trailHeads;
    for (int i = 0; i < topologyMap->size(); i++) {
        for (int j = 0; j < topologyMap->at(i).size(); j++) {
            if (topologyMap->at(i).at(j) == 0) {
                trailHeads.push_back({j, i});
            }
        }
    }
    return trailHeads;
}

bool coordsInMap(coords *coords, vector<vector<int>> *map) {
    if (coords->y >= 0 && coords->y < map->size() && coords->x >= 0 && coords->x < map->at(0).size()) {
        return true;
    }
    else return false;
}
bool isPathHikable(coords *current, coords *next, vector<vector<int>> *map) {
    int diff = map->at(next->y).at(next->x) - map->at(current->y).at(current->x);
    if (diff == 1) {
        return true;
    }
    return false;
}
bool isVisited(coords next, vector<coords> *visited) {
    for (int i = 0; i < visited->size(); i++) {
        if (next == visited->at(i)) {
            return true;
        }
    }
    return false;
}

vector<coords> getCurrentOptions(coords currentCoord, vector<coords> *visited, vector<vector<int>> *map) {
    vector<coords> currentOptions;
    coords north = {currentCoord.x, currentCoord.y - 1};
    coords east = {currentCoord.x + 1, currentCoord.y};
    coords south = {currentCoord.x, currentCoord.y + 1};
    coords west = {currentCoord.x - 1, currentCoord.y};

    if (!isVisited(north, visited) && 
        coordsInMap(&north, map) && 
        isPathHikable(&currentCoord, &north, map)) {
            currentOptions.push_back(north);
    }
    if (!isVisited(east, visited) && 
        coordsInMap(&east, map) && 
        isPathHikable(&currentCoord, &east, map)) {
            currentOptions.push_back(east);
    }
    if (!isVisited(south, visited) && 
        coordsInMap(&south, map) && 
        isPathHikable(&currentCoord, &south, map)) {
            currentOptions.push_back(south);
    }
    if (!isVisited(west, visited) && 
        coordsInMap(&west, map) && 
        isPathHikable(&currentCoord, &west, map)) {
            currentOptions.push_back(west);
    }

    return currentOptions;
}

int findNumPeaks(vector<vector<int>> topologyMap, coords trailHead) {
    int numPeaks = 0;
    vector<coords> visited;
    vector<coords> unexplored;
    vector<coords> peaks;
    vector<coords> currentOptions;
    coords currentCoord = trailHead;

    unexplored.push_back(currentCoord);

    while (!unexplored.empty()) {
        currentCoord = unexplored.back();
        unexplored.pop_back();

        int currentCoordHeight = topologyMap[currentCoord.y][currentCoord.x];
        currentOptions = getCurrentOptions(currentCoord, &visited, &topologyMap);
        visited.push_back(currentCoord);

        if (currentCoordHeight == 9) {
            peaks.push_back(currentCoord);
            numPeaks++;
        } else if (!currentOptions.empty()) {
            unexplored.insert(unexplored.end(), currentOptions.begin(), currentOptions.end());
            currentOptions.clear();
        }
    }

    return numPeaks;
}

int part1(vector<string> input) {
    int sum = 0;
    vector<vector<int>> topologyMap = parseMap(&input);
    vector<coords> trailHeads = findTrailHeads(&topologyMap);
    for (int i = 0; i < trailHeads.size(); i++) {
        sum += findNumPeaks(topologyMap, trailHeads.at(i));
    }

    cout << "Part 1 result: " << sum << endl;

    return 0;
}

int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day10_input.txt");
    part1(input);

    return 0;
}
