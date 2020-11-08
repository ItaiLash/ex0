package ex0;

import java.util.Collection;
import java.util.HashMap;

/**
 * This class is an implementation of graph interface.
 * Graph_DS class implement an undirectional unweighted graph.
 * It support a large number of nodes (over 10^6, with average degree of 10).
 * This implementation based on HashMap data structure.
 *
 * @author itai.lashover
 */

public class Graph_DS implements graph {

    /**
     * Each Graph_DS contains few fields:
     * g : HashMap data structure that represent a graph, used to store all the node_data in the graph.
     * numOfEdge : A variable that stored the amount of edges in this graph.
     * numOfNode : A variable that stored the amount of nodes in this graph.
     * mc : Mode Count a variable that stored the amount of changes(add node, remove node, add edge, remove edge)made in this graph.
     */

    private HashMap<Integer, node_data> g;   // node: <key,s> = <K,V>
    private int numOfEdge;
    private int numOfNode;
    private int mc;

    /**
     * default constructor
     */
    public Graph_DS() {
        this.g = new HashMap<Integer, node_data>();
        this.numOfEdge = 0;
        this.numOfNode = 0;
    }

    /**
     * This method is a deep copy constructor.
     * It's build a new Graph_DS with the same numOfEdge,numOfNode and mc.
     * Note: This constructor does not build a new HashMap.
     * The constructor call another method "edgeDeepCopy" that copy this HashMap.
     * @param gra      other graph that you want to duplicate.
     */
    public Graph_DS(graph gra) {
        this.g = nodeDeepCopy(gra);
        edgeDeepCopy(gra);
        this.numOfEdge = gra.edgeSize();
        this.numOfNode = gra.nodeSize();
        this.mc = gra.getMC();
    }

    /**
     * This private method gets a graph and return a duplicate of his HashMap.
     * At first the method build a new and empty HashMap.
     * Then the method add a deep copy nodes to the new HashMap in the right keys.
     * Note: This method build a new and empty HashMap of nodes.
     * That means the method will not copy the edges, just the vertices.
     * Complexity: O(n) , |V|=n.
     * @param other      other graph that you want to duplicate his HashMap.
     * @return HashMap   new and identical HashMap.
     */
    private HashMap<Integer,node_data> nodeDeepCopy(graph other) {
        HashMap<Integer, node_data> h = new HashMap<Integer, node_data>();
        for (node_data n : other.getV()) {
            h.put(n.getKey(), new NodeData(n));
        }
        return h;
    }

    /**
     * This private method gets a graph and adds to this HashMap the same edges.
     * Note: The method will used only after we have used the previous "nodeDeepCopy" method.
     * Thus in the beginning of the method we already have a HashMap with nodes.
     * All that is left is to connect the right nodes.
     * The method check which nodes connected in the other graph and connect them in this graph.
     * Complexity: O(n^2) , |V|=n.
     * @param other      other graph that you want to duplicate his HashMap.
     * @return HashMap   new and identity HashMap.
     */
    private void edgeDeepCopy(graph other){
        Collection<node_data> valArr = other.getV();
        for(node_data n : valArr){
            Collection<node_data> niArr = n.getNi();
            for(node_data n2 : niArr){
                this.connect(n.getKey(), n2.getKey());
            }
        }
    }


    /**
     * This method return the node_data by the node unique key,
     * @param key - the node unique key.
     * @return the node_data, null if none.
     */
    @Override
    public node_data getNode(int key) {
        if (this.g.isEmpty() || !this.g.containsKey(key)) {
            return null;
        }
        return this.g.get(key);
    }

    /**
     * This method returns true iff (if and only if) there is an edge between node1 and node2.
     * Complexity: this method run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        node_data n = this.g.get(node1);
        return n.hasNi(node2);
    }

    /**
     * This method adds a new node to the graph with the given node_data.
     * Complexity: this method run in O(1) time.
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        g.put(n.getKey(), n);
        this.mc++;
        this.numOfNode++;
    }

    /**
     * This method connects an edge between node1 and node2.
     * Complexity: this method run in O(1) time.
     * Note: if the edge node1-node2 already exists - the method simply does nothing.
     * Note2: if node1=node2 - the method does nothing.
     */
    @Override
    public void connect(int node1, int node2) {
        node_data n1 = getNode(node1);
        node_data n2 = getNode(node2);
        if(!n1.hasNi(node2) && !n2.hasNi(node1) && node1!=node2) {
            n1.addNi(n2);
            n2.addNi(n1);
            mc++;
            this.numOfEdge++;
        }
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * Complexity: this method run in O(1) time.
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV() {
        return g.values();
    }

    /**
     * This method returns a collection of the neighbors of the node by his key.
     * This collection represents all the nodes connected to node_id
     * Complexity: this method run in O(1) time.
     * @paran node_id
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV(int node_id) {
        return this.g.get(node_id).getNi();
    }

    /**
     * This method delete the node (with the given ID) from the graph
     * and removes all edges which starts or ends at this node.
     * Complexity: This method run in O(n), |V|=n, as all the edges should be removed.
     * Note: if the node (with the given ID) does not exists - the method simply does nothing.
     * @return the removed node (null if none).
     * @param key
     */
    @Override
    public node_data removeNode(int key) {
        if (g.containsValue(g.get(key))) {
            node_data temp = g.get(key);
            Collection<node_data> valArr = temp.getNi();
            for (node_data n : valArr) {
                n.removeNode(temp);
                this.numOfEdge--;
                this.mc++;
            }
            g.remove(key);
            this.numOfNode--;
            mc++;
            return temp;
        }
        return null;
    }

    /**
     * This method delete the edge node1<==>node2 from the graph.
     * Complexity: this method run in O(1) time.
     * Note: if the edge does not exists in the graph - the method simply does nothing.
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        node_data n1 = this.g.get(node1);
        node_data n2 = this.g.get(node2);
        if (n1.hasNi(node2) && n2.hasNi(node1) && node1 != node2) {
            n1.removeNode(n2);
            n2.removeNode(n1);
            mc++;
            this.numOfEdge--;
        }
    }

    /** This method returns the number of nodes in the graph.
     * Complexity: this method run in O(1) time.
     * @return
     */
    @Override
    public int nodeSize() {
        return this.numOfNode;
    }

    /**
     * This method returns the number of edges (undirectional graph).
     * Complexity: this method run in O(1) time.
     * @return
     */
    @Override
    public int edgeSize() {
        return this.numOfEdge;
    }

    /**
     * This method returns the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph cause an increment in the ModeCount.
     * Complexity: O(1).
     * @return
     */
    @Override
    public int getMC() {
        return this.mc;
    }

    /**
     * toString method
     */
    @Override
    public String toString() {
        return "Graph_DS{" +
                "g=" + g +
                '}';
    }

}
