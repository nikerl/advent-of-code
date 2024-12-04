#include <iostream>
#include <fstream>
#include <ostream>
#include <string>
#include <vector>

using namespace std;

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

int searchHorizontally(vector<string> input) {
    int sum = 0;
    for (int i = 0; i < input.size(); i++) {
        string line = input.at(i);
        for (int j = 0; j <= line.length() - 4; j++) {
            string substr = line.substr(j, 4);
            if (substr == "XMAS" || substr == "SAMX") {
                sum++;
            }
        }
    }
    //cout << "horizontally: " << sum << endl;
    return sum;
}

int searchVertically(vector<string> input) {
    int sum = 0;
    for (int i = 0; i < input.at(0).length(); i++) {
        for (int j = 0; j <= input.size() - 4; j++) {
            string substr = "";
            substr = substr + input.at(j)[i] + input.at(j+1)[i] + input.at(j+2)[i] + input.at(j+3)[i];
            if (substr == "XMAS" || substr == "SAMX") {
                sum++;
            }
        }
    }
    //cout << "vertically: " << sum << endl;
    return sum;
}

int searchDiagonally(vector<string> input) {
    int sum = 0;
    // Negative diagonal
    for (int i = 0; i <= input.size() - 4; i++) {
        for (int j = 0; j <= input.at(0).length() - 4; j++) {
            if (i + 3 < input.size() && j + 3 < input.at(0).length()) {
                string substr = "";
                substr = substr + input.at(i)[j] + input.at(i+1)[j+1] + input.at(i+2)[j+2] + input.at(i+3)[j+3];
                if (substr == "XMAS" || substr == "SAMX") {
                    sum++;
                }
            }
        }
    }

    // Positive diagonal
    for (int i = 3; i < input.size(); i++) {
        for (int j = 0; j <= input.at(0).length() - 4; j++) {
            if (i - 3 >= 0 && j + 3 < input.at(0).length()) {
                string substr = "";
                substr = substr + input.at(i)[j] + input.at(i-1)[j+1] + input.at(i-2)[j+2] + input.at(i-3)[j+3];
                if (substr == "XMAS" || substr == "SAMX") {
                    sum++;
                }
            }
        }
    }
    //cout << "diagonally: " << sum << endl;
    return sum;
}

int part1(vector<string> input) {
    int sum = 0;
    sum += searchHorizontally(input);
    sum += searchVertically(input);
    sum += searchDiagonally(input);

    cout << "Part 1 result: " << sum << endl;

    return 0;
}


int part2(vector<string> input) {
    int sum = 0; 

    for (int i = 1; i < input.size() - 1; i++) {
        for (int j = 1; j < static_cast<int>(input.at(0).length()) - 1; j++) {
            if (input.at(i)[j] == 'A') {
                char tl = input.at(i-1)[j-1];
                char tr = input.at(i-1)[j+1];
                char bl = input.at(i+1)[j-1];
                char br = input.at(i+1)[j+1];

                if (((tl == 'M' && tr == 'M') && (bl == 'S' && br == 'S')) ||
                    ((tl == 'S' && tr == 'S') && (bl == 'M' && br == 'M')) ||
                    ((tl == 'M' && bl == 'M') && (tr == 'S' && br == 'S')) ||
                    ((tl == 'S' && bl == 'S') && (tr == 'M' && br == 'M'))) {
                    sum++;
                }
            }
        }
    }



    cout << "Part 2 result: " << sum << endl;

    return 0;
}

int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day04_input.txt");

    part1(input);
    part2(input);

    return 0;
}