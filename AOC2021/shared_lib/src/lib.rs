use std::str;
use std::fs::File;
use std::io::{BufRead, BufReader};


pub fn read_input(path: &str) -> Vec<String> {
    let mut input = Vec::new();

    if let Ok(file) = File::open(path) {
        let reader = BufReader::new(file);
        for line in reader.lines() {
            if let Ok(line) = line {
                input.push(line);
            }
        }
    }

    return input
}
