import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * (C) 2016 Campus Language Connection. All rights reserved.
 * Created by Christian Amstutz for Campus Language Connection. To be used only by authorized people for authorized purposes.
 */
public class PeopleUtility {


    public static HashMap<Person, LinkedList<Person>> getMatches(List<Person> people) throws Exception
    {
        //Set up output data storage
        HashMap<Person, LinkedList<Person>> output = new HashMap<Person, LinkedList<Person>>();

        //For each person, find the top 4 scorers
        for(Person p: people)
        {
            //Create a list with everyone other than CLC.LanguageMatcher.camstutz.Person p
            List<Person> peopleTemp = new LinkedList<Person>(people);
            peopleTemp.remove(p);

            //Shuffle peopleTemp, in order to randomize the way people come up
            Collections.shuffle(peopleTemp);

            //Get scores for everyone, compared to CLC.LanguageMatcher.camstutz.Person p
            HashMap<Person, Integer> tempMap = getAllScores(p, peopleTemp);

            //Now, we need to find those with the highest

            //Create data storage for the 4 highest
            LinkedList<Person> highestScorers = new LinkedList<Person>();

            //Find the 4 highest - if person doesn't speak mandarin or want to learn mandarin
            int numMatchesToFind = 4;

            //Used for debugging
            //System.out.println(p);

            if(p.getKnownLanguages().contains("Mandarin"))
            {
                numMatchesToFind = 6;
            }

            if(p.getFirstLanguageToLearn().equals("Mandarin"))
            {
                numMatchesToFind = 6;
            }
            if(p.getSecondLanguageToLearn() != null && p.getSecondLanguageToLearn().equals("Mandarin"))
            {
                numMatchesToFind = 6;
            }
            if(p.getThirdLanguageToLearn() != null && p.getThirdLanguageToLearn().equals("Mandarin"))
            {
                numMatchesToFind = 6;
            }

            for(int x = 0; x < numMatchesToFind; x++) {
                //Set data storage object
                Person highScorer = null;
                Integer highScore = new Integer(0);

                for (Person key : tempMap.keySet()) {
                    //If person has higher score than current and isn't already in the list
                    if (tempMap.get(key) > highScore) {
                        highScorer = key;
                        highScore = tempMap.get(key);
                    }

                }
                highestScorers.add(highScorer);
                tempMap.remove(highScorer);
            }

            //Add their matches to the output
            output.put(p, highestScorers);
        }

        return output;
    }

    /**
     * Collects score for one person compared to everyone else
     * @param p The person for whom we are scoring
     * @param people The people that CLC.LanguageMatcher.camstutz.Person p is being scored against (CLC.LanguageMatcher.camstutz.Person p should not be included)
     * @return a Hashmap with People
     * @throws Exception in the case that there is a problem with the people
     */
    public static HashMap<Person, Integer> getAllScores(Person p, List<Person> people) throws Exception
    {
        //CLC.LanguageMatcher.camstutz.Person p must not be in the List people!
        HashMap<Person, Integer> map = new HashMap<Person, Integer>();

        for(Person person2: people)
        {
            map.put(person2, totalScore(p, person2));
        }

        return map;
    }

    /**
     * Calculates the total score for a match of people
     * @param person1  first person, with language attributes
     * @param person2  second person, with language attributes
     * @return an integer, with the score of the match
     * @throws Exception in the case that the first language of a person is null, showing a deeper problem
     */
    public static int totalScore (Person person1, Person person2) throws Exception
    {
        //Don't forget checks for null! A null shouldn't add a score!
        int totalScore = 0;

        //Compare person1's known languages to person 2's want to learn languages
        totalScore += scoreLanguagesOneWay(person1, person2);
        //Compare person2's known languages to person 1's want to learn languages
        totalScore += scoreLanguagesOneWay(person2, person1);

        //Get interests score
        totalScore += getInterestsScore(person1, person2);

        return totalScore;
    }

    /**
     * Calculates the score comparing one person to the other, in one direction
     * @param person1 first person, with language attributes
     * @param person2 second person, with language attributes
     * @return score in one direction
     * @throws Exception in the case that first language of the person is null (shows deeper problem)
     */
    public static int scoreLanguagesOneWay(Person person1, Person person2) throws Exception
    {
        //initialize score variable
        int score = 0;

        //Get person 1's known language
        List<String> person1KnownLangs = person1.getKnownLanguages();


        //Check to make sure first language isn't null
        if(!(person2.getFirstLanguageToLearn() == null))
        {
            //Check if person2's first language to learn is one of the known for person 1
            if (person1KnownLangs.contains(person2.getFirstLanguageToLearn()))
            {
                //Add to score, first language weighted at 5
                score += 3;
            }
        }
        //First language should never be null - if it is, something's wrong (probably data input specialty
        else
        {
            throw new Exception(person2.getName() + " - First Language Blank");
        }

        //Check to make sure second language isn't null
        if(!(person2.getSecondLanguageToLearn() == null))
        {
            //Check if person2's second language to learn is one of the known for person 1
            if(person1KnownLangs.contains(person2.getSecondLanguageToLearn()))
            {
                //Add to score, second language weighted at 3
                score += 2;
            }
        }

        //Check to make sure third language isn't null
        if(!(person2.getThirdLanguageToLearn() == null))
        {
            //Check if person2's third language to learn is one of the known for person 1
            if (person1KnownLangs.contains(person2.getThirdLanguageToLearn()))
            {
                //Add to score, third language weighted at 1
                score += 2;
            }
        }


        //return score total
        return score;
    }

    /**
     * Get the interests score
     * @param person1 first person
     * @param person2 second person
     * @return an integer, with a small score addition if the people share interests
     */
    public static int getInterestsScore(Person person1, Person person2)
    {
        //initialize score
        int score = 0;

        //Iterate through all of the first person's interests
        for(String interest: person1.getInterests())
        {
            //if this interest matches one of the other person's then
            if(person2.getInterests().contains(interest))
            {
                //add a bit to the score
                score += .25;
            }
        }

        //Return the value
        return score;
    }

}
