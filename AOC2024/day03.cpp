#include <iostream>
#include <fstream>
#include <ostream>
#include <string>
#include <vector>
#include <sstream>

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

bool isDigit(char c) {
    if (c >= 48 && c <= 57) return true;
    else return false; 
}


int part1(vector<string> input) {
    int sum = 0;
    for (int i = 0; i < input.size(); i++) {
        string line = input.at(i);
        for (int j = 0; j < line.length() - 4; j++) {
            string substr = line.substr(j, 4);
            if (substr == "mul(") {
                j += 4;
                string op1 = "";
                string op2 = "";
                bool valid = true;
                bool commaSeen = false;
                while (j < line.length()) {
                    char c = line[j];
                    if (c == ')') {
                        break;
                    }
                    else if (c == ',') {
                        commaSeen = true;
                    }
                    else if (isDigit(c)) {
                        if (!commaSeen) {
                            op1 = op1 + c;
                        }
                        else {
                            op2 = op2 + c;
                        }
                    }
                    else {
                        valid = false;
                        break;
                    }
                    j++;
                }
                if (valid && op1.length() >= 1 && op1.length() <= 3 && op2.length() >= 1 && op2.length() <= 3 ) {
                    sum += stoi(op1) * stoi(op2);
                }
            }
        }
    }

    cout << "Part 1 result: " << sum << endl;

    return 0;
}


int part2(vector<string> input) {
    int sum = 0;
    bool enabled = true;
    for (int i = 0; i < input.size(); i++) {
        string line = input.at(i);
        for (int j = 0; j < line.length() - 3; j++) {
            string mulstr = line.substr(j, 4);
            string dostr = line.substr(j, 4);
            string dontstr = "";
            if (j < line.length() - 6) {
                dontstr = line.substr(j, 7);
            }
            if (mulstr == "mul(" && enabled) {
                j += 4;
                string op1 = "";
                string op2 = "";
                bool valid = true;
                bool commaSeen = false;
                while (j < line.length()) {
                    char c = line[j];
                    if (c == ')') {
                        break;
                    }
                    else if (c == ',') {
                        commaSeen = true;
                    }
                    else if (isDigit(c)) {
                        if (!commaSeen) {
                            op1 = op1 + c;
                        }
                        else {
                            op2 = op2 + c;
                        }
                    }
                    else {
                        valid = false;
                        break;
                    }
                    j++;
                }
                if (valid && op1.length() >= 1 && op1.length() <= 3 && op2.length() >= 1 && op2.length() <= 3 ) {
                    sum += stoi(op1) * stoi(op2);
                }
            }
            else if (dostr == "do()") {
                enabled = true;
                j += 3;
            }
            else if (dontstr == "don't()") {
                enabled = false;
                j += 6;
            }
        }
    }

    cout << "Part 2 result: " << sum << endl;

    return 0;
}


int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day03_input.txt");

    part1(input);
    part2(input);
}
