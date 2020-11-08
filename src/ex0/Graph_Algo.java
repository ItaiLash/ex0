package ex0;

import java.util.*;

/**
 * This class is an implementation of graph_algorithms interface.
 * Graph_Algo class implement the "regular" Graph Theory algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected();
 * 3. int shortestPathDist(int src, int dest);
 * 4. List<Node> shortestPath(int src, int dest);
 *
 * @author itai.lashover
 */

public class Graph_Algo implements graph_algorithms {

    /**
     * The only field in the class is a graph on which we want to perform the methods.
     */
    private graph g;

    /**
     * Default constructor
     */
    public Graph_Algo() {
        this.g = new Graph_DS();
    }

    /**
     * Copy constructor(shallow copy)
     */
    public Graph_Algo(graph other) {
        this.g = other;
    }

    /**
     * This method initializes the graph on which this set of algorithms operates.
     * @param g
     */
    @Override
    public void init(graph g) {
        this.g = g;
    }

    /**
     * This method computes a deep copy of this graph.
     * The method does this by using the deep copy constructors in NodeData and in Graph_DS.
     * @return identical graph.
     */
    @Override
    public graph copy() {
        return new Graph_DS(this.g);
    }

    /**
     * This method returns true iff there is a valid path from every node to each other node.
     * The method uses BFS algorithm.
     * Note: BFS method changes the value of each node's tag.
     * Thus the method calls resetTag function that resets the tags that changed.
     * Complexity: O(|V|+|E|), |V|=number of nodes, |E|=number of edges.
     * @return
     */
    @Override
    public boolean isConnected() {
        if (this.g.nodeSize() == 0){return true;}
        boolean b = this.bfs(this.g);
        restTag();
        return b;
    }

    /**
     * This method returns the length of the shortest path between src to dest.
     * Note: if no such path --> returns -1
     * The method uses BFS algorithm.
     * Note2: BFS method changes the value of each node's tag.
     * Thus the method calls resetTag function that resets the tags that changed.
     * Complexity: O(|V|+|E|), |V|=number of nodes, |E|=number of edges.
     * @param src - start node
     * @param dest - end (target) node
     * @return the length of the shortest path between src to dest, -1 if there is no path.
     */
    @Override
    public int shortestPathDist(int src, int dest) {
        if(this.g.getNode(src) == null){
            throw new RuntimeException("This graph does not contain key " + src);
        }
        if(this.g.getNode(dest) == null){
            throw new RuntimeException("This graph does not contain key " + dest);
        }
        if(src == dest){return 0;}
        int dist[] = new int[this.g.nodeSize()];
        node_data pred[] = new node_data[this.g.nodeSize()];
        boolean ans = bfs(pred, dist, this.g.getNode(src), this.g.getNode(dest));
       if (!ans){
           return -1;
       }
        int shortest = dist[this.g.getNode(dest).getTag()];
        restTag();
        return shortest;
    }

    /**
     * This method returns  the shortest path between src to dest - as an ordered List of nodes:
     * src --> n1 --> n2 --> ... --> dest.
     * Note: if no such path --> empty List.
     * The method uses BFS algorithm.
     * Note2: BFS method changes the value of each node's tag.
     * Thus the method calls resetTag function that resets the tags that changed.
     * The method uses BFS algorithm to build a List od nodes: dest --> ... -->src
     * Thus the method need to reverse the list lster.
     * Complexity: O(|V|+|E|), |V|=number of nodes, |E|=number of edges.
     * @param src - start node
     * @param dest - end (target) node
     * @return List of nodes.
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        List<node_data> path = new LinkedList<node_data>();
        if(shortestPathDist(src,dest) == -1){
            return path;
        }
        if(this.g.getNode(src) == null){
            throw new RuntimeException("This graph does not contain key " + src);
        }
        if(this.g.getNode(dest) == null){
            throw new RuntimeException("This graph does not contain key " + dest);
        }
        if(src == dest){
            path.add(this.g.getNode(dest));
            return path;
        }
        int dist[] = new int[this.g.nodeSize()];
        node_data pred[] = new node_data[this.g.nodeSize()];
        bfs(pred, dist, this.g.getNode(src), this.g.getNode(dest));
        List<node_data> reversePath = new LinkedList<node_data>();
        node_data temp = this.g.getNode(dest);
        reversePath.add(temp);
        while(pred[temp.getTag()]!= null){
            reversePath.add(pred[temp.getTag()]);
            temp = pred[temp.getTag()];
        }
        node_data arr[] = reversePath.toArray(node_data[]::new);
        for(int i=arr.length-1 ; i>=0 ; i--){
            path.add(arr[i]);
        }
        restTag();
        return path;
    }

    /**
     * This private method based on breadth-first search.
     * BFS is an algorithm for traversing or searching graph data structures.
     * The method checks whether or not the graph is linked,
     * in other words it checks whether there is a path between each node and each node.
     * The method stored an array of the visited nodes and ensure not to visit them twice.
     * The method stored a queue of the visited nodes:
     * Pop the first node from the queue
     * Check if the node has already been visited, if so skip it
     * Otherwise go to array in node's tag index and mark it as visited and add the node to the queue.
     * Add this node's neighbors to the queue and repeat these steps
     *
     * In order to be able to access the correct cell in the array,
     * the method uses the node's tag whose value is the index of the node in the array.
     * Note: The method change the tags values.
     * After the queue is empty check the visited array,
     * If all the nodes in the graph are marked as visited the method will return true,
     * Otherwise false.
     * Complexity: O(|V|+|E|), |V|=number of nodes, |E|=number of edges.
     * @param g
     * @return
     */

    private boolean bfs(graph g) {
        node_data n = g.getV().iterator().next();
        int counter = 0;
        boolean visited[] = new boolean[g.nodeSize()];
        Queue<node_data> queue = new LinkedList<node_data>();
        n.setTag(counter++);
        queue.add(n);
        visited[n.getTag()] = true;
        while (!queue.isEmpty()) {
            node_data temp = queue.poll();
            Collection<node_data> h = temp.getNi();
            for (node_data next : h) {
                if (next.getTag() == -1) {
                    next.setTag(counter++);
                }
                if (!visited[next.getTag()]) {
                    visited[next.getTag()] = true;
                    queue.add(next);
                }
            }
        }

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * This private method based on breadth-first search.
     * BFS is an algorithm for traversing or searching graph data structures.
     * The method checks whether there is a path between two nodes.
     * To do so it holds an array that represents the shortest path between the source node to the other nodes the function visited.
     * The method should gets 2 empty arrays whose size is the number of nodes in the graph.
     * In addition its stored an array of the visited nodes and ensure not to visit them twice.
     * The method stored a queue of the visited nodes:
     * Pop the first node from the queue
     * Check if the node has already been visited, if so skip it
     * If that node is the one we're searching for, then the search is over.
     * Otherwise, add it to the visited nodes.
     * Add this node's neighbors to the queue and repeat these steps.
     * If the queue is empty ,this means that there is no path between the two nodes.
     *
     * In order to be able to access the correct cell in the three arrays,
     * the method uses the node's tag whose value is the index of the node in the array.
     * Note: The method change the tags values.
     * Complexity: O(|V|+|E|), |V|=number of nodes, |E|=number of edges.
     * @param pred  An array that keep the "predecessor" of each node (through which node the function reached to this node)
     * @param dist  An araay that keep the distance of this node from the source node.
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    private boolean bfs(node_data[] pred, int[] dist, node_data src, node_data dest) {
        int counter = 0;
        boolean isVisited[] = new boolean[this.g.nodeSize()];
        Queue<node_data> queue = new LinkedList<node_data>();
        for (int i = 0; i < this.g.nodeSize(); i++) {
            isVisited[i] = false;
            dist[i] = -1;
            pred[i] = null;
        }

        src.setTag(counter++);
        isVisited[src.getTag()] = true;
        dist[src.getTag()] = 0;
        queue.add(src);

        while (!queue.isEmpty()) {
            node_data temp = queue.poll();
            for (node_data n : temp.getNi()) {
                if (n.getTag() == -1) {
                    n.setTag(counter++);
                }
                if (!isVisited[n.getTag()]) {
                    isVisited[n.getTag()] = true;
                    dist[n.getTag()] = dist[temp.getTag()] + 1;
                    pred[n.getTag()] = temp;
                    queue.add(n);
                }
                if (n == dest) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This private method resets the values of all the tags of the nodes in the graph.
     * Reset the value = change it back to default value: -1
     */
    private void restTag() {
        for (node_data n : this.g.getV()) {
            n.setTag(-1);
        }
    }
}
