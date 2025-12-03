package lib

import (
	"bufio"
	"os"
)

func ReadInput(path string) []string {
	file, ferr := os.Open(path)

	if ferr != nil {
		panic(ferr)
	}

	scanner := bufio.NewScanner(file)
	var lines []string

	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}

	return lines
}

func Pow(base int, exp int) int {
	result := 1
	for i := 0; i < exp; i++ {
		result *= base
	}
	return result
}

func Log10(n int) int {
	var log int = 0
	for n >= 10 {
		n = n / 10
		log++
	}
	return log
}
