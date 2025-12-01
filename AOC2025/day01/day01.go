package main

import (
	"AOC2025/lib"
	"fmt"
	"strconv"
)

const START int = 50


func part1(lines []string) int {
	var pos int = START

	var password = 0

	for _, line := range lines {
		var dir byte = line[0]
		var value, _ = strconv.Atoi(line[1:])

		//fmt.Println("dir:" + string(dir) + " value:" + strconv.Itoa(value))

		if dir == 'L' {
			value = -value
		}

		pos += value
		pos = pos % 100

		if pos < 0 {
			pos += 100
		}

		if pos == 0 {
			password++
		}

		//fmt.Println("pos:" + strconv.Itoa(pos))
	}

	return password
}


func part2(lines []string) int {
	var pos int = START

	var password = 0

	for _, line := range lines {
		var dir byte = line[0]
		var turns, _ = strconv.Atoi(line[1:])

		oldPos := pos

		var numRotations int = turns / 100
		password += numRotations
		turns = turns % 100

		switch dir {
			case 'L':
				if oldPos == 0 {
					oldPos = 100
				}
				if turns >= oldPos {
					password++
				}

				pos -= turns
				if pos < 0{
					pos += 100
				}
				
			case 'R':
				if turns >= 100 - oldPos {
					password++
				}

				pos += turns
				if pos >= 100 {
					pos -= 100
				}
		}

	}

	return password
}


func main() {
	lines := lib.ReadInput("day01/input.txt")

	fmt.Println("Part 1, The password is: " + strconv.Itoa(part1(lines)))
	fmt.Println("Part 2, The password is: " + strconv.Itoa(part2(lines)))
}
