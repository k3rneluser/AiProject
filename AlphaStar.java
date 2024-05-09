import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.LinkedHashMap;

public class Node{
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

    public boolean pouFtanw(int row,int coln,int[] dest){
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
        List<Int[]> listaMonopatiwn = new ArrayList<>(monopati.keySet());
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
        
    }
       
}

