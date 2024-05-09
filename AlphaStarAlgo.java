import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.LinkedHashMap;

class Node{
    int root_i,root_j;
    double f,g,h;

    public Node(){
        this.root_i = 0;
        this.root_j = 0;
        this.f = 0;
        this.g = 0;
        this.h = 0;
    }
}


public class AlphaStarAlgo{
    int tablerow;
    int tablecol;
    int[] src = {};
    int[] dest = {};

    public double HeuristicEuclideanToInsert(int row,int col,int[] dest){
        return Math.sqrt((row - dest[0]) * (row - dest[0]) + (col - dest[1])*(col - dest[1]));
    }

    public boolean isValid(int row,int col){
            return (row >= 0) && (row < tablerow) && (col >= 0) && (col < tablecol);
    }

    public boolean isItBlocked(int[][] grid,int row,int col){
        return grid[row][col] == 1;
    }

    public boolean pouFtanw(int row,int col,int[] dest){
        return row == dest[0] && col == dest[1];
    }

    public void vresMonopati(Node[][] nodeInfo,int[] dest){
        System.out.println("Path starting...");
        int row = dest[0];
        int col = dest[1];

        Map<int[],Boolean> monopati = new LinkedHashMap<>();
        while(!(nodeInfo[row][col].root_i == row && nodeInfo[row][col].root_j == col)){
            monopati.put(new int[]{row,col},true);
            int tempRow = nodeInfo[row][col].root_i;
            int tempCol = nodeInfo[row][col].root_j;
            row = tempRow;
            col = tempCol;
        }

        monopati.put(new int[]{row,col},true);
        List<int[]> listaMonopatiwn = new ArrayList<>(monopati.keySet());
        listaMonopatiwn.forEach(p -> {
            if(p[0] == 2 || p[0] == 1){
                System.out.println("-> (" + p[0] +", " + (p[1]) + ")");
            }else{
                System.out.println("-> (" + p[0] + ", " + p[1] + ")");
            }
        });
        System.out.println();
    }


    public void alphaStarAlgoSearch(int[][] grid,int[] arxikiKatastasi,int[] telikiKatastasi){
        if(!isValid(arxikiKatastasi[0],arxikiKatastasi[1]) || !isValid(telikiKatastasi[0],telikiKatastasi[1])){
            System.out.println("The source or the destination it's invalid!");
            return;
        }

        if(!isItBlocked(grid, arxikiKatastasi[0], arxikiKatastasi[1]) || !isItBlocked(grid, telikiKatastasi[0], telikiKatastasi[1])){
            System.out.println("The source or the destination you choose is blocked.");
            return;
        }

        boolean[][] kleistaMonopatia = new boolean[tablerow][tablecol];
        Node[][] nodeInfo = new Node[tablerow][tablecol];
        
        for(int i = 0;i < tablerow;i++){
            for(int j = 0;j < tablecol;j++){
                nodeInfo[i][j] = new Node();
                nodeInfo[i][j].f = Double.POSITIVE_INFINITY;
                nodeInfo[i][j].g = Double.POSITIVE_INFINITY;
                nodeInfo[i][j].h = Double.POSITIVE_INFINITY;
                nodeInfo[i][j].root_i = -1;
                nodeInfo[i][j].root_j = -1;
            }
        }

        int i = arxikiKatastasi[0];
        int j = arxikiKatastasi[1];
        nodeInfo[i][j].f = 0;
        nodeInfo[i][j].g = 0;
        nodeInfo[i][j].h = 0;
        nodeInfo[i][j].root_i = i;
        nodeInfo[i][j].root_j = j;

        Map<Double, int[]> anoixtaMonopatia = new HashMap<>();
        anoixtaMonopatia.put(0.0,new int[]{i,j});
        boolean vresTeliki = false;

        while(!anoixtaMonopatia.isEmpty()){
            Map.Entry<Double, int[]> tyx_monopt = anoixtaMonopatia.entrySet().iterator().next();
            anoixtaMonopatia.remove(tyx_monopt.getKey());

            i = tyx_monopt.getValue()[0];
            j = tyx_monopt.getValue()[1];
            kleistaMonopatia[i][j] = true;
            
            double fnew,gnew,hnew;

            if(isValid(i-1,j)){
                if(pouFtanw(i-1, j, telikiKatastasi)){
                    nodeInfo[i-1][j].root_i = i;
                    nodeInfo[i-1][j].root_j = j;
                    System.out.println("The ending node was found");
                    vresMonopati(nodeInfo, telikiKatastasi);
                    vresTeliki = true;
                    return;
                }else if(!kleistaMonopatia[i-1][j] && isItBlocked(grid, i-1, j)){
                    gnew = nodeInfo[i][j].g + 1;
                    hnew = HeuristicEuclideanToInsert(i-1, j, telikiKatastasi);
                    fnew = gnew + hnew;
                    if(nodeInfo[i-1][j].f == Double.POSITIVE_INFINITY || nodeInfo[i-1][j].f > fnew){
                        anoixtaMonopatia.put(fnew,new int[]{i-1,j});
                        nodeInfo[i-1][j].f = fnew;
                        nodeInfo[i-1][j].g = gnew;
                        nodeInfo[i-1][j].h = hnew;
                        nodeInfo[i-1][j].root_i = i;
                        nodeInfo[i-1][j].root_j = j;
                    }
                }
            }
            if(isValid(i+1, j)){
                if(pouFtanw(i+1, j, telikiKatastasi)){
                    nodeInfo[i+1][j].root_i = i;
                    nodeInfo[i+1][j].root_j = j;
                    System.out.println("The ending node was found.");
                    vresMonopati(nodeInfo, telikiKatastasi);
                    vresTeliki = true;
                    return;
                }else if(!kleistaMonopatia[i+1][j] && isItBlocked(grid, i+1, j)){
                    gnew = nodeInfo[i][j].g + 1;
                    hnew = HeuristicEuclideanToInsert(i+1, j, telikiKatastasi);
                    fnew = gnew + hnew;

                    if(nodeInfo[i+1][j].f == Double.POSITIVE_INFINITY || nodeInfo[i+1][j].f > fnew){
                        anoixtaMonopatia.put(fnew,new int[]{i+1,j});
                        nodeInfo[i+1][j].f = fnew;
                        nodeInfo[i+1][j].g = gnew;
                        nodeInfo[i+1][j].h = hnew;
                        nodeInfo[i+1][j].root_i = i;
                        nodeInfo[i+1][j].root_j = j;
                    }
                }   
            }

            if(isValid(i,j+1)){
                if(pouFtanw(i, j+1, telikiKatastasi)){
                    nodeInfo[i][j+1].root_i = i;
                    nodeInfo[i][j+1].root_j = j;
                    System.out.println("The ending node was found.");
                    vresMonopati(nodeInfo, telikiKatastasi);
                    vresTeliki = true;
                    return;
                }else if(!kleistaMonopatia[i][j+1] && isItBlocked(grid, i, j+1)){
                    gnew = nodeInfo[i][j].g + 1;
                    hnew = HeuristicEuclideanToInsert(i,j+1,telikiKatastasi);
                    fnew = gnew + hnew;

                    if(nodeInfo[i][j+1].f == Double.POSITIVE_INFINITY || nodeInfo[i][j+1].f > fnew){
                        anoixtaMonopatia.put(fnew,new int[]{i,j+1});
                        nodeInfo[i][j+1].f = fnew;
                        nodeInfo[i][j+1].g = gnew;
                        nodeInfo[i][j+1].h = hnew;
                        nodeInfo[i][j+1].root_i = i;
                        nodeInfo[i][j+1].root_j = j;
                    }
                }
            }

            if(isValid(i, j-1)){
                if(pouFtanw(i, j-1, telikiKatastasi)){
                    nodeInfo[i][j-1].root_i = i;
                    nodeInfo[i][j-1].root_j = j;
                    System.out.println("The ending node was found.");
                    vresMonopati(nodeInfo, telikiKatastasi);
                    vresTeliki = true;
                    return;
                }else if(!kleistaMonopatia[i][j-1] && isItBlocked(grid, i, j-1)){
                    gnew = nodeInfo[i][j].g + 1;
                    hnew = HeuristicEuclideanToInsert(i,j-1,telikiKatastasi);
                    fnew = gnew + hnew;

                    if(nodeInfo[i][j-1].f == Double.POSITIVE_INFINITY || nodeInfo[i][j-1].f > fnew){
                        anoixtaMonopatia.put(fnew,new int[]{i,j-1});
                        nodeInfo[i][j-1].f = fnew;
                        nodeInfo[i][j-1].g = gnew;
                        nodeInfo[i][j-1].h = hnew;
                        nodeInfo[i][j-1].root_i = i;
                        nodeInfo[i][j-1].root_j = j;
                    }
                }
            }

            if(isValid(i-1, j+1)){
                if(pouFtanw(i-1, j+1, telikiKatastasi)){
                    nodeInfo[i-1][j+1].root_i = i;
                    nodeInfo[i-1][j+1].root_j = j;
                    System.out.println("The ending node was found.");
                    vresMonopati(nodeInfo, telikiKatastasi);
                    vresTeliki = true;
                    return;
                }else if(!kleistaMonopatia[i-1][j+1] && isItBlocked(grid, i-1, j+1)){
                    gnew = nodeInfo[i][j].g + 1.414;
                    hnew = HeuristicEuclideanToInsert(i-1,j+1,telikiKatastasi);
                    fnew = gnew + hnew;

                    if(nodeInfo[i-1][j+1].f == Double.POSITIVE_INFINITY || nodeInfo[i-1][j+1].f > fnew){
                        anoixtaMonopatia.put(fnew,new int[]{i-1,j+1});
                        nodeInfo[i-1][j+1].f = fnew;
                        nodeInfo[i-1][j+1].g = gnew;
                        nodeInfo[i-1][j+1].h = hnew;
                        nodeInfo[i-1][j+1].root_i = i;
                        nodeInfo[i-1][j+1].root_j = j;

                    }
                }
            }

            if(isValid(i-1, j-1)){
                if(pouFtanw(i-1, j-1, telikiKatastasi)){
                    nodeInfo[i-1][j-1].root_i = i;
                    nodeInfo[i-1][j-1].root_j = j;
                    System.out.println("The endin node was found.");
                    vresMonopati(nodeInfo, telikiKatastasi);
                    vresTeliki = true;
                    return;
                }else if(!kleistaMonopatia[i-1][j-1] && isItBlocked(grid, i-1, j-1)){
                    gnew = nodeInfo[i][j].g + 1.414;
                    hnew = HeuristicEuclideanToInsert(i-1, j-1, telikiKatastasi);
                    fnew = gnew + hnew;

                    if(nodeInfo[i-1][j-1].f == Double.POSITIVE_INFINITY || nodeInfo[i-1][j-1].f > fnew){
                        anoixtaMonopatia.put(fnew,new int[]{i-1,j-1});
                        nodeInfo[i-1][j-1].f = fnew;
                        nodeInfo[i-1][j-1].g = gnew;
                        nodeInfo[i-1][j-1].h = hnew;
                        nodeInfo[i-1][j-1].root_i = i;
                        nodeInfo[i-1][j-1].root_j = j;

                    }
                }
            
            }

            if(isValid(i+1 ,j+1)){
                if(pouFtanw(i+1, j+1, telikiKatastasi)){
                    nodeInfo[i+1][j+1].root_i = i;
                    nodeInfo[i+1][j+1].root_j = j;
                    System.out.println("The ending node was found.");
                    vresMonopati(nodeInfo, telikiKatastasi);
                    vresTeliki = true;
                    return;
                }else if(!kleistaMonopatia[i+1][j+1] && isItBlocked(grid, i+1, j+1)){
                    gnew = nodeInfo[i][j].g + 1.414;
                    hnew = HeuristicEuclideanToInsert(i+1, j+1, telikiKatastasi);
                    fnew = gnew + hnew;

                    if(nodeInfo[i+1][j+1].f == Double.POSITIVE_INFINITY || nodeInfo[i+1][j+1].f > fnew){
                        anoixtaMonopatia.put(fnew,new int[]{i+1,j+1});
                        nodeInfo[i+1][j+1].f = fnew;
                        nodeInfo[i+1][j+1].g = gnew;
                        nodeInfo[i+1][j+1].h = hnew;
                        nodeInfo[i+1][j+1].root_i = i;
                        nodeInfo[i+1][j+1].root_j = j;
                    }
                }
            }

            if(isValid(i+1, j-1)){
                if(pouFtanw(i+1, j-1, telikiKatastasi)){
                    nodeInfo[i+1][j-1].root_i = i;
                    nodeInfo[i+1][j-1].root_j = j;
                    System.out.println("The ending node was found.");
                    vresMonopati(nodeInfo, telikiKatastasi);
                    vresTeliki = true;
                    return;
                }else if(!kleistaMonopatia[i+1][j-1] && isItBlocked(grid, i+1, j-1)){
                    gnew = nodeInfo[i][j].g + 1.414;
                    hnew = HeuristicEuclideanToInsert(i+1, j-1, telikiKatastasi);
                    fnew = gnew + hnew;

                    if(nodeInfo[i+1][j-1].f == Double.POSITIVE_INFINITY || nodeInfo[i+1][j-1].f > fnew){
                        anoixtaMonopatia.put(fnew,new int[]{i+1,j-1});
                        nodeInfo[i+1][j-1].f = fnew;
                        nodeInfo[i+1][j-1].g = gnew;
                        nodeInfo[i+1][j-1].h = hnew;
                        nodeInfo[i+1][j-1].root_i = i;
                        nodeInfo[i+1][j-1].root_j = j;
                    }

                }
            }
        }
        if(!vresTeliki){
            System.out.println("Didn't reach final node(situation).");
        }
    }

}

