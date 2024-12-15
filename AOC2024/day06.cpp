#include <iostream>
#include <fstream>
#include <ostream>
#include <unordered_set>
#include <string>
#include <vector>

using namespace std;

struct coords {
    int x;
    int y;
    char dir;

    bool operator==(const coords& other) const {
        return x == other.x && y == other.y && dir == other.dir;
    }
};

// Hash function for coords
namespace std {
    template <>
    struct hash<coords> {
        size_t operator()(const coords& state) const {
            return ((hash<int>()(state.x) ^ (hash<int>()(state.y) << 1)) >> 1) ^ (hash<char>()(state.dir) << 1);
        }
    };
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

coords getGuardStartCoords(vector<string> *map) {
    for (int i = 0; i < map->size(); i++) {
        for (int j = 0; j < map->at(i).length(); j++) {
            char pos = map->at(i)[j];
            if (pos == '^' || pos == 'v' || pos == '<' || pos == '>') {
                return {j, i, pos};
            }
        }
    }
    return {-1, -1};
}

bool guardGoingOffMap(const vector<string>& map, coords guardCoords) {
    int x = guardCoords.x;
    int y = guardCoords.y;
    char dir = guardCoords.dir;

    int dx = 0, dy = 0;
    switch (dir) {
        case '^': dy = -1; break;
        case '>': dx = 1;  break;
        case 'v': dy = 1;  break;
        case '<': dx = -1; break;
    }

    int nx = x + dx;
    int ny = y + dy;

    return (ny < 0 || ny >= map.size() || nx < 0 || nx >= map[0].size());
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

coords moveGuard2(const vector<string>& map, coords guardCoords) {
    int x = guardCoords.x;
    int y = guardCoords.y;
    char dir = guardCoords.dir;

    int dx = 0, dy = 0;
    char newDir = dir;

    // Determine movement based on direction
    switch (dir) {
        case '^': dy = -1; break;
        case '>': dx = 1;  break;
        case 'v': dy = 1;  break;
        case '<': dx = -1; break;
    }

    // Check the position directly in front
    int nx = x + dx;
    int ny = y + dy;

    if (ny < 0 || ny >= map.size() || nx < 0 || nx >= map[0].size() || map[ny][nx] == '#') {
        // Obstacle found, turn right
        switch (dir) {
            case '^': newDir = '>'; break;
            case '>': newDir = 'v'; break;
            case 'v': newDir = '<'; break;
            case '<': newDir = '^'; break;
        }
    } else {
        // Move forward
        x = nx;
        y = ny;
    }

    return {x, y, newDir};
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

bool detectLoop(const vector<string>& map, coords guardCoords) {
    unordered_set<coords> visitedStates;

    while (true) {
        if (visitedStates.count(guardCoords)) {
            // Loop detected
            return true;
        }
        visitedStates.insert(guardCoords);

        if (guardGoingOffMap(map, guardCoords)) {
            // Guard leaves the map
            return false;
        }

        guardCoords = moveGuard2(map, guardCoords);
    }
    return false;
}

vector<coords> part1(vector<string> map) {
    coords guardCoords = getGuardStartCoords(&map);
    while (!guardGoingOffMap(map, guardCoords)) {
        moveGuard(&map, &guardCoords);
    }

    vector<coords> visitedCoords = getListOfVisitedCoords(&map);
    int sum = visitedCoords.size();
    cout << "Part 1 result: " << sum << endl;

    return visitedCoords;
}


void part2(vector<string> map) {
    int loopCount = 0;
    coords guardStart = getGuardStartCoords(&map);

    for (int y = 0; y < map.size(); y++) {
        for (int x = 0; x < map[y].length(); x++) {
            if (map[y][x] == '.' && !(x == guardStart.x && y == guardStart.y)) {
                // Place obstruction
                map[y][x] = '#';

                if (detectLoop(map, guardStart)) {
                    loopCount++;
                }

                // Remove obstruction
                map[y][x] = '.';
            }
        }
    }

    cout << "Part 2 result: " << loopCount << endl;
}

int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day06_input.txt");
    
    vector<coords> visitedCoords = part1(input);
    part2(input);
}
