
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Comparator;


public class EightPuzzle{

    public class UCS {

        public static void ucsSearch(EightPuzzle arxiki, EightPuzzle teliki) {
            arxiki.setPathCost(0); // Set initial path cost to 0
            PriorityQueue<EightPuzzle> frontier = new PriorityQueue<>(Comparator.comparingInt(EightPuzzle::getPathCost));
            Set<EightPuzzle> explored = new HashSet<>();
            frontier.add(arxiki);
    
            while (!frontier.isEmpty()) {
                EightPuzzle current = frontier.poll();
                explored.add(current);
    
                if (current.isGoalState()) {
                    System.out.println("Goal state reached:");
                    current.printMyTable(null);
                    return;
                }
    
                List<EightPuzzle> successors = current.getSuccessors();
                for (EightPuzzle successor : successors) {
                    if (!explored.contains(successor) && !frontier.contains(successor)) {
                        frontier.add(successor);
                    } else if (frontier.contains(successor) && successor.getPathCost() < current.getPathCost()) {
                        frontier.remove(successor);
                        frontier.add(successor);
                    }
                }
            }
            System.out.println("Goal state not reachable.");
        }
    }
    

    public int pathCost;
    public int[][] grid;
    public int dimension = 3;
    public int[][] telikikatastasi = {
        {0,1,2},
        {3,4,5},
        {6,7,8}
    };
    public int[][] moves = {
        {1, 3}, {0, 2, 4}, {1, 5}, {0, 4, 6}, {1, 3, 5, 7}, {2, 4, 8}, {3, 7}, {4, 6, 8}, {5, 7}
};

    

    public EightPuzzle(int[][] arxiki){
        grid = arxiki;
    }

    public void printMyTable(int[][] grid){
        for(int i = 0;i < grid.length;i++){
            for(int j = 0;j < grid.length;j++){
                System.out.println(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getPathCost(){
        return pathCost;
    }
    
    public boolean isGoalState() {
        return Arrays.equals(flatten(grid),flatten(telikikatastasi));
    }

    public void setPathCost(int cost){
        this.pathCost = cost;
    }
    
    private int[] flatten(int[][] array) {
        int[] flat = new int[array.length * array[0].length];
        for (int i = 0; i < array.length; i++) {
            System.arraycopy(array[i], 0, flat, i * array.length, array[i].length);
        }
        return flat;
    }

    private int[][] unflatten(int[] array) {
        int[][] unflat = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(array, i * 3, unflat[i], 0, 3);
        }
        return unflat;
    }

    public List<EightPuzzle> getSuccessors() {
        List<EightPuzzle> successors = new ArrayList<>();
        int[] flatBoard = flatten(grid);
        int zeroIndex = -1;
        for (int i = 0; i < flatBoard.length; i++) {
            if (flatBoard[i] == 0) {
                zeroIndex = i;
                break;
            }
        }
        if (zeroIndex != -1) {
            for (int move : moves[zeroIndex]) {
                int[] newFlat = Arrays.copyOf(flatBoard, flatBoard.length);
                newFlat[zeroIndex] = flatBoard[move];
                newFlat[move] = 0;
                successors.add(new EightPuzzle(unflatten(newFlat)));
            }
        }
        return successors;
    }


    public void printPath(Node root){
        if(root == null){
            return ;
        }
        printPath(root.root);
        printMyTable(root.grid);
        System.out.println();
    }
    
    

    public boolean isSolvable(int[][] grid) {
		int count = 0;
		List<Integer> array = new ArrayList<Integer>();
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				array.add(grid[i][j]);
			}
		}
		
		Integer[] anotherArray = new Integer[array.size()];
		array.toArray(anotherArray);
		
		for (int i = 0; i < anotherArray.length - 1; i++) {
			for (int j = i + 1; j < anotherArray.length; j++) {
				if (anotherArray[i] != 0 && anotherArray[j] != 0 && anotherArray[i] > anotherArray[j]) {
					count++;
				}
			}
		}
		
		return count % 2 == 0;
	}

    public int[][] getGrid(){
        return grid;
    }
    

    public static  int[] findEmptyPosition(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null; // Not found
    }



    public static void main(String[] args){
        int[][] arxiki = {
            {1,2,3},
            {0,5,6},
            {4,7,8}
        };
        int[][] teliki = {
            {0,1,2},
            {3,4,5},
            {6,7,8}
        };
    
        
    
        EightPuzzle puzzle = new EightPuzzle(arxiki);
        System.out.println("Starting point:");
        puzzle.printMyTable(arxiki);
        System.out.println("Intended ending situation:");
        for (int[] row : teliki) {
            System.out.println(Arrays.toString(row));
        }

        solvePuzzleAStar(puzzle);
        solvePuzzleUCS(puzzle);
    }

    public static void solvePuzzleAStar(EightPuzzle puzzle){
        AlphaStarAlgo alphaStarAlgo = new AlphaStarAlgo();
        int[][] gridBoard = puzzle.getGrid();
        int[] arxikiKatastasi = findEmptyPosition(gridBoard);
        int[] telikiKatastasi = {2, 2}; 

        alphaStarAlgo.tablerow = gridBoard.length;
        alphaStarAlgo.tablecol = gridBoard[0].length;

        alphaStarAlgo.alphaStarAlgoSearch(gridBoard, arxikiKatastasi, telikiKatastasi);
    }

    public static void solvePuzzleUCS(EightPuzzle puzzle){
        int[][] arxikiKatastasi = {
            {1,2,3},
            {4,8,0},
            {5,6,7}
        };


        int[][] telikiKatastasi = {
            {0,1,2},
            {3,4,5},
            {6,7,8}
        };

        EightPuzzle startingGrid = new EightPuzzle(arxikiKatastasi);
        EightPuzzle endingGrid = new EightPuzzle(telikiKatastasi);
        System.out.println("Starting point:");
        startingGrid.printMyTable(startingGrid.grid);
        System.out.println("Reached ending situation:");
        endingGrid.printMyTable(telikiKatastasi);

    }

    
    
}

