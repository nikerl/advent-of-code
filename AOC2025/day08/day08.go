package main

import (
	"AOC2025/lib"
	"fmt"
	"math"
	"sort"
)


type vertex struct {
	x int
	y int
	z int
}
type edge struct {
	from int
	to   int
	dist float64
}



func find(parent []int, i int) int {
	if parent[i] == i {
		return i
	} else {
		return find(parent, parent[i])
	}
}
func unite(u int, v int, parent []int, rank []int) {
    u = find(parent, u)
    v = find(parent, v)

    if rank[u] < rank[v] {
        parent[u] = v
    } else if rank[u] > rank[v] {
        parent[v] = u
    } else {
        parent[v] = u
        rank[u]++
    }
}
func kruskalsAlgorithm(edges []edge, junctions []vertex, numConnections int) ([]int, edge) {
	var n int = len(junctions)
    var parent []int = make([]int, n)
    var rank []int = make([]int, n)
    for i := 0; i < n; i++ {
        parent[i] = i
        rank[i] = 0
    }
    
    var minCost float64 = 0
    var lastEdge edge = edge{}
    var processed int = 0
    for i := 0; processed < numConnections-1 && i < len(edges); i++ {
        var edge edge = edges[i]

        // Find the sets of the vertices of the edge
        var v1 int = find(parent, edge.from)
        var v2 int = find(parent, edge.to)
        var dist float64 = edge.dist

        // If v1 and v2 are not in the same set, unite them
        if v1 != v2 {
            unite(v1, v2, parent, rank)
            minCost += dist
            lastEdge = edge
        }
		processed++
    }

	return parent, lastEdge
}
func calculateNetworkSizes(parent []int, junctions []vertex, numConnections int) map[int]int {
    networkSize := make(map[int]int)
    for i := 0; i < len(junctions); i++ {
        root := find(parent, i)
        networkSize[root]++
    }
    fmt.Println("Number of networks with ", numConnections-1, " connections: ", len(networkSize))
    return networkSize
}


func part1(edges []edge, junctions []vertex, numConnections int) int {
	parent, _ := kruskalsAlgorithm(edges, junctions, numConnections)

    // Count the size of each network
    networkSize := calculateNetworkSizes(parent, junctions, numConnections)

    // Find the largest three networks
    var sizes []int
    for _, size := range networkSize {
        sizes = append(sizes, size)
    }
    sort.Sort(sort.Reverse(sort.IntSlice(sizes)))

    // Multiply the three largest
    if len(sizes) >= 3 {
        return sizes[0] * sizes[1] * sizes[2]
    }

    return -1
} 


func part2(edges []edge, junctions []vertex) int {
    var numConnections int = len(junctions)
    var lastEdge edge

    // Do unbounded binary search to find an upper bound
    for {
        parent, _ := kruskalsAlgorithm(edges, junctions, numConnections)
        
        // Count the size of each network
        if len(calculateNetworkSizes(parent, junctions, numConnections)) == 1 {
            break
        }
        numConnections *= 2
    }
    // Binary search to find the exact number of connections needed
    var low int = numConnections / 2
    var high int = numConnections
    for {
        mid := (low + high) / 2
        parent, edge := kruskalsAlgorithm(edges, junctions, mid)

        // Count the size of each network
        if len(calculateNetworkSizes(parent, junctions, mid)) == 1 {
            lastEdge = edge
            high = mid
        } else {
            low = mid + 1
        } 
        if low >= high {
            break
        }
    }
    var prodXCoord int = junctions[lastEdge.from].x * junctions[lastEdge.to].x

    return prodXCoord
}


func generateEdgeList(junctions []vertex) []edge {
    edges := make([]edge, 0)

    for i := 0; i < len(junctions); i++ {
        for j := i + 1; j < len(junctions); j++ {
            var dist float64 = math.Sqrt(math.Pow(float64(junctions[i].x-junctions[j].x), 2) + math.Pow(float64(junctions[i].y-junctions[j].y), 2) + math.Pow(float64(junctions[i].z-junctions[j].z), 2))
            edges = append(edges, edge{from: i, to: j, dist: dist})
        }
    }
    // Sort edges by distance, ascending
    sort.Slice(edges, func(i, j int) bool {
        return edges[i].dist < edges[j].dist
    })

    return edges
}

func parseJunctions(input []string) []vertex {
	var junctions []vertex
	for i := 0; i < len(input); i++ {
		var junction vertex = vertex{}
		fmt.Sscanf(input[i], "%d,%d,%d", &junction.x, &junction.y, &junction.z)
		junctions = append(junctions, junction)
	}
	return junctions
}


func main() {
	var input []string = lib.ReadInput("day08/input.txt")
	var junctions []vertex = parseJunctions(input)
	var edges []edge = generateEdgeList(junctions)

	fmt.Println("Day 8, Part 1: The product of the 3 largest networks is: ", part1(edges, junctions, 1000))
    fmt.Println("Day 8, Part 1: The network is fully connected at: ", part2(edges, junctions))
}
