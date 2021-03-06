

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * (C) 2016 Campus Language Connection. All rights reserved.
 * Created by Christian Amstutz for Campus Language Connection. To be used only by authorized people for authorized purposes.
 */
public class Driver {
    public static void main(String[] args) throws Exception
    {
        //'Legal' notices
        System.out.println("(C) 2016 Campus Language Connection. All rights reserved.");
        System.out.println("Created by Christian Amstutz for Campus Language Connection.");
        System.out.println("To be used by authorized people for authorized purposes only.");

        //Remember begin time to measure execution time
        long beginTime = System.nanoTime();

        //Get file path of current class
        String path =  Driver.class.getProtectionDomain().getCodeSource().getLocation().toString();

        if(path.startsWith("file:"))
        {
            path = path.substring("file:".length(), path.length());
        }

        //System.out.println(path);

        int parentPathEnd =  path.indexOf("CLC-LanguageMatcher/");
        //String parentPath = path.substring(0, parentPathEnd + "CLC-LanguageMatcher/".length()); //Used when packaged
        String parentPath = ""; //Used in IntelliJ

        //Scanner fileInput = new Scanner(new File(parentPath + "/OnlineLocationData.txt"));

        //Find the File, read all lines into the input
        CSVReader reader = new CSVReader(new FileReader(parentPath + "CLC-Data.csv"));
        LinkedList<String[]> input = new LinkedList<String[]>(reader.readAll());

        //Create data storage for all of the CLC.LanguageMatcher.camstutz.Person objects to be created
        LinkedList<Person> people = new LinkedList<Person>();

        //Create the people from the input information
        Iterator<String[]> iterator = input.iterator();

        //Skip first line of headers
        iterator.next();

        //Used for debugging
        //System.out.println(input);

        while(iterator.hasNext())
        {
            String[] oneLine = iterator.next();

            //oneLine[0] (or CSV column 1) holds the participant number - not applicable here


            //oneLine[1] (or CSV column 2) holds the applicant's name
            String name = oneLine[1].trim();

            //oneLine[2] (or CSV column 3) holds the person's email
            String email = oneLine[2].trim();

            //oneLine[3] (or CSV column 4) holds the person's area of study
            String areaOfStudy = oneLine[3].trim();

            //oneLine[4] (or CSV column 5) hold the person's year of study
            String yearOfStudy= oneLine[4].trim();

            //get the known languages in a list, which are held in oneLine[5] and oneLine[6] (or CSV columns 6 and 7)
            LinkedList<String> knownLanguages = new LinkedList<String>();
            knownLanguages.add(oneLine[5].trim());
            if(!oneLine[6].equals("n/a")) {
                knownLanguages.add(oneLine[6].trim());
            }


            //get the languages to learn, which are held in oneLine[7], oneLine[9], and oneLine[9] (or CSV columns 8, 9, & 10)
            String firstLanguageToLearn = oneLine[7].trim();
            String secondLanguageToLearn;
            if(oneLine[8].equals("n/a"))
            {
                secondLanguageToLearn = null;
            }
            else {
                secondLanguageToLearn = oneLine[8].trim();
            }

            String thirdLanguageToLearn;
            if(oneLine[9].equals("n/a"))
            {
                thirdLanguageToLearn = null;
            }
            else {
                thirdLanguageToLearn = oneLine[9].trim();
            }

            //get the interests, which are held in oneLine[10], oneLine[11], and oneLine[12] (or CSV columns 11, 12, & 13)
            LinkedList<String> interests = new LinkedList<String>();
            for(int x = 10; x < 13; x++)
            {
                if(!oneLine[x].equals("n/a"))
                {
                    interests.add(oneLine[x].trim());
                }
            }

            //Used for debugging
            //System.out.println(oneLine[13]);

            //get the personality type
            Integer personalityType = new Integer(oneLine[13].trim());

            Person p = new Person(name, email, areaOfStudy,yearOfStudy, knownLanguages, firstLanguageToLearn, secondLanguageToLearn, thirdLanguageToLearn, interests, personalityType);
            people.add(p);


        }

        HashMap<Person, LinkedList<Person>> returned = PeopleUtility.getMatches(people);

        PrintWriter writer = new PrintWriter(parentPath + "LanguageMatcherResults.txt", "UTF-8");

        LinkedList<Person> keys = new LinkedList<Person>(returned.keySet());
        Collections.sort(keys);

        writer.println("Language Matching Result:");
        writer.println();

        for(Person p: keys)
        {
            writer.println(p);
            for(Person match: returned.get(p))
            {
                writer.println("      Match:" + match);
            }
            writer.println();
        }

        writer.println();
        writer.println();
        writer.println("All People:");
        Collections.sort(people);
        for(Person p: people)
        {
            writer.println(p);
        }

        writer.close();

        long endTime = System.nanoTime();
    }
}
