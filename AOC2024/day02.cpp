#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <sstream>

using namespace std;


std::vector<std::string> split (const std::string &s, char delim) {
    std::vector<std::string> result;
    std::stringstream ss (s);
    std::string item;

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


int part1(vector<string> input) {
    int numStable = 0;
    for (int i = 0; i < input.size(); i++) {
        string line = input.at(i);
        vector<string> substr = split(line, ' ');

        bool rising = false;
        bool falling = false;
        for (int j = 0; j < substr.size() - 1; j++) {
            int current = stoi(substr.at(j));
            int next = stoi(substr.at(j + 1));

            int diff = next - current;
            if (abs(diff) > 3 || abs(diff) == 0) {
                rising = false;
                falling = false;
                break;
            }
            else if (diff > 0) {
                rising = true;
            }
            else if (diff < 0) {
                falling = true;
            }
        }

        if (rising ^ falling) {
            numStable++; 
        }
    }

    cout << "Part 1 result: " << numStable << "\n";

    return 0;
}

int part2(vector<string> input) {
    int numStable = 0;
    for (int i = 0; i < input.size(); i++) {
        string line = input.at(i);
        vector<string> substr = split(line, ' ');

        for (int j = -1; j < static_cast<int>(substr.size()); j++) {
            //std::cout << "j: " << j << "\n";
            //std::cout << "substr.size(): " << substr.size() << "\n";
            vector<string> tempvec;
            if (j == -1) {
                tempvec = substr;
            }
            else {
                tempvec = substr;
                tempvec.erase(tempvec.begin() + j);
            }
            bool rising = false;
            bool falling = false;
            for (int k = 0; k < tempvec.size() - 1; k++) {
                int current = stoi(tempvec.at(k));
                int next = stoi(tempvec.at(k + 1));

                int diff = next - current;
                if (abs(diff) > 3 || abs(diff) == 0) {
                    rising = false;
                    falling = false;
                    break;
                }
                else if (diff > 0) {
                    rising = true;
                }
                else if (diff < 0) {
                    falling = true;
                }
            }

            if (rising ^ falling) {
                numStable++; 
                break;
            }
        }
    }

    cout << "Part 2 result: " << numStable << "\n";

    return 0;
}


int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day02_input.txt");
    
    part1(input);
    part2(input);

    return 0;
}