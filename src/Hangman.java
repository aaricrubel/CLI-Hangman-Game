import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    public static void main(String[] args) throws FileNotFoundException {
        hangman();
    }

    public static void hangman() throws FileNotFoundException {
        Words words = new Words();
        String secret_word = words.chooseWord();
        /* Steps to start Hangman:
        * In the beginning of the game user will know about the total characters in the secret_word    
        * In each round user will guess one character 
        * After each character give feedback to the user
        * right or wrong
        * Display partial word guessed by the user and use underscore in place of not guess word 
        */
        int remaining_lives = 8;
        System.out.println("Welcome to the game, Hangman!");
        System.out.println("I am thinking of a word that is " + secret_word.length() + "letters long. \n");
        List<Character> letters_guessed = new ArrayList<>();
        String available_letters = get_available_letters(letters_guessed);
        System.out.println("Available Letters: " + available_letters);
        Scanner sc = new Scanner(System.in);
        int hintCount = 0;
        while(remaining_lives>0 && !is_word_guessed(secret_word, letters_guessed)) {
            System.out.println("Please guess a letter: ");
            char guess = sc.nextLine().charAt(0);
            char letter = Character.toLowerCase(guess);
            if(guess  == '?') {
                hintCount++;
            }
            if (ifValid(letter)) {
                if(secret_word.contains(String.valueOf(letter))) {
                    letters_guessed.add(letter);
                    System.out.println("Good Guess: "+ get_guessed_word(secret_word, letters_guessed) + " ");
                    if(is_word_guessed(secret_word, letters_guessed)){
                        System.out.println("**** Congratulations, you won! **** \n\n");
                    }
                }else if(guess == '?' && hintCount<2) {
                    System.out.println("Your Hint is : " + giveHint(secret_word, letters_guessed));
                } else {
                    System.out.println("OOPS! That letter is not in your word: " + get_guessed_word(secret_word, letters_guessed));
                    System.out.println("Your remaining lives are: " + remaining_lives);
                    System.out.println("Type '?' for hint");
                    letters_guessed.add(letter);
                    System.out.println();
                    Images img = new Images();
                    int index = 8-remaining_lives;
                    System.out.println(img.images[index]);
                    remaining_lives--;
                }
            } else {
                System.out.println("Oops you entered a wrong character, please try again!");
            }
            
        }
        if(remaining_lives==0) {
            System.out.println("YOU LOSE");
            System.out.println("The Word was: " +secret_word);
        }
        sc.close();
    }

    public static boolean ifValid(char c) {
        if ((c >= 'a' && c <= 'z') || (c=='?')) {
            return true;
        }
        return false;
    }

    public static char giveHint(String secret_word, List<Character> letters_guessed) {
        Random rand = new Random();
        String available = secret_word;
        for(char c : letters_guessed) {
            available = available.replace(String.valueOf(c),"");
        }
        char[] c = available.toCharArray();
        char ch = c[rand.nextInt(c.length)];
        return ch;
    }
    public static String get_guessed_word(String secret_word, List<Character> letters_guessed) {
        /* secret_word: word guess by the user
        letters_guessed: list hold all the word guess by the user
        returns: 
        return string which contain all the right guessed characters
        Example :- 
        if secret_word -> "kindness" and letters_guessed = [k, n, s]
        return "k_n_n_ss"
        */
        int index = 0;
        String guessed_word = "";
        while (index < secret_word.length()) {
            if(letters_guessed.contains(secret_word.charAt(index))) {
                guessed_word += secret_word.charAt(index);
            }
            else {
                guessed_word += "_";
            }
            index++;
        }
            
        return guessed_word;
    }

    public static boolean is_word_guessed(String secret_word, List<Character> letters_guessed) {
        /*
            secret_word: word to be guessed by the user
            letters_guessed: list hold all the word guess by the user
            returns: 
                return True (if user guess the word correctly )
                return False (wrong selection)
        */
        char[] charArray = secret_word.toCharArray();
        for (char c : charArray) {
            if(!letters_guessed.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public static String get_available_letters(List<Character> letters_guessed) {
        /*
        letters_guessed: list contains all guessed characters
        returns: 
        it return string which contains all characters except guessed letters
        Example :-
        letters_guessed = ['e', 'a'] then    
        return sting is -> `bcdfghijklmnopqrstuvwxyz`
        */
        String letters_left = "abcdefghijklmnopqrstuvwxyz";
        for(char c : letters_guessed) {
            letters_left = letters_left.replace(String.valueOf(c),"");
        }
        return letters_left;
    }
}