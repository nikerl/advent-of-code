#include <iostream>
#include <vector>
#include <unordered_map>
#include <string>
#include <cmath>
#include <cstdio>
#include <cstdlib>
#include <fstream>
#include <sstream>

using namespace std;

vector<string> parseInput() {
    ifstream file("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day11_input.txt");
    string str;
    getline(file, str);

    vector<string> result;
    stringstream ss (str);
    string item;
    while (getline (ss, item, ' ')) {
        result.push_back(item);
    }

    return result;
}


// Function to split the number into left and right halves
void splitNumber(const string& num, string& left, string& right) {
    int len = num.length();
    int mid = len / 2;
    left = num.substr(0, mid);
    right = num.substr(mid);
    // Remove leading zeros
    while (left.length() > 1 && left[0] == '0') left.erase(0, 1);
    while (right.length() > 1 && right[0] == '0') right.erase(0, 1);
}

// Memoization cache
unordered_map<string, long long> memo;

// Recursive function to compute the number of stones after N blinks
long long countStones(int N, const string& num) {
    if (N == 0) return 1;
    string key = to_string(N) + "_" + num;
    if (memo.find(key) != memo.end()) return memo[key];

    if (num == "0") {
        // Rule 1
        memo[key] = countStones(N - 1, "1");
    } else if (num.length() % 2 == 0) {
        // Rule 2
        string left, right;
        splitNumber(num, left, right);
        memo[key] = countStones(N - 1, left) + countStones(N - 1, right);
    } else {
        // Rule 3
        // Multiply num by 2024
        string product = to_string(stoll(num) * 2024);
        memo[key] = countStones(N - 1, product);
    }
    return memo[key];
}

int main() {
    int N = 75;
    vector<string> initialStones = parseInput();
    long long totalStones = 0;
    for (const auto& stone : initialStones) {
        totalStones += countStones(N, stone);
    }
    cout << "Total stones after " << N << " blinks: " << totalStones << endl;
    return 0;
}