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

bool coordsInMap(coords *coords, vector<string> *map) {
    if (coords->y >= 0 && coords->y < map->size() && coords->x >= 0 && coords->x < map->at(0).size()) {
        return true;
    }
    else return false;
}
bool isInList(coords next, vector<coords> *visited) {
    for (int i = 0; i < visited->size(); i++) {
        if (next == visited->at(i)) {
            return true;
        }
    }
    return false;
}

vector<coords> getAdjacentPlots(coords currentCoord, vector<coords> *visited, vector<coords> *unexploredCurrentPlant, vector<string> *map) {
    vector<coords> currentOptions;
    coords north = {currentCoord.x, currentCoord.y - 1};
    coords east = {currentCoord.x + 1, currentCoord.y};
    coords south = {currentCoord.x, currentCoord.y + 1};
    coords west = {currentCoord.x - 1, currentCoord.y};

    if (!isInList(north, visited) && !isInList(north, unexploredCurrentPlant) && coordsInMap(&north, map)) {
            currentOptions.push_back(north);
    }
    if (!isInList(east, visited) && !isInList(east, unexploredCurrentPlant) && coordsInMap(&east, map)) {
            currentOptions.push_back(east);
    }
    if (!isInList(south, visited) && !isInList(south, unexploredCurrentPlant) && coordsInMap(&south, map)) {
            currentOptions.push_back(south);
    }
    if (!isInList(west, visited) && !isInList(west, unexploredCurrentPlant) && coordsInMap(&west, map)) {
            currentOptions.push_back(west);
    }

    return currentOptions;
}

int getPlotFence(coords currentCoord, vector<coords> *visited, vector<string> *map) {
    char currentPlant = map->at(currentCoord.y).at(currentCoord.x);

    coords north = {currentCoord.x, currentCoord.y - 1};
    coords east = {currentCoord.x + 1, currentCoord.y};
    coords south = {currentCoord.x, currentCoord.y + 1};
    coords west = {currentCoord.x - 1, currentCoord.y};

    int fenceLength = 0;

    if (!coordsInMap(&north, map) || currentPlant != map->at(north.y).at(north.x)) {
            fenceLength++;
    }
    if (!coordsInMap(&east, map) || currentPlant != map->at(east.y).at(east.x)) {
            fenceLength++;
    }
    if (!coordsInMap(&south, map) || currentPlant != map->at(south.y).at(south.x)) {
            fenceLength++;
    }
    if (!coordsInMap(&west, map) || currentPlant != map->at(west.y).at(west.x)) {
            fenceLength++;
    }

    return fenceLength;
}

int part1(vector<string> map) {
    int price = 0;

    vector<coords> unexplored = {{0, 0}};
    vector<coords> visited;
    vector<coords> unexploredCurrentPlant;

    while (!unexplored.empty()) {
        unexploredCurrentPlant.push_back(unexplored.back());
        unexplored.pop_back();
        
        int area = 0;
        int perimeter = 0;

        while (!unexploredCurrentPlant.empty()) {
            coords currentPlot = unexploredCurrentPlant.back();
            unexploredCurrentPlant.pop_back();
            if (isInList(currentPlot, &visited)) {
                continue;
            }
            visited.push_back(currentPlot);

            area++;
            perimeter += getPlotFence(currentPlot, &visited, &map);

            char plantType = map[currentPlot.y][currentPlot.x];
            vector<coords> adjacentPlots = getAdjacentPlots(currentPlot, &visited, &unexploredCurrentPlant, &map);
            for (coords plot : adjacentPlots) {
                if (map[plot.y][plot.x] == plantType) {
                    unexploredCurrentPlant.push_back(plot);
                } else {
                    unexplored.push_back(plot);
                }
            }   
        }
        price += area * perimeter;
    }

    cout << "Part 1 result: " << price << endl;
    return 0;
}

int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day12_input.txt");
    part1(input);

    return 0;
}