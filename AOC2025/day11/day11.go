package main

import (
	"AOC2025/lib"
	"fmt"
)

type node struct {
	name string
	parents []*node
	children []*node
}



func dfs(src *node, dest *node, path *map[string]*node, numPaths *int, required []string, visited *map[string]bool) {
    (*path)[src.name] = src

    if src == dest {
        // Check if all required nodes were visited
        allFound := true
        for _, req := range required {
            if _, ok := (*path)[req]; !ok {
                allFound = false
                break
            }
        }
        if allFound {
            (*numPaths)++
        }
    } else {
        for _, child := range src.children {
            if _, ok := (*path)[child.name]; !ok {
                dfs(child, dest, path, numPaths, required, visited)
            }
        }
    }

    delete(*path, src.name)
}

func part1(nodesMap map[string]*node) int {
	var numPaths int = 0
    var path *map[string]*node = &map[string]*node{}
    var visited *map[string]bool = &map[string]bool{}
	required := []string{}

	dfs(nodesMap["you"], nodesMap["out"], path, &numPaths, required, visited)

	return numPaths
}


func parseMap(input []string) map[string]*node {
	var nodesMap map[string]*node = make(map[string]*node)
	for _, line := range input {
		var parts []string = lib.SplitString(line, ':')
		var nodeName string = parts[0]
		var childNames []string = lib.SplitString(parts[1], ' ')

		var currentNode *node
		if val, exists := nodesMap[nodeName]; exists {
			currentNode = val
		} else {
			currentNode = &node{name: nodeName}
			nodesMap[nodeName] = currentNode
		}
		for _, childName := range childNames {
			var childNode *node
			if val, exists := nodesMap[childName]; exists {
				childNode = val
			} else {
				childNode = &node{name: childName}
				nodesMap[childName] = childNode
			}
			childNode.parents = append(childNode.parents, currentNode)
			currentNode.children = append(currentNode.children, childNode)
		}
	}
	return nodesMap
}

func main() {
	//var example1 []string = lib.ReadInput("day11/example1.txt")
	var input []string = lib.ReadInput("day11/input.txt")

	//var nodesMapEx1 map[string]*node = parseMap(example1)
	var nodesMap map[string]*node = parseMap(input)

	fmt.Println("Day 11, Part 1: The number of distinct paths from 'you' to 'out' is:", part1(nodesMap))
}