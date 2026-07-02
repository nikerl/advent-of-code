use std::collections::HashMap;

struct Coord {
	x: i32,
	y: i32,
}

fn parse_input(input: &Vec<String>) -> (Vec<HashMap<i32, Coord>>, Vec<i32>) {
	let bingo_numbers: Vec<i32> = input[0].split(",").map(|s| s.parse::<i32>().unwrap()).collect();
	let mut bingo_cards: Vec<HashMap<i32, Coord>> = Vec::new();

	let mut row_index = 0;
	let mut current_card: HashMap<i32, Coord> = HashMap::new();
	for i in 2..input.len() {
		if input[i].trim().is_empty() {
			bingo_cards.push(current_card);
			current_card = HashMap::new();
			row_index = 0;
			continue;
		}

		let row: Vec<i32> = input[i].split_whitespace().map(|s| s.parse::<i32>().unwrap()).collect();
		for i in 0..row.len() {
			current_card.insert(row[i], Coord{x: i as i32, y: row_index});
		}
		row_index += 1;
	}

	if !current_card.is_empty() {
		bingo_cards.push(current_card);
	}

	(bingo_cards, bingo_numbers)
}


fn count_winning_points(bingo_card: &HashMap<i32, Coord>, check_card: &[[bool; 5]; 5], bingo_num: i32) -> i32 {
	let mut sum = 0;
	for (val, coord) in bingo_card {
		if !check_card[coord.y as usize][coord.x as usize] {
			sum += val;
		}
	}

	let total = sum * bingo_num;
	return total;
}

fn part1(bingo_cards: &Vec<HashMap<i32, Coord>>, mut check_cards: [[[bool; 5]; 5]; 120], bingo_numbers: &Vec<i32>) -> i32 {
	for &num in bingo_numbers {
		for card_index in 0..bingo_cards.len() {
			if let Some(coord) = bingo_cards[card_index].get(&num){
				check_cards[card_index][coord.y as usize][coord.x as usize] = true;
				
				// Check if the current row or collumn as won
				if check_cards[card_index][coord.y as usize].iter().all(|&x| x) || (0..5).all(|row| check_cards[card_index][row][coord.x as usize]) {
					let points = count_winning_points(&bingo_cards[card_index], &check_cards[card_index], num);
					return points;
				}
			}
		}
	}

	return -1
}

fn part2(bingo_cards: &Vec<HashMap<i32, Coord>>, mut check_cards: [[[bool; 5]; 5]; 120], bingo_numbers: &Vec<i32>) -> i32 {
	let mut last_winner = -1;
	let mut winners: Vec<i32> = Vec::new();
	for &num in bingo_numbers {
		for card_index in 0..bingo_cards.len() {
			if winners.contains(&(card_index as i32)) {
				continue;
			} 
			if let Some(coord) = bingo_cards[card_index].get(&num){
				check_cards[card_index][coord.y as usize][coord.x as usize] = true;
				
				// Check if the current row or collumn as won
				if check_cards[card_index][coord.y as usize].iter().all(|&x| x) || (0..5).all(|row| check_cards[card_index][row][coord.x as usize]) {
					let points = count_winning_points(&bingo_cards[card_index], &check_cards[card_index], num);
					last_winner = points;
					winners.push(card_index as i32);
				}
			}
		}
	}

	return last_winner
}

fn main() {
	let input: Vec<String> = shared_lib::read_input("day04/src/input.txt");
	let (bingo_cards, bingo_numbers) = parse_input(&input);
	let check_cards: [[[bool; 5]; 5]; 120] = [[[false; 5]; 5]; 120];

	let part1_points = part1(&bingo_cards, check_cards, &bingo_numbers);
	let part2_points = part2(&bingo_cards, check_cards, &bingo_numbers);

	println!("Part 1: {}", part1_points);
	println!("Part 2: {}", part2_points);
}
