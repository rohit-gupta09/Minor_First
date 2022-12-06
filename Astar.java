import pathfinding.Node;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Astar {
    public static final int Diagnol_Cost = 14;
    public static final int V_H_Cost = 10;

    private Node[][] grid;
    // We define a priority queue for open Node
    // open node: set of nodes to be evaluated
    // we put node with lowest cost in first
    private PriorityQueue<Node> openNodes;
    private boolean[][] closedNodes;
    private int startI, startJ;
    private int endI, endJ;

    public Astar(int width, int height, int si, int sj, int ei, int ej, int [][] blocks){
        grid = new Node[width][height];
        closedNodes = new boolean[width][height];
        openNodes = new PriorityQueue<Node>((Node c1, Node c2) -> {
            return c1.finalCost < c2.finalCost ? -1 : c1.finalCost>c2.finalCost?1:0;
        });

        startNode(si, sj);
        endNode(ei, ej);

        // init heuristic and node
        for(int i = 0; i<grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = new Node(i, j);
                grid[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j - endJ);//Manhattan distance 
                grid[i][j].solution = false;
            }
        }

        grid[startI][startJ].finalCost = 0;

        // we put the blocks on the grid
        for(int i = 0; i<blocks.length; i++){
            addBlockOnNode(blocks[i][0], blocks[i][1]);
        }
    }

    public void addBlockOnNode(int i, int j){
        grid[i][j] = null;
    }

    public void startNode(int i, int j){
        startI = i;
        startJ = j;
    }

    public void endNode(int i, int j){
        endI = i;
        endJ = j;
    }
    public void updateCostIfNeeded(Node current, Node t, int cost){
        if(t == null || closedNodes[t.i][t.j])
        return;

        int tFinalCost = t.heuristicCost + cost;
        boolean isOpen = openNodes.contains(t);

        if(!isOpen || tFinalCost < t.finalCost){
            t.finalCost = tFinalCost;
            t.parent = current;

            if(!isOpen)
            openNodes.add(t);
        }
    }

    public void process(){
        openNodes.add(grid[startI][startJ]);
        Node current;

        while(true){
            current = openNodes.poll();

            if(current == null)
            break;

            closedNodes[current.i][current.j] = true;

            if(current.equals(grid[endI][endJ]))
            return;

            Node t;

            if (current.i - 1>= 0){
                t = grid[current.i - 1][current.j];
                updateCostIfNeeded(current, t, current.finalCost + V_H_Cost);
            

                if (current.j - 1>= 0){
                    t = grid[current.i - 1][current.j-1];
                    updateCostIfNeeded(current, t, current.finalCost + Diagnol_Cost);
                }

                if (current.j + 1 < grid[0].length){
                    t = grid[current.i - 1][current.j+1];
                    updateCostIfNeeded(current, t, current.finalCost + Diagnol_Cost);
                }
            }
            if (current.j - 1 >= 0){
                t = grid[current.i][current.j-1];
                updateCostIfNeeded(current, t, current.finalCost + V_H_Cost);
            }
            if (current.j + 1 < grid[0].length){
                t = grid[current.i][current.j+1];
                updateCostIfNeeded(current, t, current.finalCost + V_H_Cost);
            }

            if (current.i + 1 < grid.length){
                t = grid[current.i + 1][current.j];
                updateCostIfNeeded(current, t, current.finalCost + V_H_Cost);
        
                if (current.j - 1 >= 0){
                    t = grid[current.i + 1][current.j-1];
                    updateCostIfNeeded(current, t, current.finalCost + Diagnol_Cost);
                }

                if (current.j + 1 < grid[0].length){
                    t = grid[current.i + 1][current.j+1];
                    updateCostIfNeeded(current, t, current.finalCost + Diagnol_Cost);
                }
            }
        }
    }
    public void display(){
        System.out.println("Grid: ");
    
        for(int i = 0; i<grid.length; i++){
            for(int j = 0; j<grid[i].length; j++){
                if(i == startI && j == startJ){
                    System.out.print("SN  "); // Source Node
                }
                else if(i == endI && j == endJ){
                    System.out.print("DN  "); //Destination Node
                }
                else if(grid[i][j]!= null){
                    System.out.printf("%-3d ", 0);
                }
                else{
                    System.out.print("BN  "); //Block Node
               }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void displayScores(){
        System.out.println("Scores for Nodes: ");
    
        for(int i = 0; i<grid.length; i++){
            for(int j = 0; j<grid[0].length; j++){
               if(grid[i][j] != null)
               System.out.printf("%-3d ", grid[i][j].finalCost);
               else
               System.out.print("BN  ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void displaySolution(){
        if(closedNodes[endI][endJ]){
            System.out.print("Path: ");
            Node current = grid[endI][endJ];
            System.out.print(current);
            grid[current.i][current.j].solution = true;
    
            while(current.parent != null){
                System.out.print(" <- "+current.parent);
                grid[current.parent.i][current.parent.j].solution = true;
                current = current.parent;
            }
            System.out.println("\n");
    
            for(int i = 0; i<grid.length; i++){
                for(int j = 0; j<grid[i].length; j++){
                    if(i == startI && j == startJ){
                        System.out.print("SN  "); // Source Node
                    }
                    else if(i == endI && j == endJ){
                        System.out.print("DN  "); //Destination Node
                    }
                    else if(grid[i][j]!= null){
                        System.out.printf("%-3s ", grid[i][j].solution ? "X":"0");
                    }
                    else{
                        System.out.print("BN  "); //Block Node
                    }
                }
                System.out.println();
            }
            System.out.println();       
        }else
            System.out.println("No possible path");
    }
    
    public static void main(String[] args){
        Astar aStar = new Astar(5, 5, 0, 0, 3, 2,
            new int[][]{
            {0,4}, {2,2}, {3,1}, {3,3}, {2,1}, {2,3}
            }
        );
        // Scanner in = new Scanner(System.in);
        // System.out.println("Enter the width of array:");
        // int width = in.nextInt();
        // System.out.println("Enter the height of array:");
        // int height = in.nextInt();
        // System.out.println("Enter the co-ordinates of starting points:");
        // int si = in.nextInt();
        // int sj = in.nextInt();
        // System.out.println("Enter the co-ordinates of ending points:");
        // int ei = in.nextInt();
        // int ej = in.nextInt();
        // System.out.println("Enter the number of blocks:");
        // int n = in.nextInt();
        // System.out.println("Enter the co-ordinates of blocks:");
        // int[][] blocks = new int[n][2];
        // for (int i = 0; i<n; i++){
        //      blocks[i][0] = in.nextInt();
        //      blocks[i][1] = in.nextInt();
        // }
        // Astar aStar = new Astar(width, height, si, sj, ei, ej, blocks);
        System.out.println();
        aStar.display();
        aStar.process(); // Apply A* Algo, 
        aStar.displayScores();
        aStar.displaySolution();
    }
}
