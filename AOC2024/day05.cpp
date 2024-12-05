#include <iostream>
#include <fstream>
#include <ostream>
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

vector<vector<int>> parseRules(vector<string> input) {
    vector<vector<int>> rules;
    for (int i = 0; i < input.size(); i++) {
        vector<string> substr = split(input.at(i), '|');
        if (substr.size() > 1) {
            vector<int> numbers = {stoi(substr.at(0)), stoi(substr.at(1))};
            rules.push_back(numbers);
        }
        else {
            return rules;
        }
    }
}

vector<vector<int>> parseSequence(vector<string> input) {
    vector<vector<int>> sequence;
    for (int i = 0; i < input.size(); i++) {
        vector<string> substr = split(input.at(i), ',');
        if (substr.size() > 1) {
            vector<int> numbers;
            for (int j = 0; j < substr.size(); j++) {
                numbers.push_back(stoi(substr.at(j)));
            }
            sequence.push_back(numbers);
        }
    }
    return sequence;
}

bool checkRules(vector<vector<int>> rules, int sequenceFirst, int sequenceSecond) {
    for (int i = 0; i < rules.size(); i++) {
        int ruleFirst = rules.at(i).at(0);
        int ruleSecond = rules.at(i).at(1);

        if (ruleFirst == sequenceFirst && ruleSecond == sequenceSecond) {
            return true;
        }
        else if (ruleFirst == sequenceSecond && ruleSecond == sequenceFirst) {
            return false;
        }
        else {
            continue;
        }
    }
    return true;
}


int calculateCenterSum(vector<vector<int>> sequence) {
    int sum = 0;
    for (int i = 0; i < sequence.size(); i ++) {
        int middleIndex = (sequence.at(i).size() / 2);
        sum += sequence.at(i).at(middleIndex);
    }
    return sum;
}


vector<vector<int>> part1(vector<vector<int>> rules, vector<vector<int>> sequence) {
    vector<vector<int>> validSequences;
    vector<vector<int>> invalidSequences;

    for (int i = 0; i < sequence.size(); i++) {
        bool isValid = true;
        for (int j = 0; j < sequence.at(i).size(); j++) {
            int sequenceFirst = sequence.at(i).at(j);
            if (isValid == false) {
                break;
            }
            for (int k = 0; k < sequence.at(i).size(); k++) {
                int sequenceSecond = sequence.at(i).at(k);
                if (isValid == false) {
                    break;
                }
                else if (j == k) {
                    continue;
                }
                else if (j < k) {
                    isValid = checkRules(rules, sequenceFirst, sequenceSecond);
                }
                else if (j > k) {
                    isValid = checkRules(rules, sequenceSecond, sequenceFirst);
                }
            }
        }
        if (isValid) {
            validSequences.push_back(sequence.at(i));
        }
        else {
            invalidSequences.push_back(sequence.at(i));
        }
    }

    int sum = calculateCenterSum(validSequences);

    cout << "Part 1 result: " << sum << endl;

    return invalidSequences;
}


int part2(vector<vector<int>> rules, vector<vector<int>> invalidSequences) {
    for (int i = 0; i < invalidSequences.size(); i ++) {
        bool wasModified;
        do {
            wasModified = false;
            for (int j = 0; j < invalidSequences.at(i).size(); j++) {
                int sequenceFirst = invalidSequences.at(i).at(j);
                for (int k = 0; k < invalidSequences.at(i).size(); k++) {
                    int sequenceSecond = invalidSequences.at(i).at(k);
                    bool isValid = true;
                    if (j == k) {
                        continue;
                    }
                    else if (j < k) {
                        isValid = checkRules(rules, sequenceFirst, sequenceSecond);
                    }
                    else if (j > k) {
                        isValid = checkRules(rules, sequenceSecond, sequenceFirst);
                    }

                    if (isValid == false) {
                        invalidSequences.at(i).at(j) = sequenceSecond;
                        invalidSequences.at(i).at(k) = sequenceFirst;
                        wasModified = true;
                        break;
                    }
                }
            }
        } while (wasModified);

    }

    int sum = calculateCenterSum(invalidSequences);

    cout << "Part 2 result: " << sum << endl;

    return 0;
}


int main() {
    vector<string> input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day05_input.txt");
    vector<vector<int>> rules = parseRules(input);
    vector<vector<int>> sequence = parseSequence(input);

    vector<vector<int>> invalidSequences = part1(rules, sequence);
    part2(rules, invalidSequences);

    return 0;
}