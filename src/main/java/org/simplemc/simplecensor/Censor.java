package org.simplemc.simplecensor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;

/**
 * A simple word censor
 * 
 * @author taylorjb
 * 
 */
public class Censor
{
    private Map<String, String> words; // words mapped to replacements

    /**
     * Initialize the censor
     * 
     * @param words
     *            words to censor
     */
    public Censor(char censorChar, List<String> words)
    {
        // fill in replacement map
        this.words = new HashMap<String, String>();
        
        for (String word : words)
        {
            // array of censor chars to the length of word being censored
            char[] replace = new char[word.length()];
            Arrays.fill(replace, censorChar);
            this.words.put(word, String.valueOf(replace));
        }
    }

    /**
     * Censor things contained in words from a message
     * 
     * @param toCensor
     *            message to censor
     * @return censored toCensor
     */
    public String censorMessage(String toCensor)
    {
        Queue<Integer> nonAlphaNumIndexes = new LinkedList<Integer>(); // queue saving indexes of non-alphanumeric characters
        Queue<Character> nonAlphaNumChars = new LinkedList<Character>(); // queue saving non-alphanumeric characters(parallel to nonAlphNumIndexes)

        // strip non alpha-numerics, save them to put back later
        int i = 0;
        for (char c : toCensor.toCharArray())
        {
            if (!(Character.isDigit(c) || Character.isLetter(c)))
            {
                nonAlphaNumIndexes.add(toCensor.indexOf(c, i));
                nonAlphaNumChars.add(c);
            }
            i++;
        }
        toCensor = toCensor.replaceAll("[^a-zA-Z0-9]", "");

        // loop through words to censor looking for occurrences and censor them
        for (Map.Entry<String, String> word : words.entrySet())
        {
            // replace all occurrences ignoring case
            toCensor = toCensor.replaceAll("(?i)" + word.getKey(), word.getValue());
        }

        // write back all non-alphanumeric chars to the string
        StringBuilder censored = new StringBuilder(toCensor);
        while (!(nonAlphaNumIndexes.isEmpty() || nonAlphaNumChars.isEmpty()))
        {
            int index = nonAlphaNumIndexes.remove();
            
            // if we're adding to the end(off the string), append it instead of insert
            if (censored.length() <= index)
                censored.append(nonAlphaNumChars.remove());
            else
                censored.insert(index, nonAlphaNumChars.remove());
        }
        
        return censored.toString();
    }
}
