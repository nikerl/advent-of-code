use std::collections::{HashMap, HashSet};

#[derive(Hash, Eq, PartialEq, Clone)]
struct Coord {
    x: i32,
    y: i32
}

struct Line {
    c1: Coord,
    c2: Coord,
}


fn parse_lines(input: Vec<String>) -> Vec<Line> {
    let mut lines: Vec<Line> = Vec::new();

    for str in input {
        let coords: Vec<&str> = str.split(" -> ").collect();
        let coord1: Vec<i32> = coords[0].split(",").map(|s| s.parse::<i32>().unwrap()).collect();
        let coord2: Vec<i32> = coords[1].split(",").map(|s| s.parse::<i32>().unwrap()).collect();
        lines.push(Line{c1: Coord{x: coord1[0], y: coord1[1]}, c2: Coord{x: coord2[0], y: coord2[1]}});
    }

    return lines;
}

fn part1(lines: &Vec<Line>) -> i32 {
    let mut vent_points: HashSet<Coord> = HashSet::new();
    let mut dangerous_area: HashMap<Coord, i32> = HashMap::new();

    for line in lines {
        let dx = line.c2.x - line.c1.x;
        let dy = line.c2.y - line.c1.y;
        if dx != 0 && dy != 0 { // part 1: skip diagonal lines
            continue;
        }
        let dist = dx.abs().max(dy.abs());
        let x_dir = dx.signum();
        let y_dir = dy.signum();

        for i in 0..=dist {
            let coordinate = Coord{x: line.c1.x + i*x_dir, y: line.c1.y + i*y_dir};
            if !vent_points.insert(coordinate.clone()) {
                dangerous_area.entry(coordinate.clone()).and_modify(|v| *v += 1).or_insert(2);
            }
        }
    }

    return dangerous_area.len() as i32;
}

fn part2(lines: &Vec<Line>) -> i32 {
    let mut vent_points: HashSet<Coord> = HashSet::new();
    let mut dangerous_area: HashMap<Coord, i32> = HashMap::new();

    for line in lines {
        let dx = line.c2.x - line.c1.x;
        let dy = line.c2.y - line.c1.y;

        let dist = dx.abs().max(dy.abs());
        let x_dir = dx.signum();
        let y_dir = dy.signum();

        for i in 0..=dist {
            let coordinate = Coord{x: line.c1.x + i*x_dir, y: line.c1.y + i*y_dir};
            if !vent_points.insert(coordinate.clone()) {
                dangerous_area.entry(coordinate.clone()).and_modify(|v| *v += 1).or_insert(2);
            }
        }
    }

    return dangerous_area.len() as i32;
}

fn main() {
    let input = shared_lib::read_input("day05/src/input.txt");
    let lines: Vec<Line> = parse_lines(input);

    let part1_sum = part1(&lines);
    let part2_sum = part2(&lines);

    println!("Part 1: {}", part1_sum);
    println!("Part 2: {}", part2_sum);
}
