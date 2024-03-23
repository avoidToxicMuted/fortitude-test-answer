package asia.fourtitude.interviewq.jumble.core;

import java.io.*;
import java.util.*;
import java.util.List;
import asia.fourtitude.interviewq.jumble.exception.GoodQuestionException;

import asia.fourtitude.interviewq.jumble.util.Constant;


public class JumbleEngine {
    private Random random = new Random();

    //file reader
    private List<String> loadWordList(String filePath) {
        List<String> wordList = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String word;
            while ((word = reader.readLine()) != null) {
                wordList.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordList;
    }

    /**
     * From the input `word`, produces/generates a copy which has the same
     * letters, but in different ordering.
     *
     * Example: from "elephant" to "aeehlnpt".
     *
     * Evaluation/Grading:
     * a) pass unit test: JumbleEngineTest#scramble()
     * b) scrambled letters/output must not be the same as input
     *
     * @param word  The input word to scramble the letters.
     * @return  The scrambled output/letters.
     */
    public String scramble(String word) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        char[] wordArray = word.toCharArray();

        String scrambleWordResult =  this.shuffle(wordArray);
        while(scrambleWordResult.equals(word)) {
            scrambleWordResult = this.shuffle(wordArray);
        }
        return scrambleWordResult;
    }

    private String shuffle(char[] wordArray) {

        for (int i = wordArray.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = wordArray[i];
            wordArray[i] = wordArray[j];
            wordArray[j] = temp;
        }
        return new String(wordArray);
    }

    /**
     * Retrieves the palindrome words from the internal
     * word list/dictionary ("src/main/resources/words.txt").
     *
     * Word of single letter is not considered as valid palindrome word.
     *
     * Examples: "eye", "deed", "level".
     *
     * Evaluation/Grading:
     * a) able to access/use resource from classpath
     * b) using inbuilt Collections
     * c) using "try-with-resources" functionality/statement
     * d) pass unit test: JumbleEngineTest#palindrome()
     *
     * @return  The list of palindrome words found in system/engine.
     * @see //https://www.google.com/search?q=palindrome+meaning
     */
    public Collection<String> retrievePalindromeWords() {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        List<String> palindromes = new ArrayList<>();

        try (InputStream inputStream = getClass().getResourceAsStream("/words.txt")) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                String word;
                while ((word = reader.readLine()) != null) {
                    if (isPalindrome(word)) {
                        palindromes.add(word);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return palindromes;
    }

    private boolean isPalindrome(String word) {
        if (word.length() <= 1) {
            return false;
        }

        int left = 0;
        int right = word.length() - 1;

        while (left < right) {
            if (word.charAt(left) != word.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    /**
     * Picks one word randomly from internal word list.
     *
     * Evaluation/Grading:
     * a) pass unit test: JumbleEngineTest#randomWord()
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param length  The word picked, must of length.
     * @return  One of the word (randomly) from word list.
     *          Or null if none matching.
     */
    public String pickOneRandomWord(Integer length) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        List<String> wordList = this.loadWordList(Constant.FILE_PATH);
        Collections.shuffle(wordList);

        if(length == null) {
            return "";
        }

        for (String word : wordList) {
            if (word.length() == length) {
                return word;
            }
        }
        return null;
    }

    /**
     * Checks if the `word` exists in internal word list.
     * Matching is case insensitive.
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param word  The input word to check.
     * @return  true if `word` exists in internal word list.
     */
    public boolean exists(String word) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */

        if(word==null) {
            return false;
        }

        String lowercaseWord = word.toLowerCase();
        lowercaseWord = lowercaseWord.trim();

        List<String> wordList = this.loadWordList(Constant.FILE_PATH);
        for (String wordTemp : wordList) {
            if (wordTemp.equalsIgnoreCase(lowercaseWord)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds all the words from internal word list which begins with the
     * input `prefix`.
     * Matching is case insensitive.
     *
     * Invalid `prefix` (null, empty string, blank string, non letter) will
     * return empty list.
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param prefix  The prefix to match.
     * @return  The list of words matching the prefix.
     */
    public Collection<String> wordsMatchingPrefix(String prefix) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        List<String> wordList = this.loadWordList(Constant.FILE_PATH);
        List<String> wordsMatchingPrefixList = new ArrayList<>();

        if(prefix!= null && isValidPrefix(prefix.trim())) {
            String lowercasePrefix = prefix.toLowerCase();
            lowercasePrefix = lowercasePrefix.trim();
            for (String word : wordList) {
                if (word.toLowerCase().startsWith(lowercasePrefix)) {
                    wordsMatchingPrefixList.add(word);
                }
            }
        }
        return wordsMatchingPrefixList;
    }

    private boolean isValidPrefix(String prefix) {
        return !prefix.isEmpty() && prefix.matches("^[a-zA-Z]+$");
    }

    /**
     * Finds all the words from internal word list that is matching
     * the searching criteria.
     *
     * `startChar` and `endChar` must be 'a' to 'z' only. And case insensitive.
     * `length`, if have value, must be positive integer (>= 1).
     *
     * Words are filtered using `startChar` and `endChar` first.
     * Then apply `length` on the result, to produce the final output.
     *
     * Must have at least one valid value out of 3 inputs
     * (`startChar`, `endChar`, `length`) to proceed with searching.
     * Otherwise, return empty list.
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param startChar  The first character of the word to search for.
     * @param endChar    The last character of the word to match with.
     * @param length     The length of the word to match.
     * @return  The list of words matching the searching criteria.
     */
    public Collection<String> searchWords(Character startChar, Character endChar, Integer length) {
        if (startChar == null && endChar == null && length == null) {
            return new ArrayList<>();
        }

        List<String> wordList = loadWordList(Constant.FILE_PATH);
        List<String> matchingWords = new ArrayList<>();

        if (startChar == null) {
            startChar = '\0';
        }
        if (endChar == null) {
            endChar = '\0';
        }


        for (String word : wordList) {
            char firstChar = Character.toLowerCase(word.charAt(0));
            char lastChar = Character.toLowerCase(word.charAt(word.length() - 1));

            if ((startChar == '\0' || firstChar == Character.toLowerCase(startChar)) &&
                    (endChar == '\0' || lastChar == Character.toLowerCase(endChar)) &&
                    (length == null || word.length() == length)) {
                matchingWords.add(word);
            }
        }

        return matchingWords;
    }

    /**
     * Generates all possible combinations of smaller/sub words using the
     * letters from input word.
     *
     * The `minLength` set the minimum length of sub word that is considered
     * as acceptable word.
     *
     * If length of input `word` is less than `minLength`, then return empty list.
     *
     * Example: From "yellow" and `minLength` = 3, the output sub words:
     *     low, lowly, lye, ole, owe, owl, well, welly, woe, yell, yeow, yew, yowl
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param word       The input word to use as base/seed.
     * @param minLength  The minimum length (inclusive) of sub words.
     *                   Expects positive integer.
     *                   Default is 3.
     * @return  The list of sub words constructed from input `word`.
     */
    public Collection<String> generateSubWords(String word, Integer minLength) throws GoodQuestionException {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        throw new GoodQuestionException("Good question (•‿•)");
    }

    /**
     * Creates a game state with word to guess, scrambled letters, and
     * possible combinations of words.
     *
     * Word is of length 6 characters.
     * The minimum length of sub words is of length 3 characters.
     *
     * @param length     The length of selected word.
     *                   Expects >= 3.
     * @param minLength  The minimum length (inclusive) of sub words.
     *                   Expects positive integer.
     *                   Default is 3.
     * @return  The game state.
     */
    public GameState createGameState(Integer length, Integer minLength) throws GoodQuestionException {
        Objects.requireNonNull(length, "length must not be null");
        if (minLength == null) {
            minLength = 3;
        } else if (minLength <= 0) {
            throw new IllegalArgumentException("Invalid minLength=[" + minLength + "], expect positive integer");
        }
        if (length < 3) {
            throw new IllegalArgumentException("Invalid length=[" + length + "], expect greater than or equals 3");
        }
        if (minLength > length) {
            throw new IllegalArgumentException("Expect minLength=[" + minLength + "] greater than length=[" + length + "]");
        }
        String original = this.pickOneRandomWord(length);
        if (original == null) {
            throw new IllegalArgumentException("Cannot find valid word to create game state");
        }
        String scramble = this.scramble(original);
        Map<String, Boolean> subWords = new TreeMap<>();
        for (String subWord : this.generateSubWords(original, minLength)) {
            subWords.put(subWord, Boolean.FALSE);
        }
        return new GameState(original, scramble, subWords);
    }

}
