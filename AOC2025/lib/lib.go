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
