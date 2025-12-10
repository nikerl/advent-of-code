package main

import (
	"AOC2025/lib"
	"fmt"
	"math"
	"strconv"
)

type lightButton struct {
	lightDiagram int
	buttonDiagram []int
	joltageRecs []int
}


func generateSuperset(n int) [][]int {
    totalCombinations := 1 << n // 2^n combinations
    result := make([][]int, 0, totalCombinations)
    
    for mask := 0; mask < totalCombinations; mask++ {
        combination := []int{}
        for i := 0; i < n; i++ {
            if mask&(1<<i) != 0 {
                combination = append(combination, i)
            }
        }
        result = append(result, combination)
    }
    
    return result
}

func part1(lightButtons []lightButton) int {
	var total int = 0
	for _, lb := range lightButtons {
		var superset [][]int = generateSuperset(len(lb.buttonDiagram))
 
		var leastButtonsPressed int = math.MaxInt
		for _, subset := range superset {
			var lights int = 0
			for _, index := range subset {
				lights ^= lb.buttonDiagram[index]
				if lights == lb.lightDiagram {
					if len(subset) < leastButtonsPressed {
						leastButtonsPressed = len(subset)
					}
				}
			}
		}
		total += leastButtonsPressed
	}
	return total
}


func parseInput(input []string) []lightButton {
	var lightButtons []lightButton 
	for _, line := range input {
		var lb lightButton
		var splitLine []string = lib.SplitString(line, ' ')

		var lightDiagram []rune = []rune(splitLine[0])[1:len(splitLine[0])-1]
		var ld int = 0
		for i, c := range lightDiagram {
			if c == '#' {
				ld |= 1 << i
			}
		}
		lb.lightDiagram = ld

		var bds []int
		for _, s := range splitLine[1:len(splitLine)-1] {
			var buttonDiagram []rune = []rune(s)[1:len(s)-1]
			var bd int = 0
			for _, c := range buttonDiagram {
				if c != ',' {
					var number int = int(c - '0')
					bd |= 1 << number 
				}
			}
			bds = append(bds, bd)
		}
		lb.buttonDiagram = bds

		var jr []int
		var s string = splitLine[len(splitLine)-1]
		var joltageRecs []string = lib.SplitString(s[1:len(s)-1], ',')
		for _, n := range joltageRecs {
			number, _ := strconv.Atoi(n)
			jr = append(jr, number)
		}
		lb.joltageRecs = jr

		lightButtons = append(lightButtons, lb)
	}
	return lightButtons
}

func main() {
	var input []string = lib.ReadInput("day10/input.txt")
	var lightButtons []lightButton = parseInput(input)

	fmt.Println("Day 10, Part 1: The total least buttons pressed is:", part1(lightButtons))
}
