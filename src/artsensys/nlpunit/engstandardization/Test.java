package artsensys.nlpunit.engstandardization;

/**
 * Created by nguyennghi on 2/26/184:16 PM.
 */
public class Test {
    public static void main(String... args)
    {
        ENG_Standardize eng_standardize = new ENG_Standardize("", FormalLevel.HIGH, "");
        eng_standardize.parse("IntelliJ IDEA suggests using file and code templates on the project or default (global) level.\n" +
                "\n" +
                "If you need a sharable set of file and code templates, then these templates should be per-project; otherwise the templates are global and pertain to the entire workspace.\n" +
                "\n" +
                "The file and code templates are stored in the following locations:");
    }
}
