#include <algorithm>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <sstream>
#include <algorithm>

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

int binFirstSearch(int element, int array[], int size) {
    int low = 0;
    int high = size - 1;
    int i = low + (high - low) / 2;
    int currentEl = array[i];

    // Find any occurrence of the element
    while (low <= high) {
        i = low + (high - low) / 2;
        currentEl = array[i];
        if (currentEl == element) {
            break;
        }
        if (currentEl > element) {
            high = i - 1;
        } else {
            low = i + 1;
        }
    }

    if (low > high) {
        return -1;
    }

    // Find the first occurrence
    while (i > 0 && array[i - 1] == element) {
        i--;
    }

    return i;
}



int part1(int* left, int* right, int size) {
    int sum = 0;
    for (int i = 0; i < size; i++) {
        sum += abs(left[i] - right[i]);
    }

    cout << "Part 1 result: " << sum << "\n";

    return 0;
}

int part2(int* left, int* right, int size) {
    int sum = 0;

    for (int i = 0; i < size; i++) {
        int location = left[i];
        int rightStart = binFirstSearch(location, right, size);
        int num = 0;
        if (rightStart != -1) {
            while (right[rightStart + num] == location) {
                num++;
            }
        }
        sum += location * num;        
    }

    cout << "Part 2 result: " << sum << "\n";

    return 0;
}

int main () {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day01_input.txt");

    int size = input.size();
    int left[size];
    int right[size];

    for (int i = 0; i < size; i++) {
        string line = input.at(i);

        char delimiter = ' ';
        vector<string> substr = split(line, delimiter);
        left[i] = stoi(substr.at(0));
        right[i] = stoi(substr.at(3));
    }
    sort(left, left + size);
    sort(right, right + size);

    part1(left, right, size);
    part2(left, right, size);
}
