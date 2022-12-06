package pathfinding;

public class Node {
    public int i, j;  // coordinates
    public Node parent; // parent node for path
    public int heuristicCost;
    public int finalCost;
    public boolean solution;

    public Node(int i, int j){
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString(){
        return "["+i+","+j+"]";
    }
}
