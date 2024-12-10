#include <cmath>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

struct coords {
    int x;
    int y;
    char frequency;

    // Overload operator<
    bool operator<(const coords& other) const {
        if (x != other.x)
            return x < other.x;
        else if (y != other.y)
            return y < other.y;
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

vector<vector<coords>> parseMap(vector<string> map) {
    vector<vector<coords>> antennas;
    for (int i = 0; i < map.size(); i++) {
        for (int j = 0; j < map.at(i).length(); j++) {
            char frequency = map.at(i)[j];
            if (frequency != '.') {
                bool foundInList = false;
                for (int k = 0; k < antennas.size(); k++) {
                    if (frequency == antennas.at(k).at(0).frequency) {
                        antennas.at(k).push_back({j, i, frequency});
                        foundInList = true;
                        break;
                    }
                }
                if (foundInList == false) {
                    antennas.push_back({{j, i, frequency}});
                }
            }
        }
    }
    return antennas;
}

int part1(vector<string> map) {
    vector<coords> antinodes;
    vector<vector<coords>> antennas = parseMap(map);
    for (int i = 0; i < antennas.size(); i++) {
        for (int j = 0; j < antennas.at(i).size(); j++) {
            coords antenna1 = antennas.at(i).at(j);
            for (int k = j + 1; k < antennas.at(i).size(); k++) {
                coords antenna2 = antennas.at(i).at(k);
                int diffx = antenna1.x - antenna2.x;
                int diffy = antenna1.y - antenna2.y;
                coords antinode1 = {antenna1.x + diffx, antenna1.y + diffy};
                coords antinode2 = {antenna2.x - diffx, antenna2.y - diffy};
                
                bool found1InAntinodes = false;
                bool found2InAntinodes = false;
                for (int l = 0; l < antinodes.size(); l++) {
                    if (antinodes.at(l).x == antinode1.x && antinodes.at(l).y == antinode1.y) {
                        found1InAntinodes = true;
                    }
                    if (antinodes.at(l).x == antinode2.x && antinodes.at(l).y == antinode2.y) {
                        found2InAntinodes = true;
                    }
                    if (found1InAntinodes && found2InAntinodes) break;
                }
                if (!found1InAntinodes && 
                    antinode1.x >= 0 && antinode1.x < map.at(0).length() &&
                    antinode1.y >= 0 && antinode1.y < map.at(0).length()) {
                        antinodes.push_back(antinode1);
                }
                if (!found2InAntinodes &&
                    antinode2.x >= 0 && antinode2.x < map.at(0).length() &&
                    antinode2.y >= 0 && antinode2.y < map.at(0).length()) { 
                        antinodes.push_back(antinode2);
                }
            }
        }
    }
    cout << "Part 1 result: " << antinodes.size() << endl;

    return 0;
}
int part2(vector<string> map) {
    vector<coords> antinodes;
    vector<vector<coords>> antennas = parseMap(map);
    for (int i = 0; i < antennas.size(); i++) {
        for (int j = 0; j < antennas.at(i).size(); j++) {
            coords antenna1 = antennas.at(i).at(j);
            for (int k = j + 1; k < antennas.at(i).size(); k++) {
                coords antenna2 = antennas.at(i).at(k);
                int diffx = antenna1.x - antenna2.x;
                int diffy = antenna1.y - antenna2.y;

                int index = 0;
                while (true) {
                    coords antinode1 = {antenna1.x + diffx * index, antenna1.y + diffy * index};
                    if (antinode1.x >= 0 && antinode1.x < map.at(0).length() && 
                        antinode1.y >= 0 && antinode1.y < map.at(0).length()) {
                            bool foundInList = false;
                            for (int l = 0; l < antinodes.size(); l++) {
                                if (antinode1.x == antinodes.at(l).x && antinode1.y == antinodes.at(l).y) {
                                    foundInList = true;
                                    break;
                                }
                            }
                            if (!foundInList) {
                                antinodes.push_back(antinode1);
                            }
                    }
                    else {
                        break;
                    }
                    index++;
                }
                index = 0;
                while (true) {
                    coords antinode2 = {antenna2.x - diffx * index, antenna2.y - diffy * index};
                    if (antinode2.x >= 0 && antinode2.x < map.at(0).length() && 
                        antinode2.y >= 0 && antinode2.y < map.at(0).length()) {
                            bool foundInList = false;
                            for (int l = 0; l < antinodes.size(); l++) {
                                if (antinode2.x == antinodes.at(l).x && antinode2.y == antinodes.at(l).y) {
                                    foundInList = true;
                                    break;
                                }
                            }
                            if (!foundInList) {
                                antinodes.push_back(antinode2);
                            }
                    }
                    else {
                        break;
                    }
                    index++;
                }
            }
        }
    }
    cout << "Part 2 result: " << antinodes.size() << endl;

    return 0;
}

int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day08_input.txt");

    part1(input);
    part2(input);

    return 0;
}