import java.util.List;
import java.util.ArrayList;

/**
 * The type Main.
 */
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        String train = "spamLabelled.dat";
        String test= "spamUnlabelled.dat";

        Parser p = new Parser();
        List<List<String>> train_attributes = p.readFromFile(train);
        List<List<String>> test_attributes1 = new ArrayList<>(train_attributes);

        List<String> ZeroOccurance1 = new ArrayList<>();
        List<String> ZeroOccurance2 = new ArrayList<>();
        for(int i =0; i<train_attributes.get(0).size();i++){
            ZeroOccurance1.add("1");
            ZeroOccurance2.add("0");
        }
        train_attributes.add(ZeroOccurance1);
        train_attributes.add(ZeroOccurance2);

        NaiveBayes naiveBayes = new NaiveBayes();
        naiveBayes.calculatePriorProb(train_attributes);
        List<List<String>> train_attributes1 = new ArrayList<>(train_attributes);
        naiveBayes.calculateLikelihoodProb(train_attributes1);




        double correct1 = 0;
        for(List<String> em:test_attributes1){
            List<String> email = new ArrayList<>(em);
            email.remove(email.size()-1);
            double PisSpam = naiveBayes.calculatePosteriorProb(email,true);
            double PisnotSpam = naiveBayes.calculatePosteriorProb(email,false);
            double prediction =0;
            if(PisSpam>PisnotSpam){
                prediction =1;
            }
            if(prediction==Double. parseDouble(em.get(em.size()-1))){
                correct1++;
            }
        }
        System.out.println("");
        System.out.println("--------------------------------------------------------------");
        System.out.println("Here is the Naive Bayes classification on spamLabelled.dat , for testing performance purpose");
        System.out.println("There are "+correct1+" out of "+test_attributes1.size()+" correct.");
        System.out.println("acc ="+ correct1/test_attributes1.size());


        List<List<String>> test_attributes = p.readFromFile(test);
        //test_attributes.remove(test_attributes.size()-1);

        System.out.println("");
        System.out.println("--------------------------------------------------------------");
        System.out.println("Here is the Naive Bayes classification on spamUnlabelled.dat:");
        int count=1;
        int spamC=0;
        int notSpamC=test_attributes.size();
        for(List<String> em:test_attributes){
            List<String> email = new ArrayList<>(em);
            double PisSpam = naiveBayes.calculatePosteriorProb(email,true);
            double PisnotSpam = naiveBayes.calculatePosteriorProb(email,false);
            System.out.println("");
            System.out.println("For instance "+count);
            System.out.println("The posterior probability of P(C=1 | f) =");
            System.out.println(PisSpam);
            System.out.println("The posterior probability of P(C=0 | f) =");
            System.out.println(PisnotSpam);
            System.out.println("");

            String prediction ="is not Spam";
            if(PisSpam>PisnotSpam){
                prediction ="is Spam";
                spamC++;
                notSpamC--;
            }
            if(prediction.equals("is Spam")){
                System.out.println("Because P(C=1 | all attributes) > P(C=0 | all attributes), so ");
            }
            else if(prediction.equals("is not Spam")){
                System.out.println("Because P(C=0 | all attributes) > P(C=1 | all attributes), so ");
            }

            System.out.println("Instance "+count+" is classified as '" +prediction+"'");
            System.out.println("--------------------------------------------------------------");
            count++;
        }
        System.out.println("there are " + spamC+" spam emails and there are " + notSpamC +" emails not spam" );

        System.out.println("Finish");
    }
}
