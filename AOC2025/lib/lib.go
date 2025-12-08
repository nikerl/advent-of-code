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

// Splits a string into substrings separated by the given delimiter rune.
//
// Ignores consecutive delimiters and does not include empty substrings in the result.
func SplitString(str string, delimiter rune) []string {
	var result []string
	var stringRune []rune = []rune(str)
	for i := 0; i < len(str); i++ {
		if stringRune[i] != delimiter {
			var substr []rune = []rune{}
			for i < len(str) && stringRune[i] != delimiter {
				substr = append(substr, stringRune[i])
				i++
			}
			result = append(result, string(substr))
		}
	}
	return result
}

// Transposes a 2D matrix.
//
// Accepts matrixes with elements of any type.
func TransposeMatrix[T any](matrix [][]T) [][]T {
    rows := len(matrix)
    cols := len(matrix[0])
    result := make([][]T, cols)

    for i := range result {
        result[i] = make([]T, rows)
    }

    for i := 0; i < rows; i++ {
        for j := 0; j < cols; j++ {
            result[j][i] = matrix[i][j]
        }
    }

    return result
}

