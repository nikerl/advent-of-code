package main

import (
	"AOC2025/lib"
	"fmt"
)

type coord struct {
	x int
	y int
}

func part1(diagram *[]string, coordsSeen *map[coord]bool, startCol int, startRow int) int {
	if startCol < 0 || startCol >= len((*diagram)[0]) {
		return 0
	}
	for startRow < len(*diagram) {
		if (*coordsSeen)[coord{x: startCol, y: startRow}] {
			return 0
		} else if (*diagram)[startRow][startCol] == '^' {
			(*coordsSeen)[coord{x: startCol, y: startRow}] = true
			return 1 + part1(diagram, coordsSeen, startCol+1, startRow) + part1(diagram, coordsSeen, startCol-1, startRow)
		} else {
			//(*diagram)[startRow] = (*diagram)[startRow][:startCol] + "|" + (*diagram)[startRow][startCol+1:]
			(*coordsSeen)[coord{x: startCol, y: startRow}] = true
		}
		startRow++
	}
	return 0
}



/* func part2(diagram *[]string, startCol int) int {
	var unexplored []coord = []coord{{x: startCol, y: 1}}
	var count int = 0
	var seen map[coord]int = map[coord]int{}

	var lastSplit coord = coord{x: -1, y: -1}
	for len(unexplored) > 0 {
		var current coord = unexplored[len(unexplored)-1]
		unexplored = unexplored[:len(unexplored)-1]
		if seen[current] > 0 {
			seen[current]++
			continue
		}
		for {
			if current.x < 0 || current.x >= len((*diagram)[0]) {
				break
			} else if current.y >= len(*diagram) {
				count++
				seen[lastSplit] = 1
				break
			} else {
				if (*diagram)[current.y][current.x] == '^' {
					lastSplit = current
					if seen[current] > 0 {
						seen[current]++
					} else {
						seen[current] = 1
					}
					unexplored = append(unexplored, coord{x: current.x + 1, y: current.y})
					unexplored = append(unexplored, coord{x: current.x - 1, y: current.y})
					break
				} else {
					current.y++
				}
			}
		}
	}

	return count
} */


func countTimelines(diagram *[]string, x int, y int, memo *map[coord]int) int {
    // Out of bounds horizontally = 0 timelines
    if x < 0 || x >= len((*diagram)[0]) {
        return 0
    }
    
    // Reached bottom = 1 timeline
    if y >= len(*diagram) {
        return 1
    }
    
    // Check if we've already calculated this position
    pos := coord{x: x, y: y}
    if (*memo)[pos] != 0 {
        return (*memo)[pos]
    }
    
    var result int
    
    // If splitter, add timelines from both branches
    if (*diagram)[y][x] == '^' {
        result = countTimelines(diagram, x-1, y, memo) + countTimelines(diagram, x+1, y, memo)
    } else {
        // Otherwise continue downward
        result = countTimelines(diagram, x, y+1, memo)
    }
    
    // Store result before returning
    (*memo)[pos] = result
    return result
}

func part2(diagram *[]string, startCol int) int {
    memo := make(map[coord]int)
    return countTimelines(diagram, startCol, 1, &memo)
}

func findStart(diagram string) int {
	var startIndex int = 0
	for i := 0; i < len(diagram); i++ {
		if diagram[i] == 'S' {
			startIndex = i
			break
		}
	}
	return startIndex
}

func main() {
	diagram := lib.ReadInput("day07/input.txt")
	startCol := findStart(diagram[0])

	fmt.Println("Day 7, Part 1:", part1(&diagram, &map[coord]bool{}, startCol, 1))
	fmt.Println("Day 7, Part 2:", part2(&diagram, startCol))
}
