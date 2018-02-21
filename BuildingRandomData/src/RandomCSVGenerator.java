import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static com.sun.javafx.util.Utils.split;

/**
 * Created by christianamstutz on 2/20/18.
 * Generates a set of random people as an example for the Language Matcher program.
 * Built to allow for publishing of Language Matching code without compromising people's personal information.
 */
public class RandomCSVGenerator {

    public static void main(String[] args)
    {
        // _Person Generation Checks_
        //String[] person = createPerson();
        //for(String s: person)
        //    System.out.println(s);
        //System.out.println(checkPerson(person));

        int count = 1;
        List<String[]> people = new LinkedList<>();
        while(people.size() < 160)
        {
            String[] person = createPerson();
            if(checkPerson(person))
            {
                people.add(person);
            }
            count++;
            System.out.println(Integer.toString(count) + "-" + Integer.toString(people.size()));
        }

        System.out.println("Complete");

        FileWriter writer = null;

        try{
            writer = new FileWriter("BuildingRandomData/CLC-Data.csv");
            writer.append("#");
            writer.append(',');
            writer.append("Name");
            writer.append(',');
            writer.append("Email");
            writer.append(',');
            writer.append("Area of Study");
            writer.append(',');
            writer.append("Year");
            writer.append(',');
            writer.append("Language 1");
            writer.append(',');
            writer.append("Language 2");
            writer.append(',');
            writer.append("To Learn 1");
            writer.append(',');
            writer.append("To Learn 2");
            writer.append(',');
            writer.append("To Learn 3");
            writer.append(',');
            writer.append("Interest 1");
            writer.append(',');
            writer.append("Interest 2");
            writer.append(',');
            writer.append("Interest 3");
            writer.append(',');
            writer.append("Personality");
            writer.append('\n');


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        int personNumber = 1;
        for(String[] person : people){
            try{
                for(int i = 0; i < person.length + 1; i++) {

                    if(i == 0)
                        writer.append(Integer.toString(personNumber));
                    else
                        writer.append(person[i - 1]);

                    if(i < person.length)
                        writer.append(',');
                }
                if(! person.equals(people.get(people.size() - 1)))
                    writer.append('\n');

            } catch (IOException e) {
                e.printStackTrace();
            }
            personNumber++;
        }


        try{
            writer.flush();
            writer.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static String[] createPerson()
    {
        String[] names = getName();

        String[] person = {names[0] + " " + names[1], getEmail(names), getStudies(), getYear(), getLanguage(1, 0), getLanguage(2, 0), getLanguage(1, 1), getLanguage(2, 1), getLanguage(3, 1), getInterest(1), getInterest(2), getInterest(3), Integer.toString(getPersonality())};
        return person;
    }

    public static boolean checkPerson(String[] person)
    {
        List<String> know = new ArrayList<>();
        List<String> toLearn = new ArrayList<>();

        know.add(person[4]);
        know.add(person[5]);
        toLearn.add(person[6]);
        toLearn.add(person[7]);
        toLearn.add(person[8]);

        for(String s: know)
        {
            if(toLearn.contains(s) && !s.equals("n/a"))
                return false;
        }

        Set<String> knowSet = new HashSet<>(know);
        Set<String> toLearnSet = new HashSet<>(toLearn);

        if(knowSet.size() < know.size() || toLearnSet.size() < toLearn.size())
            return false;

        //11,12,13
        List<String> interests = new ArrayList<>();
        interests.add(person[9]);
        interests.add(person[10]);
        interests.add(person[11]);

        Set<String> interestsSet = new HashSet<>(interests);

        if(interestsSet.size() < interests.size())
            return false;

        return true;
    }

    /**
     *
     * @param whichLanguage 1, 2 or 3
     * @param knowOrToLearn 0 -> toKnow; 1 -> toLearn
     * @return
     */
    public static String getLanguage(int whichLanguage, int knowOrToLearn)
    {

        List<String> languages = importFile("BuildingRandomData/languages.txt");

        Random r = new Random();
        if(whichLanguage == 1){
            return languages.get(r.nextInt(languages.size()));
        }

        else if(whichLanguage == 2){

            int choice;
            if(knowOrToLearn == 0) {
                choice = r.nextInt(3);
                if(choice == 0)
                    return getLanguage(1,0);
                else
                    return "n/a";
            }
            else
            {
                choice = r.nextInt(20);
                if(choice < 12)
                {
                    return getLanguage(1,0);
                }
                else
                    return "n/a";
            }
        }

        else if(whichLanguage == 3)
        {

            int choice;
            if(knowOrToLearn != 1)
                return "ERROR: getLanguage";
            choice = r.nextInt(4);
            if(choice == 0)
            {
                return getLanguage(1,0);
            }
            else
                return "n/a";
        }

        else
        {
            return "Error: getLanguage";
        }

    }

    public static String[] getName()
    {
        Scanner in = null;
        try {
            in = new Scanner(new FileReader("BuildingRandomData/firstNames.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String> firstNames = new ArrayList<>();

        while(in.hasNextLine()){
            String temp = in.nextLine();
            String tempSplit[] = split(temp, " ");
            firstNames.add(tempSplit[0]);
        }

        try {
            in = new Scanner(new FileReader("BuildingRandomData/lastNames.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String> lastNames = new ArrayList<>();

        while(in.hasNextLine()){
            String temp = in.nextLine();
            String tempSplit[] = split(temp, " ");
            lastNames.add(tempSplit[0]);
        }

        Random r = new Random();

        String firstName = firstNames.get(r.nextInt(firstNames.size()));
        String lastName = lastNames.get(r.nextInt(lastNames.size()));

        String[] finalName = {firstName, lastName};

        finalName[0] = firstName.substring(0,1) + firstName.substring(1, firstName.length()).toLowerCase();
        finalName[1] = lastName.substring(0,1) + lastName.substring(1, lastName.length()).toLowerCase();

        return finalName;
    }

    public static String getInterest(int whichInterest)
    {
        List<String> interests = importFile("BuildingRandomData/interests.txt");
        Random r = new Random();

        if(whichInterest == 1)
            return interests.get(r.nextInt(interests.size()));
        else if(whichInterest == 2)
        {
            int choice = r.nextInt(6);
            if(choice == 0)
                return "n/a";
            else
                return getInterest(1);
        }
        else if(whichInterest == 3)
        {
            int choice = r.nextInt(2);
            if(choice == 0)
                return "n/a";
            else
                return getInterest(1);
        }
        else
            return "ERROR: getInterest";
    }


    public static int getPersonality()
    {
        Random r = new Random();
        return r.nextInt(5) + 1;
    }

    public static String getYear()
    {
        List<String> years = importFile("BuildingRandomData/years.txt");
        Random r = new Random();
        return years.get(r.nextInt(years.size()));
    }

    public static String getStudies()
    {
        List<String> studies = importFile("BuildingRandomData/studies.txt");
        Random r = new Random();
        return studies.get(r.nextInt(studies.size()));
    }

    public static String getEmail(String[] names)
    {
        return names[0] + "." + names[1] + "@test.com";
    }

    public static List<String> importFile(String path)
    {
        Scanner in = null;
        try {
            in = new Scanner(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(path);
        }
        try {
            in = new Scanner(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<String> array = new ArrayList<>();

        while(in.hasNextLine()){
            String temp = in.nextLine();
            array.add(temp);
        }
        return array;
    }
}
