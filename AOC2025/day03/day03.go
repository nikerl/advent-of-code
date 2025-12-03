package main

import (
	"AOC2025/lib"
	"fmt"
)



func part1(batteryBanks []string) int {
	var totalJoltage int = 0

	for i := 0; i < len(batteryBanks); i++ {
		var batteryBank []rune = []rune(batteryBanks[i])
		var firstBatteryIndex int = 0
		var firstBatteryMax int = 0
		var secondBatteryMax int = 0

		// Finding the largest battery in the bank
		for j := 0; j < len(batteryBank) - 1; j++ {
			var batteryValue int = int(batteryBank[j] - '0')
			if batteryValue > firstBatteryMax {
				firstBatteryMax = batteryValue
				firstBatteryIndex = j
			}
		}
		// Finding the second largest battery sequentially after the first
		for j := firstBatteryIndex + 1; j < len(batteryBank); j++ {
			var batteryValue int = int(batteryBank[j] - '0')
			if batteryValue > secondBatteryMax {
				secondBatteryMax = batteryValue
			}
		}

		var bankJoltage int = firstBatteryMax * 10 + secondBatteryMax 
		totalJoltage += bankJoltage
	}	
	return totalJoltage
}


// Generalized version of part 1 to handle any number of batteries turned on
func part2(batteryBanks []string, batteriesToTurnOn int) int {
	var totalJoltage int = 0

	for i := 0; i < len(batteryBanks); i++ {
		var batteryBank []rune = []rune(batteryBanks[i])
		var batteryIndex []int = make([]int, batteriesToTurnOn)
		var batteryMax []int = make([]int, batteriesToTurnOn)

		for j := 0; j < batteriesToTurnOn; j++ {
			var startIndex int = 0
			if j > 0 {
				startIndex = batteryIndex[j-1] + 1
			}
			for k := startIndex; k < len(batteryBank) - (batteriesToTurnOn - j - 1); k++ {
				var batteryValue int = int(batteryBank[k] - '0')
				if batteryValue > batteryMax[j] {
					batteryMax[j] = batteryValue
					batteryIndex[j] = k
				}
			}
		}
		var bankJoltage int = 0
		for j := 0; j < batteriesToTurnOn; j++ {
			bankJoltage = lib.Pow(10, batteriesToTurnOn - j - 1) * batteryMax[j] + bankJoltage
		}
		totalJoltage += bankJoltage
	}	
	return totalJoltage
}



func main() {
	var batteryBanks []string = lib.ReadInput("day03/input.txt")

	fmt.Println("Part 1, The sum of the maximum joltage of each bank is: ", part1(batteryBanks))
	fmt.Println("Part 2, The sum of the maximum joltage of each bank with 12 batteries turned on is: ", part2(batteryBanks, 12))
}