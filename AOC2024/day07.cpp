#include <cmath>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <sstream>

using namespace std;

vector<string> split (const string &s, char delim) {
    vector<string> result;
    stringstream ss (s);
    string item;

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



int power(int base, int exponent) {
    int product = 1;
    for (int i = 0; i < exponent; i++) {
        product *= base;
    }

    return product;
}


unsigned long long isValidEquation(vector<string> operands, vector<char> ops) {
    unsigned long long result = strtoull(operands[0].substr(0, operands[0].length() - 1).c_str(), nullptr, 10);
    int numOps = operands.size() - 2;

    for (int i = 0; i < power(ops.size(), numOps); i++) {
        int opMask = i;
        unsigned long long tempSum = strtoull(operands.at(1).c_str(), nullptr, 10);
        for (int j = 2; j < operands.size(); j++) {
            char op = ops.at(opMask % ops.size());
            opMask = opMask / ops.size();

            unsigned long long operand = strtoull(operands.at(j).c_str(), nullptr, 10);
            switch (op) {
                case '+': 
                    tempSum += operand;
                    break;
                case '*':
                    tempSum *= operand;
                    break;
                case '|':
                    tempSum = strtoull((to_string(tempSum) + to_string(operand)).c_str(), nullptr, 10);
            }
        }
        if (tempSum == result) {
            return tempSum;
        }
    }
    return 0;
}


int part1(vector<string> equations) {
    vector<char> ops = {'+', '*'};
    unsigned long long sum = 0;
    for (int i = 0; i < equations.size(); i++) {
        vector<string> operands = split(equations.at(i), ' ');
        sum += isValidEquation(operands, ops);
    }

    cout << "Part 1 result: " << sum << endl;
    return 0;
}

int part2(vector<string> equations) {
    vector<char> ops = {'+', '*', '|'};
    unsigned long long sum = 0;
    for (int i = 0; i < equations.size(); i++) {
        vector<string> operands = split(equations.at(i), ' ');
        sum += isValidEquation(operands, ops);
    }

    cout << "Part 2 result: " << sum << endl;
    return 0;
}


int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day07_input.txt");
    part1(input);
    part2(input);

    return 0;
}