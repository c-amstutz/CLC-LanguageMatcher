import java.util.InputMismatchException;
import java.util.List;

/**
 * (C) 2016 Campus Language Connection. All rights reserved.
 * Created by Christian Amstutz for Campus Language Connection. To be used only by authorized people for authorized purposes.
 */
public class Person implements Comparable {

    private String email;
    private String name;
    private String areaOfStudy;
    private String yearOfStudies;
    private List<String> knownLanguages;
    private String firstLanguageToLearn;
    private String secondLanguageToLearn;
    private String thirdLanguageToLearn;
    private List<String> interests;
    private Integer personalityType;

    public Person()
    {
        throw new InputMismatchException("Tried to use general constructor");
    }

    /**
     * Constructor for person object - inputs are self explanatory
     * @param inName
     * @param inEmail
     * @param inKnownLanguages
     * @param inFirstLanguage
     * @param inSecondLanguage
     * @param inThirdLanguage
     * @param inAreaOfStudy
     * @param inInterests
     * @param inPersonalityType
     */
    public Person(String inName, String inEmail, String inAreaOfStudy, String inYearOfStudy, List<String> inKnownLanguages, String inFirstLanguage, String inSecondLanguage, String inThirdLanguage, List<String> inInterests, Integer inPersonalityType)
    {
        name = inName;
        email = inEmail;
        areaOfStudy = inAreaOfStudy;
        yearOfStudies = inYearOfStudy;
        knownLanguages = inKnownLanguages;
        firstLanguageToLearn = inFirstLanguage;
        secondLanguageToLearn = inSecondLanguage;
        thirdLanguageToLearn = inThirdLanguage;
        interests = inInterests;
        personalityType = inPersonalityType;

        //Important fields can't be null, if others become important (as in, used to determine score), this IF statement would need to change
        if (inName == null || inName == "" || inEmail == null || inEmail == "" || inKnownLanguages == null || inKnownLanguages.size() == 0 || inFirstLanguage == null || inFirstLanguage == "")
        {
            throw new InputMismatchException("Constructor field null");
        }
    }
    //Note: CLC.LanguageMatcher.camstutz.Person constructor args as follows: name, email, known languages, firstLang, secondLang, thirdLang, areaOfStudy, academicInterests, nonAcademicInterests, personalityType

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getYearOfStudies()
    {
        return yearOfStudies;
    }

    public String getAreaOfStudy() {
        return areaOfStudy;
    }

    public List<String> getKnownLanguages() {
        return knownLanguages;
    }

    public String getSecondLanguageToLearn() {
        return secondLanguageToLearn;
    }

    public String getFirstLanguageToLearn() {
        return firstLanguageToLearn;
    }

    public String getThirdLanguageToLearn() {
        return thirdLanguageToLearn;
    }

    public List<String> getInterests() {
        return interests;
    }

    public int getPersonalityType() {
        return personalityType;
    }

    public String toStringMoreInfo()
    {
        return "Person[email=" + email + ", name=" + name +", yearOfStudy=" + yearOfStudies + ", knownLanguages=" + knownLanguages.toString() + ", firstToLearnLanguage=" + firstLanguageToLearn + ", secondToLearnLanguage=" + secondLanguageToLearn + ", thirdToLearnLanguage=" + thirdLanguageToLearn + ", interests=" + interests.toString() + "]";
    }


    //Building a toString that's easier to read
    public String toString()
    {
        return "" + name  +  ", knownLanguages=" + ListToString(knownLanguages) + ", toLearn=[" + firstLanguageToLearn + " " + secondLanguageToLearn + " " + thirdLanguageToLearn + "], interests=" + ListToString(interests) + ", yearOfStudy=" + yearOfStudies + ", email=" + email;
    }

    public int compareTo(Object other)
    {
        if(other instanceof Person) {
            Person otherPerson = (Person) other;
            return getName().compareToIgnoreCase(otherPerson.getName());
        }
        else
        {
            throw new InputMismatchException("Can't compare Person to other object");
        }
    }

    private static String ListToString(List list)
    {
        String original = list.toString();
        String[] originalNoCommas = original.split(",");
        String output = "";

        for(String temp: originalNoCommas)
        {
            output += temp;
        }
        return output;
    }
}


