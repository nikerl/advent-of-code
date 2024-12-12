#include <cmath>
#include <cstdio>
#include <cstdlib>
#include <fstream>
#include <iostream>
#include <string>
#include <vector>
#include <sstream>

using namespace std;

vector<unsigned long long> parseInput() {
    ifstream file("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day11_input.txt");
    string str;
    getline(file, str);

    vector<unsigned long long> result;
    stringstream ss (str);
    string item;
    while (getline (ss, item, ' ')) {
        result.push_back(stoull(item));
    }

    return result;
}

int getNumDigits(unsigned long long stone) {
    int numDigits = 0;
    while (stone > 0) {
        stone /= 10;
        numDigits++;
    }
    return numDigits;
}


void updateStones(vector<unsigned long long> *stones) {
    for (int j = 0; j < stones->size(); j++) {
            unsigned long long stone = stones->at(j);
            if (stone == 0) {
                stones->at(j) = 1;
            } else if (getNumDigits(stone) % 2 == 0) {
                int numDigits = getNumDigits(stone);
                int modifier = 1;
                for (int k = 0; k < numDigits / 2; k++) {
                    modifier *= 10;
                }
                unsigned long long leftStone = stone / modifier;
                unsigned long long rightStone = stone % modifier;

                stones->at(j) = leftStone;
                stones->insert(stones->begin() + j + 1, rightStone);
                j++;
            } else {
                stones->at(j) = stone * 2024;
            }
        }
}

int part1(vector<unsigned long long> stones) {
    for (int i = 0; i < 25; i++) {
        updateStones(&stones);
    }
    int numStones = stones.size();
    
    cout << "Part 1 result: " << numStones << endl;
    
    return 0;
}

int part2(vector<unsigned long long> stones) {
    for (int i = 0; i < 75; i++) {
        updateStones(&stones);
    }
    int numStones = stones.size();
    
    cout << "Part 1 result: " << numStones << endl;
    
    return 0;
}


int main() {
    vector<unsigned long long> stones = parseInput();
    part1(stones);
    part2(stones);

    return 0;

}