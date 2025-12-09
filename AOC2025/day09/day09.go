package main

import (
	"AOC2025/lib"
	"fmt"
	"math"
	"sort"
)

type coords struct {
	x int
	y int
}
type rectangle struct {
	c1 coords
	c2 coords
	area int
}


func part1(coordsList []coords) int {
	var rectangles []rectangle
	for i := 0; i < len(coordsList); i++ {
		for j := i+1; j < len(coordsList); j++ {
			var c1 coords = coordsList[i]
			var c2 coords = coordsList[j]
			var area int = int((math.Abs(float64(c2.x - c1.x)) + 1) * (math.Abs(float64(c2.y - c1.y)) + 1))
			rectangles = append(rectangles, rectangle{c1, c2, area})
		}
	}

	sort.Slice(rectangles, func(i, j int) bool {
        return rectangles[i].area > rectangles[j].area
    })

	return rectangles[0].area
}

func parseCoords(input []string) []coords {
	var result []coords
	for _, line := range input {
		var coord coords
		fmt.Sscanf(line, "%d,%d", &coord.x, &coord.y)
		result = append(result, coord)
	}
	return result
}

func main() {
	var input []string = lib.ReadInput("day09/input.txt")
	var coords []coords = parseCoords(input)

	fmt.Println("Day 9, Part 1: The largest rectangle is: ", part1(coords))
}
