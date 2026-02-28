use std::process;

#[derive(PartialEq, Eq)]
enum Direction {
    Forward,
    Down,
    Up,
}


fn part1(measurements: &Vec<(Direction, i32)>) {
    let mut h_pos = 0;
    let mut d_pos = 0;

    for measurement in measurements {
        if measurement.0 == Direction::Forward {
            h_pos += measurement.1;
        }
        else if measurement.0 == Direction::Down {
            d_pos += measurement.1;
        }
        else if measurement.0 == Direction::Up {
            d_pos -= measurement.1;
        }
    }

    let hd_product = h_pos * d_pos;
    
    println!("Part 1: {}", hd_product);
}


fn part2(measurements: &Vec<(Direction, i32)>) {
    let mut aim = 0;
    let mut h_pos = 0;
    let mut d_pos = 0;

    for measurement in measurements {
        if measurement.0 == Direction::Forward {
            h_pos += measurement.1;
            d_pos += aim * measurement.1;
        }
        else if measurement.0 == Direction::Down {
            aim += measurement.1;
        }
        else if measurement.0 == Direction::Up {
            aim -= measurement.1;
        }
    }

    let hd_product = h_pos * d_pos;
    
    println!("Part 2: {}", hd_product);
}



fn main() {
    let input = shared_lib::read_input("day02/src/input.txt");
    let mut measurements: Vec<(Direction, i32)> = Vec::new();
    for line in input {
        let split_ln: Vec<&str> = line.split(' ').collect();
        let distance = split_ln[1].parse::<i32>().unwrap();
        let dir: Direction;
        if split_ln[0] == "forward" {dir = Direction::Forward}
        else if split_ln[0] == "down" {dir = Direction::Down}
        else if split_ln[0] == "up" {dir = Direction::Up}
        else {process::exit(1)}

        measurements.push((dir, distance))
    }

    part1(&measurements);
    part2(&measurements);
}
