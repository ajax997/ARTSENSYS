package artsensys.nlpunit.engstandardization;

import artsensys.dbcontroller.ObjectController;
import artsensys.dbcontroller.neo4jcontroller.Neo4JInteraction;
import org.neo4j.driver.v1.Record;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by nguyennghi on 2/26/184:16 PM.
 */
public class Test {
    public static void main(String... args)
    {
        LogManager.getLogManager().reset();
//        ENG_Standardized eng_standardize = new ENG_Standardized("", FormalLevel.HIGH, "");
////        eng_standardize.parse("In 1958, David Finkelstein identified the Schwarzschild surface as an event horizon, \"a perfect unidirectional membrane: causal influences can cross it in only one direction \". This did not strictly contradict Oppenheimer's results, but extended them to include the point of view of infalling observers. Finkelstein's solution extended the Schwarzschild solution for the future of observers falling into a black hole. A complete extension had already been found by Martin Kruskal, who was urged to publish it.\n" +
////                "\n" +
////                "These results came at the beginning of the golden age of general relativity, which was marked by general relativity and black holes becoming mainstream subjects of research. This process was helped by the discovery of pulsars in 1967, which, by 1969, were shown to be rapidly rotating neutron stars. Until that time, neutron stars, like black holes, were regarded as just theoretical curiosities; but the discovery of pulsars showed their physical relevance and spurred a further interest in all types of compact objects that might be formed by gravitational collapse.\n" +
////                "\n" +
////                "In this period more general black hole solutions were found. In 1963, Roy Kerr found the exact solution for a rotating black hole. Two years later, Ezra Newman found the axisymmetric solution for a black hole that is both rotating and electrically charged." +
////                " Through the work of Werner Israel, Brandon Carter, and David Robinson the no-hair theorem emerged, stating that a stationary black hole solution is completely described by the three parameters of the Kerr–Newman metric: mass, angular momentum, and electric charge.[35]\n" +
////                "\n" +
////                "At first, it was suspected that the strange features of the black hole solutions were pathological artifacts from the symmetry conditions imposed, and that the singularities would not appear in generic situations. This view was held in particular by Vladimir Belinsky, Isaak Khalatnikov, and Evgeny Lifshitz, who tried to prove that no singularities appear in generic solutions. However, in the late 1960s Roger Penrose[36] and Stephen Hawking used global techniques to prove that singularities appear generically.\n" +
////                "\n" +
////                "Work by James Bardeen, Jacob Bekenstein, Carter, and Hawking in the early 1970s led to the formulation of black hole thermodynamics. These laws describe the behaviour of a black hole in close analogy to the laws of thermodynamics by relating mass to energy, area to entropy, and surface gravity to temperature. The analogy was completed when Hawking, in 1974, showed that quantum field theory predicts that black holes should radiate like a black body with a temperature proportional to the surface gravity of the black hole.[39]\n" +
////                "\n");
////        ArrayList<PartOfSpeech> pos = Neo4jObjectHelper.getAllPartOfSpeech("a");
////        for(PartOfSpeech p : pos)
////            System.out.println(p);
//        eng_standardize.parse("WalkSAT first picks a clause which is unsatisfied by the current assignment, then flips a variable within that clause. The clause is picked at random among unsatisfied clauses. The variable is picked that will result in the fewest previously satisfied clauses becoming unsatisfied, with some probability of picking one of the variables at random. When picking at random, WalkSAT is guaranteed at least a chance of one out of the number of variables in the clause of fixing a currently incorrect assignment. When picking a guessed to be optimal variable, WalkSAT has to do less calculation than GSAT because it is considering fewer possibilities.");

        try {
            FileWriter writer = new FileWriter("gerundwithnoverb.txt");
            List<Record> res = ObjectController.getNeo4jInstance().execute("match (n:ObjectEntity)  where right(n.objectEntity, 3) = 'ing' and not (n)-[:LANG_POLY_MEANING]->(:VERB) and not (n)<-[:LANG_GRAMMAR_GERUND]-() return n.objectEntity");
            for(Record record : res)
            {
                String value = record.get("n.objectEntity").asString();
                writer.write(value + "\n");

            }
            System.out.println("DONE!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
