package main

import (
	"AOC2025/lib"
	"fmt"
)


func part1(diagram [][]rune) int {
	var accessibleRolls int = 0

	for i := 0; i < len(diagram); i++ {
		for j := 0; j < len(diagram[i]); j++ {
			if diagram[i][j] == '@' {
				var adjacentRolls int = 0
				for k := i-1; k < i+2; k++ {
					for l := j-1; l < j+2; l++ {
						if (k >= 0 && k < len(diagram)) && (l >= 0 && l < len(diagram[i])) {
							if k == i && l == j {
								continue
							} else if diagram[k][l] == '@' {
								adjacentRolls++
							}
						}
					}
				}
				if adjacentRolls < 4 {
					accessibleRolls++
				}
			} 
		}
	}
	return accessibleRolls
}


func part2(diagram [][]rune) int {
	var accessibleRolls int = 0

	for {
		var removedRollsThisIter int = 0

		for i := 0; i < len(diagram); i++ {
			for j := 0; j < len(diagram[i]); j++ {
				if diagram[i][j] == '@' {
					var adjacentRolls int = 0
					for k := i-1; k < i+2; k++ {
						for l := j-1; l < j+2; l++ {
							if (k >= 0 && k < len(diagram)) && (l >= 0 && l < len(diagram[i])) {
								if k == i && l == j {
									continue
								} else if diagram[k][l] == '@' {
									adjacentRolls++
								}
							}
						}
					}
					if adjacentRolls < 4 {
						removedRollsThisIter++
						diagram[i][j] = '.'
					}
				} 
			}
		}
		if removedRollsThisIter == 0 {
			break
		} else {
			accessibleRolls += removedRollsThisIter
		}
	}
	return accessibleRolls
}


func main() {
	var input []string = lib.ReadInput("day04/input.txt")
	var diagram [][]rune
	for _, line := range input {
		diagram = append(diagram, []rune(line))
	}

	fmt.Println("Part 1, the number of accecible rolls is: ", part1(diagram))
	fmt.Println("Part 1, the total number of removed rolls: ", part2(diagram))

}
