
use rayon::prelude::*;


fn num_children(start_time: i32, SIM_TIME: i32) -> u64 {
    let mut age = start_time;
    let mut direct_decendents = 0;
    while age <= SIM_TIME {
        direct_decendents += num_children(age + 9, SIM_TIME);
        age += 7;
    }

    return direct_decendents + 1;
}

fn run_sim(initial_state: &Vec<i32>, SIM_TIME: i32) -> u64 {
   let sum_fish: u64 = initial_state.par_iter()
        .map(|x| num_children(*x + 1, SIM_TIME))
        .sum();

    return sum_fish;
}

fn main() {
    let input = shared_lib::read_input("day06/src/input.txt");
    let initial_state: Vec<i32> = input[0].split(",").map(|s| s.parse::<i32>().unwrap()).collect();

    let part1_result = run_sim(&initial_state, 80);
    let part2_result = run_sim(&initial_state, 256);

    println!("Part 1: {}", part1_result);
    println!("Part 2: {}", part2_result);
}
