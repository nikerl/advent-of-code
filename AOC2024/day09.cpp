#include <cmath>
#include <codecvt>
#include <iostream>
#include <fstream>
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

int ctoi(char c) {
    return c - 48;
}

void decompress(vector<int> *decompressed, string *filesystem) {
    int id = 0;
    for (int i = 0; i < filesystem->length(); i++) {
        if (i % 2 == 0) {
            for (int j = 0; j < ctoi(filesystem->at(i)); j++) {
                decompressed->push_back(id);
            }
            id++;
        }
        else {
            for (int j = 0; j < ctoi(filesystem->at(i)); j++) {
                decompressed->push_back(-1);
            }
        }
    }
}

void rearrangeFragment(vector<int> *decompressed) {
    for (int i = 0; i < decompressed->size(); i++) {
        if (decompressed->at(i) == -1) {
            int movedChunk = decompressed->at(decompressed->size() - 1);
            decompressed->at(i) = movedChunk;
            decompressed->pop_back();
            if (movedChunk == -1) {
                i--;
            }
        }
    }
}

void rearrange(vector<int> *decompressed, string *filesystem) {
    vector<int> movedID;
    int fileIndex = filesystem->size() - 1;
    for (int i = decompressed->size() - 1; i >= 0; i--) {
        if (decompressed->at(i) != -1) {
            int fileStartIndex = i;
            int file = decompressed->at(fileStartIndex);
            int fileSize = 1;
            int index = fileStartIndex;
            while (index > 0) {
                if (file != decompressed->at(index - 1)) {
                    break;
                }
                fileSize++;
                index--;
            }
            bool alreadyMoved = false;
            for (int l = 0; l < movedID.size(); l++) {
                if (file == movedID[l]) {
                    alreadyMoved = true;
                    break;
                }
            }
            if (!alreadyMoved) {
                
                int continiousFreeSpace = 0;
                int freeSpaceStartIndex = -1;
                for (int j = 0; j <= fileStartIndex; j++) {
                    if (decompressed->at(j) == -1) {
                        if (continiousFreeSpace == 0) {
                            freeSpaceStartIndex = j;
                        }
                        continiousFreeSpace++;
                    }
                    else {
                        freeSpaceStartIndex = -1;
                        continiousFreeSpace = 0;
                    }
                    if (continiousFreeSpace == fileSize) {
                        for (int k = 0; k < fileSize; k++) {
                            decompressed->at(freeSpaceStartIndex) = decompressed->at(fileStartIndex);
                            decompressed->at(fileStartIndex) = -1;
                            freeSpaceStartIndex++;
                            fileStartIndex--;
                        }
                        movedID.push_back(file);
                        break;
                    }
                }
            }
            i -= fileSize - 1;
            fileIndex -= 2;
        }
    }
}

unsigned long long calculateChecksum(vector<int> *rearranged) {
    unsigned long long checksum = 0;
    for (int i = 0; i < rearranged->size(); i++) {
        if (rearranged->at(i) != -1) {
            checksum += i * rearranged->at(i);
        }
    }
    return checksum;
}

int part1(string filesystem) {
    vector<int> decompressed;
    decompress(&decompressed, &filesystem);
    rearrangeFragment(&decompressed);
    unsigned long long checksum = calculateChecksum(&decompressed);

    cout << "Part 1 result: " << checksum << endl;

    return 0;
}

int part2(string filesystem) {
    vector<int> decompressed;
    decompress(&decompressed, &filesystem);
    rearrange(&decompressed, &filesystem);
    unsigned long long checksum = calculateChecksum(&decompressed);
    
    cout << "Part 2 result: " << checksum << endl;

    return 0;
}


int main() {
    string input = readFile("/home/nikerl/Documents/Repos/advent-of-code/AOC2024/input/day09_input.txt")[0];
    part1(input);
    part2(input);

    return 0;
}