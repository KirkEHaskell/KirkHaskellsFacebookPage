import java.util.ArrayList;
import java.util.List;

public class AI {

    private int rowNumber;
    private int columnNumber;
    private int networkNumber;
    private int setNumber;
    private int bestAIIndex=0;
    private List<Network> networks = new ArrayList<>();
    private List<Double> networkGrades = new ArrayList<>();
    private List<List<Double>> inputSet = new ArrayList<>();
    private List<List<Double>> outputSet = new ArrayList<>();

    public AI(int rows, int columns, int nets){
        rowNumber = rows;
        columnNumber = columns;
        networkNumber = nets;

        for(int net =0; net<networkNumber; net++){
            networks.add(new Network(rows,columns));
            networkGrades.add(0.0);
        }

    }

    //check if the double thing works out....
    public void setLearningSet
    (List<List<Double>> newInputSet, List<List<Double>> newOutputSet, int sets){
        setNumber =sets;
        inputSet = newInputSet;
        outputSet= newOutputSet;
    }

    //this is a super method
    public void learnRandomly(int generations, double mutationFactor){

        for(int gen =0; gen<generations; gen++){
            System.out.println("generation:" + gen);
            this.calculateGrades();
            this.findBestGrade();
            this.randomizeNetworks(mutationFactor);
        }
    }
    public void calculateGrades(){
        double temp;
        for(int net =0; net<networkNumber; net++){
            networkGrades.remove(net);
            networkGrades.add(0.0);
            for(int set =0; set<setNumber; set++){
                temp = networkGrades.get(net) + networks.get(net).calculateGrade(inputSet.get(set), outputSet.get(set));
                networkGrades.remove(net);
                networkGrades.add(net, temp);
            }
        }
    }
    public void findBestGrade(){
        double bestAIGrade = networkGrades.get(0) * 1;
        bestAIIndex =0;
        System.out.println("network grade 0:" + networkGrades.get(0));

        for(int net =0; net<networkNumber; net++){
            if(bestAIGrade>networkGrades.get(net)){
                bestAIGrade=networkGrades.get(net)  * 1;
                bestAIIndex=net;
            }
        }
        System.out.println("bestAIGrade:" + networkGrades.get(bestAIIndex));
    }
    public void randomizeNetworks(double mutationFactor){
        for(int net =0; net<networkNumber; net++){
            if(net != bestAIIndex){
                networks.get(bestAIIndex).copyTo(networks.get(net));
                networks.get(net).randomChanges(mutationFactor);
            }
        }
    }

    public void setToBestAI(){
        for(int net =0; net<networkNumber; net++){
            if(net!=bestAIIndex){
                networks.get(bestAIIndex).copyTo(networks.get(net));
            }
        }
    }
    //if your going to use this, do it with networkNumber set to 3
    public void learnBimodaly(int generations, double mutationAmount){
        double temp;
        for(int gen=0; gen<generations; gen++){

            for (int row = 0; row < rowNumber; row++) {
               for (int column = 1; column < columnNumber; column++) {
                   for (int special = 0; special < rowNumber; special++) {
                       for (int i = 0; i < 3; i++) {
                           networks.get(0).changeNodeWeight(row, column, special, 0.0);
                           networks.get(1).changeNodeWeight(row, column, special, mutationAmount);

                           temp = -1 * mutationAmount;

                           networks.get(2).changeNodeWeight(row, column, special, temp);
                           networks.get(3).changeNodeWeight(row, column, special, 0.0);
                           networks.get(4).changeNodeWeight(row, column, special, mutationAmount);
                           networks.get(5).changeNodeWeight(row, column, special, temp);
                           System.out.println("this is gen " + gen);
                           this.calculateGrades();
                           this.findBestGrade();
                           this.setToBestAI();
                        }
                   }
               }
           }
        }
    }

    public List<Double> useAI(List<Double> inputs){
        List<Double> outputs = networks.get(bestAIIndex).useNetwork(inputs);
        return outputs;
    }

    //these last two are garabage that have not been tested, they are likely the problem if your doing something
    //I think they have been tested
    public void setAllFunctions(String newFunction){
        for(int net =0; net<networkNumber; net++) {
            networks.get(net).setAllFunctions(newFunction);
        }
    }
    public void setColumnFunctions(String newFunction, int column){
        for(int net =0; net<networkNumber; net++){
            networks.get(net).setColumnFunction(newFunction, column);
        }
    }



}
