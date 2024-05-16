import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;

public class UCS{

	
        public Node source;
        public Node togoNode;
        public int value;
        public Edge[] adjs;


        
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
                public String value;
                public Node root;
                public double pathCost;
                public Edge[] adjs;//ennow ta adjacencies aplws pio syntoma sorry
                public int[][] grid;
                public int x,y;
                public int movesMade;
                public int numbersSh;
                

                public Node(int[][] grid,int x,int y,int new_x,int new_y,int movesMade,String value,Node root){
                        this.root = root;
                        this.value = value;
                        this.grid = new int[grid.length][];
                        for(int i =0;i < grid.length;i++){
                                this.grid[i] = grid[i].clone();
                        }

                        this.grid[x][y] = this.grid[x][y] + this.grid[new_x][new_y];
                        this.grid[new_x][new_y] = this.grid[x][y] - this.grid[new_x][new_y];
                        this.grid[x][y] = this.grid[x][y] - this.grid[new_x][new_y];
                        this.numbersSh = Integer.MAX_VALUE;
                        this.movesMade = movesMade;
                        this.x = new_x;
                        this.y = new_y;

                }

                public String getValue(){
                        return value;
                }
        }

        

        public class Edge{
                public double costValue;
                public Node wheretoNode;

                public Edge(Node wheretoNode,double costValue){
                        this.wheretoNode = wheretoNode;
                        this.costValue = costValue;

                }
        }
        





}