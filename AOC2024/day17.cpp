#include <climits>
#include <cmath>
#include <cstdlib>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <sstream>

using namespace std;

enum instructions {
    adv = 0, 
    bxl = 1, 
    bst = 2, 
    jnz = 3, 
    bxc = 4, 
    out = 5, 
    bdv = 6, 
    cdv = 7
};


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


vector<int> parseRegisters(vector<string> input) {
    vector<int> registers;
    for (int i = 0; i < 3; i++) {
        registers.push_back(stoi(split(input[i], ' ')[2]));
    }
    return registers;
}

vector<int> parseProgram(vector<string> input) {
    vector<string> instructions = split(split(input[4], ' ')[1], ',');
    vector<int> program;
    for (int i = 0; i < instructions.size(); i++) {
        program.push_back(stoi(instructions[i]));
    }
    return program;
}

inline int power(int base, int exponent) {
    int product = 1;
    for (int i = 0; i < exponent; i++) {
        product *= base;
    }
    return product;
}

string simulateComputer(vector<int> program, vector<int> registers) {
    string output = "";
    int A = registers[0];
    int B = registers[1];
    int C = registers[2];
    int IP = 0; // Instruction Pointer

    while(IP < program.size()) {
        int opcode = program[IP];
        int operand = program[IP + 1];

        // Check if OP needs combo operand
        bool combo = false;
        switch (opcode) {
            case adv:
            case bst:
            case out:
            case bdv:
            case cdv:
                combo = true;
                break;
        }

        // Set combo operand based on operand value 
        if (combo) {
            switch (operand) {
                case 4:
                    operand = A;
                    break;
                case 5:
                    operand = B;
                    break;
                case 6:
                    operand = C;
                    break;
                case 7:
                    cout << "Error: Combo 7, invalid program" << endl;
                default:
                    break;
            }
        }

        // ALU logic
        switch (opcode) {
            case adv: // A = A/2^operand
                A = A / power(2, operand);
                IP += 2;
                break;
            case bxl:  // B = B XOR operand
                B = B ^ operand;
                IP += 2;
                break;
            case bst: // B = operand modulo 8
                B = operand % 8;
                IP += 2;
                break;
            case jnz: // Branch if A not equal 0
                if (A != 0) IP = operand;
                else IP += 2;
                break;
            case bxc: // B = B XOR C
                B = B ^ C;
                IP += 2;
                break;
            case out: // output: operand modulo 8
                if (!output.empty()) output += ",";
                output += to_string(operand % 8);
                IP += 2;
                break;
            case bdv: // B = A/2^operand
                B = A / power(2, operand);
                IP += 2;
                break;
            case cdv: // C = A/2^operand
                C = A / power(2, operand);
                IP += 2;
                break;
        }
    }

    return output;
}


int part1(vector<int> program, vector<int> registers) {
    string output = simulateComputer(program, registers);

    cout << "Part 1 result: " << output << endl;

    return 0;
}

int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day17_input.txt");
    vector<int> registers = parseRegisters(input);
    vector<int> program = parseProgram(input); 

    part1(program, registers);

    return 0;
}


