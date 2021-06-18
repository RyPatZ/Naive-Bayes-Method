import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Naive bayes.
 */
public class NaiveBayes {

    /**
     * The Prior prob.
     */
    Map<String, Double> PriorProb= new HashMap<>();
    /**
     * The Likelihood true probibility.
     */
    Map<String, Double> likelihood_true_probibility = new HashMap<String, Double>();
    /**
     * The Likelihood false probibility.
     */
    Map<String, Double> likelihood_false_probibility = new HashMap<String, Double>();

    /**
     * Instantiates a new Naive bayes.
     */
    public NaiveBayes(){

    }


    /**
     * Calculate prior prob map.
     *
     * @param em the em
     * @return the map
     */
    public Map<String, Double> calculatePriorProb(List<List<String>> em){
        List<List<String>> emails = em;
        List<Integer> trueCount = new ArrayList<>();
        List<Integer> falseCount = new ArrayList<>();

        int totalAmount = emails.size();

        for(int i=0; i< emails.get(0).size();i++){
            trueCount.add(0);
            falseCount.add(0);
        }
        for(List<String> email: emails){
            for(int i=0; i< email.size();i++){
                if(email.get(i).equals("1")){
                    trueCount.set(i,trueCount.get(i)+1);
                }
            }
        }
        Map<String, Double> probibility = new HashMap<String, Double>();
        for(int i=0; i< emails.get(0).size();i++){
            double prob = (double)trueCount.get(i)/(double)totalAmount;
            String key = "attribute" + i;

            if(i==12){
                key = "isSpam";
            }
            probibility.put(key,prob);
        }
        this.PriorProb = probibility;
        return probibility;
    }

    /**
     * Calculate likelihood prob.
     *
     * @param em the em
     */
    public void calculateLikelihoodProb(List<List<String>> em){
        List<List<String>> emails = em;
        Map<String, Double> true_probibility = new HashMap<String, Double>();
        Map<String, Double> false_probibility = new HashMap<String, Double>();

        List<Integer> trueCount = new ArrayList<>();
        List<Integer> trueFCount = new ArrayList<>();
        List<Integer> falseCount = new ArrayList<>();
        List<Integer> falseFCount = new ArrayList<>();

        for(int i=0; i< emails.get(0).size();i++){
            trueCount.add(0);
            falseCount.add(0);
            trueFCount.add(0);
            falseFCount.add(0);
        }


        int isSpam_Count=0;
        int isnotSpam_Count=0;
        for(List<String> email: emails){
                if(email.get(12).equals("1")) {
                    for(int i=0; i< email.size();i++) {
                        if (email.get(i).equals("1")) {
                            trueCount.set(i, trueCount.get(i) + 1);
                        }
                        if (email.get(i).equals("0")) {
                            trueFCount.set(i, trueFCount.get(i) + 1);
                        }
                    }
                    isSpam_Count++;
                }
                else if(email.get(12).equals("0")) {
                    for(int i=0; i< email.size();i++){
                    if (email.get(i).equals("1")) {
                        falseCount.set(i, falseCount.get(i) + 1);
                    }
                        if (email.get(i).equals("0")) {
                            falseFCount.set(i, falseFCount.get(i) + 1);
                        }
                }
                    isnotSpam_Count++;
            }
        }
        System.out.println("The likelihood probability table :");
        System.out.println("             "+"P(F=1 | C = 1)"+ "           " +"P(F=0 | C = 1)"+"          " +"P(F=1 | C = 0)"+ "       " +"P(F=0 | C = 0)");
        for(int i=0; i< emails.get(0).size();i++){
            double probfalse = (double)falseCount.get(i)/(double)isnotSpam_Count;
            double probFfalse = (double)falseFCount.get(i)/(double)isnotSpam_Count;
            double prob = (double)trueCount.get(i)/(double)isSpam_Count;
            double probF = (double)trueFCount.get(i)/(double)isSpam_Count;
            String key = "attribute" + i;

            if(i==12){
                key = "isSpam";
            }
            System.out.println(key +"  "+prob+ "     " +probF+"   "+ probfalse+"     " +probFfalse);
            true_probibility.put(key,prob);
            false_probibility.put(key,probfalse);
        }

        this.likelihood_true_probibility=true_probibility;
        this.likelihood_false_probibility=false_probibility;
    }

    /**
     * Calculate posterior prob double.
     *
     * @param em       the em
     * @param Classify the classify
     * @return the double
     */
    public double calculatePosteriorProb(List<String> em,boolean Classify){
        double prob =0;
        List<String> email = em;
        double likelihoodProb=0;
        int count=0;
        if(Classify) {
            likelihoodProb=PriorProb.get("isSpam");
            for (String e : email) {
                double likelihood =0;
                if(e.equals("1")){
                    likelihood=likelihood_true_probibility.get("attribute" + count);
                }
                else if(e.equals("0")){
                    likelihood= 1-likelihood_true_probibility.get("attribute" + count);
                }
                likelihoodProb *= likelihood;
                count++;
            }
            prob = likelihoodProb /1;
        }
        else if (!Classify){
            likelihoodProb=1-PriorProb.get("isSpam");
            for (String e : email) {
                double likelihood =0;
                if(e.equals("1")){
                    likelihood=likelihood_false_probibility.get("attribute" + count);
                }
                else if(e.equals("0")){
                    likelihood= 1-likelihood_false_probibility.get("attribute" + count);
                }
                likelihoodProb *= likelihood;
                count++;
            }
            prob = likelihoodProb /1;
        }
        return prob;
    }
}
