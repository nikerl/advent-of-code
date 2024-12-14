#include <algorithm>
#include <cmath>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

struct coords {
    int x;
    int y;
    char direction;

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

vector<coords> getPlotFence(coords currentCoord, vector<coords> *visited, vector<string> *map) {
    char currentPlant = map->at(currentCoord.y).at(currentCoord.x);
    vector<coords> fences;

    coords north = {currentCoord.x, currentCoord.y - 1};
    coords east = {currentCoord.x + 1, currentCoord.y};
    coords south = {currentCoord.x, currentCoord.y + 1};
    coords west = {currentCoord.x - 1, currentCoord.y};

    //int fenceLength = 0;

    if (!coordsInMap(&north, map) || currentPlant != map->at(north.y).at(north.x)) {
        currentCoord.direction = 'N';
        fences.push_back(currentCoord);
    }
    if (!coordsInMap(&east, map) || currentPlant != map->at(east.y).at(east.x)) {
        currentCoord.direction = 'E';
        fences.push_back(currentCoord);
    }
    if (!coordsInMap(&south, map) || currentPlant != map->at(south.y).at(south.x)) {
        currentCoord.direction = 'S';
        fences.push_back(currentCoord);
    }
    if (!coordsInMap(&west, map) || currentPlant != map->at(west.y).at(west.x)) {
        currentCoord.direction = 'W';
        fences.push_back(currentCoord);
    }

    return fences;
}


int calculateFenceSides(vector<coords> perimeter, vector<string> *map) {
    int numFences = 0;
    while (!perimeter.empty()) {
        coords plot1 = perimeter.back();
        perimeter.pop_back();
        vector<coords> temp = {plot1};
        for (int j = 0; j < perimeter.size(); j++) {
            coords plot2 = perimeter[j];
            if (plot1.direction == plot2.direction) {
                if ((plot1.direction == 'N' || plot1.direction == 'S') && plot1.y == plot2.y) {
                    perimeter.erase(perimeter.begin() + j);
                    temp.push_back(plot2);
                    j--;
                }
                else if ((plot1.direction == 'E' || plot1.direction == 'W') && plot1.x == plot2.x) {
                    perimeter.erase(perimeter.begin() + j);
                    temp.push_back(plot2);
                    j--;
                }
            }
        }

        int numSpaces = 1;
        if (temp.size() > 1) {
            // Sort the coordinates
            if (plot1.direction == 'N' || plot1.direction == 'S') {
                sort(temp.begin(), temp.end(), [](coords a, coords b) { return a.x < b.x; });
                for (size_t j = 1; j < temp.size(); j++) {
                    if (temp[j].x != temp[j-1].x + 1) {
                        numSpaces++;
                    }
                }
            } else {
                sort(temp.begin(), temp.end(), [](coords a, coords b) { return a.y < b.y; });
                for (size_t j = 1; j < temp.size(); j++) {
                    if (temp[j].y != temp[j-1].y + 1) {
                        numSpaces++;
                    }
                }
            }
        }
        numFences += numSpaces;
    }
    return numFences;
}


int calculatePrice(vector<string> map) {
    int pricePart1 = 0;
    int pricePart2 = 0;

    vector<coords> unexplored = {{0, 0}};
    vector<coords> visited;
    vector<coords> unexploredCurrentPlant;

    while (!unexplored.empty()) {
        unexploredCurrentPlant.push_back(unexplored.back());
        unexplored.pop_back();
        
        int area = 0;
        vector<coords> perimeter;

        while (!unexploredCurrentPlant.empty()) {
            coords currentPlot = unexploredCurrentPlant.back();
            unexploredCurrentPlant.pop_back();
            if (isInList(currentPlot, &visited)) {
                continue;
            }
            visited.push_back(currentPlot);

            area++;
            vector<coords> currentPlotFences = getPlotFence(currentPlot, &visited, &map);
            perimeter.insert(perimeter.end(), currentPlotFences.begin(), currentPlotFences.end());

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
        pricePart1 += area * perimeter.size();
        pricePart2 += area * calculateFenceSides(perimeter, &map);
    }

    cout << "Part 1 result: " << pricePart1 << endl;
    cout << "Part 2 result: " << pricePart2 << endl;
    return 0;
}

int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day12_input.txt");
    calculatePrice(input);

    return 0;
}