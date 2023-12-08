import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class day07 {
    public static void main(String[] args) {
        ArrayList<String> input = Functions.readInputFile("AOC2023/input/day07_input.txt");

        System.out.println("Part 1: " + part1(input));
        System.out.println("Part 2: " + part2(input));

    }


    static int part1(ArrayList<String> input) {
        ArrayList<Hand> hands = Hand.parseHands(input);

        Collections.sort(hands, new SortHands());

        int totalWinnings = 0;
        for (int i = hands.size(); i > 0; i--) {
            totalWinnings += hands.get(i - 1).bid * i;
        }

        return totalWinnings;
    }

    static int part2(ArrayList<String> input) {
        ArrayList<Hand> hands = Hand.parseHands(input);
        
        Collections.sort(hands, new SortHands2());

        int totalWinnings = 0;
        for (int i = hands.size(); i > 0; i--) {
            totalWinnings += hands.get(i - 1).bid * i;
        }

        return totalWinnings;

    }
}

class Hand {
    ArrayList<Character> cards = new ArrayList<>();
    int bid;
    int type;
    
    public enum Type {
        HIGH_CARD, 
        ONE_PAIR, 
        TWO_PAIR, 
        THREE_OF_A_KIND, 
        FULL_HOUSE, 
        FOUR_OF_A_KIND, 
        FIVE_OF_A_KIND
    }
    static HashMap<Character, Integer> cardRanks1 = new HashMap<>();
    static {
        cardRanks1.put('2', 2);
        cardRanks1.put('3', 3);
        cardRanks1.put('4', 4);
        cardRanks1.put('5', 5);
        cardRanks1.put('6', 6);
        cardRanks1.put('7', 7);
        cardRanks1.put('8', 8);
        cardRanks1.put('9', 9);
        cardRanks1.put('T', 10);
        cardRanks1.put('J', 11);
        cardRanks1.put('Q', 12);
        cardRanks1.put('K', 13);
        cardRanks1.put('A', 14);
    }   
    static HashMap<Character, Integer> cardRanks2 = new HashMap<>();
    static {
        cardRanks2.put('J', 1);
        cardRanks2.put('2', 2);
        cardRanks2.put('3', 3);
        cardRanks2.put('4', 4);
        cardRanks2.put('5', 5);
        cardRanks2.put('6', 6);
        cardRanks2.put('7', 7);
        cardRanks2.put('8', 8);
        cardRanks2.put('9', 9);
        cardRanks2.put('T', 10);
        cardRanks2.put('Q', 11);
        cardRanks2.put('K', 12);
        cardRanks2.put('A', 13);
    }



    public Hand(ArrayList<Character> cards, int bid) {
        this.cards = cards;
        this.bid = bid;
    }


    static ArrayList<Hand> parseHands(ArrayList<String> input) {
        ArrayList<Hand> hands = new ArrayList<>();

        for (String line : input) {
            String[] split = line.split(" ");

            int bid = Integer.parseInt(split[1]);
            ArrayList<Character> cards = new ArrayList<>();

            for (int j = 0; j < 5; j++) {
                cards.add(split[0].charAt(j));
            }
            hands.add(new Hand(cards, bid));
        }
        return hands;
    }

    static Type getType(Hand hand) {
        ArrayList<Character> sortedHand = new ArrayList<>(hand.cards);
        Collections.sort(sortedHand);

        ArrayList<Integer> matching = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int numMatching = 0;
            while (i < 4 && sortedHand.get(i) == sortedHand.get(i + 1)) {
                numMatching++;
                i++;
            }
            matching.add(numMatching + 1);
        }

        switch (matching.size()) {
            case 1:
                return Type.FIVE_OF_A_KIND;
            case 2:
                if (matching.get(0) == 3 || matching.get(1) == 3) {
                    return Type.FULL_HOUSE;
                } else {
                    return Type.FOUR_OF_A_KIND;
                }
            case 3:
                if (matching.get(0) == 3 || matching.get(1) == 3 || matching.get(2) == 3) {
                    return Type.THREE_OF_A_KIND;
                } else {
                    return Type.TWO_PAIR;
                }
            case 4:
                return Type.ONE_PAIR;
            case 5:
                return Type.HIGH_CARD;
            default:
                return null;
        }
    }

    /* 
     * @return 1 if card1 is better, -1 if card2 is better, 0 if equal
     */
    static int compareCards(char card1, char card2, HashMap<Character, Integer> cardRanks) {
        int rank1 = cardRanks.get(card1);
        int rank2 = cardRanks.get(card2);
    
        if (rank1 > rank2) {
            return 1;
        } else if (rank1 < rank2) {
            return -1;
        } else {
            return 0;
        }
    }
}


class SortHands implements Comparator<Hand> {
    @Override
    public int compare(Hand hand1, Hand hand2) {
        return compareHands(hand1, hand2);
    }

    /* 
     * @return 1 if hand1 is better, -1 if hand2 is better, 0 if equal
     */
    static int compareHands(Hand hand1, Hand hand2) {
        Hand.Type type1 = Hand.getType(hand1);
        Hand.Type type2 = Hand.getType(hand2);

        if (type1.ordinal() > type2.ordinal()) {
            return 1;
        } 
        else if (type1.ordinal() < type2.ordinal()) {
            return -1;
        } 
        else {
            for (int i = 0; i < 5; i++) {
                int comp = Hand.compareCards(hand1.cards.get(i), hand2.cards.get(i), Hand.cardRanks1);
                if (comp != 0) {
                    return comp;
                }
            }
            return 0;
        }
    }
}


class SortHands2 implements Comparator<Hand> {
    @Override
    public int compare(Hand hand1, Hand hand2) {
        return compareHands(hand1, hand2);
    }

    /* 
     * @return 1 if hand1 is better, -1 if hand2 is better, 0 if equal
     */
    static int compareHands(Hand hand1, Hand hand2) {
        Hand.Type type1 = Hand.getType(hand1);
        Hand.Type type2 = Hand.getType(hand2);

        type1 = includeJoker(hand1, type1);
        type2 = includeJoker(hand2, type2);

        if (type1.ordinal() > type2.ordinal()) {
            return 1;
        } 
        else if (type1.ordinal() < type2.ordinal()) {
            return -1;
        } 
        else {
            for (int i = 0; i < 5; i++) {
                int comp = Hand.compareCards(hand1.cards.get(i), hand2.cards.get(i), Hand.cardRanks2);
                if (comp != 0) {
                    return comp;
                }
            }
            return 0;
        }
    }

    
    static Hand.Type includeJoker(Hand hand, Hand.Type type) {
        int numJokers = 0;
        for (char card : hand.cards) {
            if (card == 'J') {
                numJokers++;
            }
        }
        switch (numJokers) {
            case 0:
                return type;
            case 1:
                if (type == Hand.Type.HIGH_CARD || type == Hand.Type.FOUR_OF_A_KIND) {
                    return Hand.Type.values()[type.ordinal() + 1];
                } 
                else if (type == Hand.Type.ONE_PAIR || type == Hand.Type.TWO_PAIR || type == Hand.Type.THREE_OF_A_KIND) {
                    return Hand.Type.values()[type.ordinal() + 2];
                }
            case 2:
                if (type == Hand.Type.ONE_PAIR) {
                    return Hand.Type.THREE_OF_A_KIND;
                }
                else if (type == Hand.Type.TWO_PAIR) {
                    return Hand.Type.FOUR_OF_A_KIND;
                }
                else if (type == Hand.Type.THREE_OF_A_KIND && type == Hand.Type.FULL_HOUSE) {
                    return Hand.Type.FIVE_OF_A_KIND;
                }
            case 3:
                if (type == Hand.Type.THREE_OF_A_KIND) {
                    return Hand.Type.FOUR_OF_A_KIND;
                }
                else if (type == Hand.Type.FULL_HOUSE) {
                    return Hand.Type.FIVE_OF_A_KIND;
                }
            case 4:
                return Hand.Type.FIVE_OF_A_KIND;
            case 5:
                return Hand.Type.FIVE_OF_A_KIND;    
            default:
                return null;          
        }
    }
}
