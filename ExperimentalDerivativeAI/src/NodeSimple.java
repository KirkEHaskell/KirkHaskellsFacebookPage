import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class NodeSimple{


    private List<NodeSimple> inputConnections= new ArrayList<>();
    private List<Double> weights = new ArrayList<>();
    private Double value;
    private double constant;
    private String function;

    private double derivative;
    public NodeSimple(){
    }

    public void addInputConnection(NodeSimple newInput){
        Random rand = new Random();
        inputConnections.add(newInput);
        weights.add(rand.nextDouble());
        constant = 0.0;
    }
    public Double getValue(){
        return value;
    }
    public void setValue(Double newValue){
        value = newValue;
    }
    public void setFunction(String newFunction){
        function = newFunction;
    }
    public void calculateValue(){
        double sum =0;
        double temp;
        int index =0;
        for(NodeSimple input : inputConnections){
            temp = input.getValue();
            temp = temp * weights.get(index);
            sum=sum+temp;
            index++;
        }

        //okay, so lets find a way to make different forms of functions
        switch(function){
            case "tanh":
                value = Math.tanh(sum);
                break;
            case "GeLU":
                double pi = Math.PI;
                double c = Math.sqrt(2/pi);
                value = sum;
                value = 0.5 * value * (1 + Math.tanh(c * (value + 0.044715 * Math.pow(value, 3))));
                break;
            case "exponential":
                double e = 2.71828;
                double a = Math.pow(e,sum);
                value =a;
            case "none":
                value=sum;
                break;
        }
        value=value+constant;
    }
    public void setWeights(List<Double> newWeights){
        double temp;
        for(int weight=0; weight<weights.size(); weight++){
            temp = 1 * newWeights.get(weight);
            weights.remove(weight);
            weights.add(weight, temp);
        }
    }

    public void changeWeight(int special, double change){
        //System.out.println(weights);
        double temp = weights.get(special) * 1.0;
        temp = temp + change;
        weights.remove(special);
        weights.add(special, temp);
    }
    public void copyTo(NodeSimple worseNode){
        List<Double> tempList = new ArrayList<>();
        double temp;
        for(Double weight : weights){
            temp = weight * 1 ;
            tempList.add(temp);
        }
        worseNode.setWeights(tempList);
        worseNode.setConstant(constant);
    }
    public void setConstant(double newConstant){
        constant = newConstant;
    }
    public void randomChanges(Double mutationFactor){
        Random rand = new Random();
        double temp;
        for(int index =0; index<weights.size();index++){
            temp = rand.nextDouble() -0.5;
            temp=temp*mutationFactor;
            temp = temp + weights.get(index);
            weights.remove(index);
            weights.add(index, temp);
        }

        temp = rand.nextDouble() -0.5;
        temp = temp * mutationFactor;
        constant = constant + temp;

    }

    public void printWeights(){
        System.out.println(weights);
    }

}
