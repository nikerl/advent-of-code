fn build_bin_num(binary_number: Vec<u32>) -> u32 {
    let num_digits = binary_number.len() as i32;
    let mut result: u32 = 0;

    for i in 0..num_digits {
        result += binary_number[i as usize] << (num_digits - i - 1)
    }

    return result;
}


fn find_rating(diag_report: &Vec<Vec<u32>>, rating_idx: i32) -> Vec<u32> {
    let mut rating = diag_report.clone();
    for j in 0..rating[0].len() {
        if rating.len() == 1 { break; }

        let mut temp_zero: Vec<Vec<u32>> = Vec::new();
        let mut temp_one: Vec<Vec<u32>> = Vec::new();

        for i in 0..rating.len() {
            match rating[i][j] {
                0 => temp_zero.push(rating[i].clone()),
                1 => temp_one.push(rating[i].clone()),
                _ => panic!("Unexpected value in diagnostic report"),
            }
        }

        if rating_idx == 0 {
            if temp_zero.len() > temp_one.len() { rating = temp_zero }
            else { rating = temp_one }
        }
        else {
            if temp_one.len() < temp_zero.len() { rating = temp_one }
            else { rating = temp_zero }
        }
    }

    return rating[0].clone();
}



fn part1(diag_report: &Vec<Vec<u32>>) {
    let mut gamma: Vec<u32> = Vec::new();
    let mut epsilon: Vec<u32> = Vec::new();

    for j in 0..diag_report[0].len() {
        let mut num_zero = 0;
        let mut num_one = 0;
        for i in 0..diag_report.len() {
            match diag_report[i][j] {
                0 => num_zero += 1,
                1 => num_one += 1,
                _ => panic!("Unexpected value in diagnostic report"),
            }
        }
        gamma.push(if num_zero > num_one { 0 } else { 1 });
        epsilon.push(if num_zero < num_one { 0 } else { 1 });
    }

    let gamma_num = build_bin_num(gamma);
    let epsilon_num = build_bin_num(epsilon);

    println!("Part 1: Gamma: {}, Epsilon: {}, Product: {}", gamma_num, epsilon_num, gamma_num * epsilon_num);
}


fn part2(diag_report: &Vec<Vec<u32>>) {
    let oxygen_rating = find_rating(diag_report, 0);
    let co2_rating = find_rating(diag_report, 1);

    let oxygen_num = build_bin_num(oxygen_rating);
    let co2_num = build_bin_num(co2_rating);

    println!("Part 1: Oxygen: {}, CO2: {}, Product: {}", oxygen_num, co2_num, oxygen_num * co2_num);
}



fn main() {
    let input = shared_lib::read_input("day03/src/input.txt");
    let mut diag_report: Vec<Vec<u32>> = Vec::new();

    for line in input {
        let bit_array: Vec<u32> = line.chars().filter_map(|s| s.to_digit(10)).collect();
        diag_report.push(bit_array);
    }

    part1(&diag_report);
    part2(&diag_report);
}
