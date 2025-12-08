package main

import (
	"AOC2025/lib"
	"fmt"
	"sort"
)


func part1(freshRanges [][]int, ingredients []int) int {
	var sumValid int = 0

	for _, ingredient := range ingredients {
		for _, freshRange := range freshRanges {
			if ingredient >= freshRange[0] && ingredient <= freshRange[1] {
				sumValid++
				break
			}
		}
	}

	return sumValid
}


func part2(freshRanges [][]int) int {
	// Sort ranges by start position
	sort.Slice(freshRanges, func(i, j int) bool {
		return freshRanges[i][0] < freshRanges[j][0]
	})

	// Merge overlapping or adjacent ranges
	merged := [][]int{freshRanges[0]}

	for i := 1; i < len(freshRanges); i++ {
		current := freshRanges[i]
		last := merged[len(merged)-1]

		// Check if current range overlaps or is adjacent to the last merged range
		if current[0] <= last[1]+1 {
			// Merge by extending the end of the last range if necessary
			if current[1] > last[1] {
				last[1] = current[1]
			}
		} else {
			// No overlap, add as a new range
			merged = append(merged, current)
		}
	}

	// Count total IDs in merged ranges
	var sumMerged int = 0
	for _, r := range merged {
		var idInRange int = r[1] - r[0] + 1
		sumMerged += idInRange
	}

	return sumMerged
}


func parseDatabase(database []string) ([][]int, []int) {
	var freshRanges [][]int
	var ingredients []int

	var index int = 0
	for i := index; i < len(database); i++ {
		var line string = database[i]
		if line == "" {
			break
		} else {
			var start, end int
			fmt.Sscanf(line, "%d-%d", &start, &end)
			freshRanges = append(freshRanges, []int{start, end})
		}
		index++
	}
	for i := index; i < len(database); i++ {
		var line string = database[i]
		var ingredient int
		fmt.Sscanf(line, "%d", &ingredient)
		ingredients = append(ingredients, ingredient)
	}

	return freshRanges, ingredients
}

func main() {
	var database []string = lib.ReadInput("day05/input.txt")

	freshRanges, ingredients := parseDatabase(database)

	fmt.Println("Part 1, the sum of all valid ingredients is: ", part1(freshRanges, ingredients))
	fmt.Println("Part 2, the sum of all merged ranges is: ", part2(freshRanges))
}
