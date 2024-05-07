import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;

public class UCS{

	public static void main(String[] args){

	}
        Node source;
        Node togoNode;
        int value;



        
        public static void UCS(Node source,Node togoNode){
                //ArrayList<Node> myTable = new ArrayList<Node>();
                source.pathCost = 0;
                PriorityQueue<Node> queue = new PriorityQueue<Node>(9,new Comparator<Node>(){
                        public int compare(Node i,Node j){
                                if(i.pathCost > j.pathCost){
                                        return 1;
                                }else if(i.pathCost < j.pathCost){
                                        return -1;
                                }else{
                                        return 0;
                                }

                        }
                });

                queue.add(source);
                ArrayList<Node> explored = new ArrayList<Node>();//i lista me osous episkefthkame 
                boolean havitOrNo = false;

                do{
                        Node current = queue.poll();
                        explored.add(current);

                        if(current.value.equals(togoNode.value)){
                                havitOrNo = true;
                        }

                        
                        for(Edge edge: current.adjs){
                                Node child = edge.wheretoNode;
                                double kostos = edge.costValue;

                                if(!explored.contains(child) && !queue.contains(child)){
                                        child.pathCost = current.pathCost + kostos;
                                        child.root = current;
                                        queue.add(child);
                                }else if((queue.contains(child))&&(child.pathCost>(current.pathCost+kostos))){
                                        child.root = current;
                                        child.pathCost = current.pathCost + kostos;
                                        queue.remove(child);
                                        queue.add(child);    
                                }
                        }
                }while(!queue.isEmpty()&&(havitOrNo==false));
        }

        public void printMyTable(int tableToBeMade[][]){
                for(int i =0;i < tableToBeMade.length;i++){
                        for(int j = 0; j < tableToBeMade.length;j++){
                                System.out.println(tableToBeMade[i][j] + " ");
                        }
                        System.out.println("");

                }

        }

        public class Node{
                String value;
                Node root;
                double pathCost;
                Edge[] adjs;//ennow ta adjacencies aplws pio syntoma sorry
                
                

                public Node(String value){
                        this.value = value;
                }

                public String getValue(){
                        return value;
                }
        }

        public class Edge{
                double costValue;
                Node wheretoNode;

                public Edge(Node wheretoNode,double costValue){
                        this.wheretoNode = wheretoNode;
                        this.costValue = costValue;

                }
        }
        





}