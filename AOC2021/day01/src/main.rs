
fn part1(measurements: &Vec<i32>) {
    let mut count = 0; 

    for i in 0..measurements.len()-1 {
        if measurements[i] < measurements[i+1] {
            count += 1;
        }
    }

    println!("Part 1: {}", count);
}


fn part2(measurements: &Vec<i32>) {
    let mut count = 0; 

    for i in 0..measurements.len()-3 {
        let sum1 = measurements[i] + measurements[i+1] + measurements[i+2];
        let sum2 = measurements[i+1] + measurements[i+2] + measurements[i+3];

        if sum1 < sum2 {
            count += 1;
        }
    }

    println!("Part 2: {}", count);
}



fn main() {
    let input = shared_lib::read_input("day01/src/input.txt");
    let measurements: Vec<i32> = input.iter().filter_map(|s| s.parse().ok()).collect();
    //println!("{:?}", measurements);

    part1(&measurements);
    part2(&measurements);
}
