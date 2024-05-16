public class Node{
                public String value;
                public Node root;
                public double pathCost;
                //public Edge[] adjs;//ennow ta adjacencies aplws pio syntoma sorry
                public int[][] grid;
                public int x,y;
                public int movesMade;
                public int numbersSh;
                //public Edge[] adjs;

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

                public class Edge{
                        public double costValue;
                        public Node wheretoNode;
        
                        public Edge(Node wheretoNode,double costValue){
                                this.wheretoNode = wheretoNode;
                                this.costValue = costValue;
        
                        }
                }
        }