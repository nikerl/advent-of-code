package main

import (
	"AOC2025/lib"
	"fmt"
	"strconv"
)


func part1(operands [][]int, operators []string) int {
	var totalSum int = 0
	
	for i := 0; i < len(operators); i++ {
		var sum int = 0
		for j := 0; j < len(operands); j++ {
			switch operators[i] {
				case "+":
					sum += operands[j][i]
				case "*":
					if sum == 0 {
						sum = 1
					}
					sum *= operands[j][i]
			}
		}
		totalSum += sum
	}

	return totalSum
}


func part2(operands [][]string, operators []string) int {
	var totalSum int = 0

	var operandsT [][]string = lib.TransposeMatrix(operands)
	for i := 0; i < len(operators); i++ {
		// convert list of ints to matrix of digits
		var digitsMatrix [][]rune = [][]rune{}
		for j := 0; j < len(operandsT[i]); j++ {
			digits := []rune(operandsT[i][j])
			
			digitsMatrix = append(digitsMatrix, digits)
		}

		var sum int = 0
		for j := 0; j < len(digitsMatrix[0]); j++ {
			// construct the column number
			var number int = 0
			for k := 0; k < len(digitsMatrix); k++ {
				if digitsMatrix[k][j] == ' ' {
					continue
				}
				number *= 10
				number += int(digitsMatrix[k][j] - '0')
			}

			// calculate the sum/product
			switch operators[i] {
				case "+":
					sum += number
				case "*":
					if sum == 0 {
						sum = 1
					}
					sum *= number
			}
		}
		totalSum += sum
	}

	return totalSum
}


func parseWorksheet2(input []string) ([][]string, []string) {
	
	var operatorsRune []rune = []rune(input[len(input)-1])
	var operators []string = []string{}
	var numbersIndex []int = []int{}
	for i := 0; i < len(operatorsRune); i++ {
		if operatorsRune[i] == '+' || operatorsRune[i] == '*' {
			operators = append(operators, string(operatorsRune[i]))
			numbersIndex = append(numbersIndex, i)
		}
	}
	
	var operands [][]string
	for i := 0; i < len(input)-1; i++ {
		var line []rune = []rune(input[i])
		operands = append(operands, []string{})
		for j := 0; j < len(numbersIndex); j++ {
			var startIndex int = numbersIndex[j]
			var endIndex int
			if j+1 < len(numbersIndex) {
				endIndex = numbersIndex[j+1]-1
			} else {
				endIndex = len(line)
			}
			operands[i] = append(operands[i], string(line[startIndex:endIndex]))
		}

	}
	return operands, operators
}

func parseWorksheet(input []string) ([][]int, []string) {
	var operands [][]int = make([][]int, len(input)-1)

	for i := 0; i < len(input)-1; i++ {
		var line string = input[i]
		var strOperands []string = lib.SplitString(line, ' ')

		for operand := range strOperands {
			intOperand, _ := strconv.Atoi(strOperands[operand])
			operands[i] = append(operands[i], intOperand)
		}
	}
	var operators []string = lib.SplitString(input[len(input)-1], ' ')

	return operands, operators
}

func main() {
	var input []string = lib.ReadInput("day06/input.txt")
	operands, operators := parseWorksheet(input)
	operands2, operators2 := parseWorksheet2(input)

	fmt.Println("Day 06, Part 1: The sum of the answers is", part1(operands, operators))
	fmt.Println("Day 06, Part 2: The sum of the answers is", part2(operands2, operators2))
	
}