import java.util.ArrayList;
import java.util.List;

public class Network {

    private int rowNumber;
    private int columnNumber;
    private List<List<NodeSimple>> nodes = new ArrayList<>();


    //this sets up everything for the network, I think.
    public Network(int rows, int columns){
        rowNumber =rows;
        columnNumber = columns;


        for(int col =0; col<columnNumber; col++){
            this.addColumn();
            for(int row =0; row<rowNumber; row++){
                this.addNode(col);
            }
        }

        this.setConnections();
    }

    public void addColumn(){
        nodes.add(new ArrayList<>());
    }

    public void addNode(int column){
        nodes.get(column).add(new NodeSimple());
    }

    public void setConnections(){
        for(int column=1; column<columnNumber; column++){
            for(int row=0; row<rowNumber; row++){
                for(int special=0; special<rowNumber; special++){
                    nodes.get(column).get(row).addInputConnection(nodes.get(column-1).get(special));
                }
            }
        }
    }

    public void calculateNetwork(List<Double> inputs){

        for(int row=0; row<rowNumber; row++){
            nodes.get(0).get(row).setValue(inputs.get(row) * 1);
        }

        for(int column=1; column<columnNumber; column++){
            for(int row=0; row<rowNumber; row++){
                nodes.get(column).get(row).calculateValue();
            }
        }

    }

    //returns a new set of Doubles, does not return specific referances, just
    //a set of new referances, the outputs are not special or whatever.
    public List<Double> getOutputs(){
        List<Double> outputs = new ArrayList<>();
        double temp;
        for(int row=0; row<rowNumber; row++){
            temp = nodes.get(columnNumber-1).get(row).getValue();
            outputs.add(temp);
        }
        return outputs;
    }

    public Double calculateGrade(List<Double> inputs, List<Double> wantedOutputs){
        this.calculateNetwork(inputs);
        List<Double> outputs = this.getOutputs();
        double sum=0;
        double temp;
        for(int row=0; row<rowNumber; row++){
            temp = outputs.get(row) - wantedOutputs.get(row);
            temp = temp * temp;
            sum = sum+temp;
        }
        Double totalGrade = sum;

        return totalGrade;
    }

    public NodeSimple getNode(int row, int column){
        return nodes.get(column).get(row);
    }

    public void copyTo(Network worseNetwork){
        for(int column =0; column<columnNumber; column++){
            for(int row=0; row<rowNumber; row++){
                this.getNode(row, column).copyTo(worseNetwork.getNode(row, column));
            }
        }
    }

    public void randomChanges(Double mutationFactor){
        for(List<NodeSimple> column : nodes){
            for(NodeSimple node : column){
                node.randomChanges(mutationFactor);
            }
        }
    }

    public void changeNodeWeight(int row, int column, int special, double mutationAmount){
        nodes.get(column).get(row).changeWeight(special, mutationAmount);
    }
    public List<Double> useNetwork(List<Double> inputs){
        this.calculateNetwork(inputs);
        List<Double> outputs = this.getOutputs();
        return outputs;
    }
    public void printWeights(){
        for(List<NodeSimple> column : nodes){
            for(NodeSimple node : column){
                node.printWeights();
            }
        }
    }

    public void setAllFunctions(String newFunction){
        for(int row=0; row<rowNumber; row++){
            for(int column=0; column<columnNumber; column++){
                nodes.get(column).get(row).setFunction(newFunction);
            }
        }
    }
    public void setColumnFunction(String newFunction, int column){
        for(int row=0; row<rowNumber; row++){
            nodes.get(column).get(row).setFunction(newFunction);
        }
    }

    public void calculateDerivatives(List<Double> inputs){
        List<Double> firstOutputs;
        List<Double> seccondOutputs;
        List<Double> tempDerivatives;

        for(int row=0; row<rowNumber; row++){
            for(int column=0; column<columnNumber; column++){
                for(int special=0; special<rowNumber; special++){
                    firstOutputs = this.useNetwork(inputs);
                    nodes.get(column).get(row).changeWeight(special,0.001);
                    seccondOutputs = this.useNetwork(inputs);
                    nodes.get(column).get(row).changeWeight(special,-0.001);

                    for(int rrow =0; rrow<rowNumber; rrow++){
                        
                    }
                    //okay, so the partial derivative of the wieght would be cy/cx
                    //so derivatvie list = c in outs / c in ins
                }
            }
        }
    }
}
