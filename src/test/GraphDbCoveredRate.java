package test;

import artsensys.dbcontroller.neo4jcontroller.Neo4jObjectHelper;
import com.sun.deploy.util.StringUtils;
import javafx.stage.FileChooser;
//import org.neo4j.driver.internal.shaded.io.netty.util.internal.StringUtil;
//import org.neo4j.driver.internal.shaded.io.netty.util.internal.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by nguyennghi on 2/16/189:00 PM.
 */
public class GraphDbCoveredRate {
    private File file;
    GraphDbCoveredRate(String filename)
    {
        file = new File(filename);
    }

    public void start() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        Scanner scanner = new Scanner(fis);
        ArrayList<String> already = new ArrayList<>();
        int count = 0;
        int i = 0;
        while (scanner.hasNext())
        {

            String word = scanner.next().replace('.', ' ').replace('-',' ').replace(',',' ').replace('\"', ' ').replace('!', ' ').trim().toLowerCase();
            i++;
            if(!already.contains(word)) {
                boolean chk = Neo4jObjectHelper.checkEntityAvailable(word);
                if (!chk) {
                    if (isNumeric(word))
                        chk = true;

                }
                if (chk) {
                    already.add(word);
                }
                else {
                    System.out.println(word + " >> " + false);;
                }
                count = chk ? count + 1 : count;
            }
            else
            {
                count++;
            }
        }
        System.err.println("The database covers "+String.valueOf((float)count/i*100).substring(0,5)+"% of this file.");
    }
    private static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

}
