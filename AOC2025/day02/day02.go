package main

import (
	"AOC2025/lib"
	"fmt"
	"strconv"
	"strings"
)

func pow(base int, exp int) uint64 {
	result := 1
	for i := 0; i < exp; i++ {
		result *= base
	}
	return uint64(result)
}

func log10(n uint64) int {
	var log int = 0
	for n >= 10 {
		n = n / 10
		log++
	}
	return log
}



func checkRange(lowerBound uint64, upperBound uint64) uint64 {
	var total uint64 = 0
	//upperLength := log10(upperBound) + 1
	lowerLength := log10(lowerBound) + 1
	halfLength := lowerLength / 2
	for i := lowerBound / pow(10, halfLength); i <= upperBound/pow(10, halfLength); i++ {
		var candidate uint64 = i*pow(10, halfLength) + i
		if candidate >= lowerBound && candidate <= upperBound {
			total += candidate
		}
	}
	return total
}

func part1(ranges []string) uint64 {
	var total uint64 = 0
	for i := 0; i < len(ranges); i += 2 {

		var lowerBound []rune = []rune(ranges[i])
		var upperBound []rune = []rune(ranges[i+1])
		var lowerInt, _ = strconv.ParseUint(ranges[i], 10, 64)
		var upperInt, _ = strconv.ParseUint(ranges[i+1], 10, 64)

		var lowerLength int = len(lowerBound)
		var upperLength int = len(upperBound)

		if lowerLength < upperLength {
			if lowerLength%2 == 0 {
				upperInt = pow(10, lowerLength) - 1
				total += checkRange(lowerInt, upperInt)
			} else if upperLength%2 == 0 {
				lowerInt = pow(10, upperLength-1)
				total += checkRange(lowerInt, upperInt)
			}
		} else if lowerLength%2 == 0 {
			total += checkRange(lowerInt, upperInt)
		}
	}

	return total
}



func checkRange2(lowerBound uint64, upperBound uint64) uint64 {
	var total uint64 = 0
	length := log10(lowerBound) + 1

	seen := make(map[uint64]bool)

	// Try all pattern lengths from 1 to length/2
	for patternLen := 1; patternLen <= length/2; patternLen++ {
		// Only consider patterns that divide evenly into the total length
		if length % patternLen == 0 {
			// Calculate the range of pattern values to check
			minPattern := lowerBound / pow(10, length-patternLen)
			maxPattern := upperBound / pow(10, length-patternLen)

			for pattern := minPattern; pattern <= maxPattern; pattern++ {
				patternStr := strconv.FormatUint(pattern, 10)

				// Skip if pattern doesn't have the right length
				if len(patternStr) != patternLen {
					continue
				}

				// Build candidate by repeating pattern
				candidate := ""
				for len(candidate) < length {
					candidate += patternStr
				}

				candidateInt, _ := strconv.ParseUint(candidate, 10, 64)

				if candidateInt >= lowerBound && candidateInt <= upperBound && !seen[candidateInt] {
					total += candidateInt
					seen[candidateInt] = true
				}
			}
		}
	}
	return total
}

func part2(ranges []string) uint64 {
	var total uint64 = 0

	for i := 0; i < len(ranges); i += 2 {
		var lowerInt, _ = strconv.ParseUint(ranges[i], 10, 64)
		var upperInt, _ = strconv.ParseUint(ranges[i+1], 10, 64)

		var lowerLength int = log10(lowerInt) + 1
		var upperLength int = log10(upperInt) + 1

		// If the lengths are different, split into two ranges
		if lowerLength < upperLength {
			// Lower range
			lowerRange1 := lowerInt
			upperRange1 := pow(10, lowerLength) - 1
			total += checkRange2(lowerRange1, upperRange1)

			// Upper range
			lowerRange2 := pow(10, upperLength-1)
			upperRange2 := upperInt
			total += checkRange2(lowerRange2, upperRange2)
		} else {
			total += checkRange2(lowerInt, upperInt)
		}

	}
	return total
}



func pasrseRanges(line string) []string {
	var rangesString []string = strings.Split(line, ",")

	var ranges []string = make([]string, 0)
	for _, r := range rangesString {
		var bounds []string = strings.Split(r, "-")
		ranges = append(ranges, bounds...)
	}
	return ranges
}

func main() {
	var lines string = lib.ReadInput("day02/input.txt")[0]
	ranges := pasrseRanges(lines)

	fmt.Println("Part 1, The sum of the invalid IDs is: " + strconv.FormatUint(part1(ranges), 10))
	fmt.Println("Part 2, The sum of the invalid IDs is: " + strconv.FormatUint(part2(ranges), 10))

}
