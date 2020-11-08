package ex0;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class is an implementation of node_data interface.
 * NodeData class implement Set of operations applicable on a
 * node (vertex) in an (unidirectional) unweighted graph.
 *</p>
 * @author itai.lashover
 */

public class NodeData implements node_data {

    /**
     * Each NodeData contains few fields:
     * key : A unique key that is used as each NodeData's ID.
     * tag : A variable that is used in later functions.
     * info : A variable that is used in later functions.
     * ni : HashMap data structure that is used to store this NodeData neighbors.
     * uniqueKey is a static variable that is used to give each NodeData a unique key(id).
     */
    private final int key;
    private int tag;
    private String info;
    private HashMap<Integer, node_data> ni;
    private static int uniqueKey = 0;

    /**
     * default constructor
     */

    public NodeData() {
        this.key = uniqueKey++;
        this.tag = -1;
        this.info = "";
        this.ni = new HashMap<Integer, node_data>();
    }

    /**
     * This method is a deep copy constructor.
     * It builds a new node with the same key,tag and info.
     * Note: This method builds a new and empty HashMap of neighbors.
     * That means the method will not copy the neighbors of this node.
     * @param other      other node that you want to duplicate.
     */
    public NodeData(node_data other) {
        this.key = other.getKey();
        this.tag = other.getTag();
        this.info = new String(other.getInfo());
        this.ni = new HashMap<Integer, node_data>();
    }

    /**
     * This method returns the key (id) associated with this NodeData.
     *
     * @return      this node unique key.
     */
    @Override
    public int getKey() {
        return this.key;
    }

    /**
     * This method returns a collection with all the neighbors NodeData of this NodeData.
     *
     * @return      collections of NodeData.
     */
    @Override
    public Collection<node_data> getNi() {
        return this.ni.values();
    }

    /**
     * This method returns true iff this<==>key are adjacent, as an edge between them.
     *
     * @param key
     * @return      true or false.
     */
    @Override
    public boolean hasNi(int key) {
        return this.ni.containsKey(key);
    }

    /**
     * This method adds the node t to this NodeData.
     * In other words, the method add an edge between this node_data ==> node_data t.
     *
     * @param t
     */
    @Override
    public void addNi(node_data t) {
        if (this != t && !this.hasNi(t.getKey()))
            this.ni.put(t.getKey(), t);
    }

    /**
     * This method removes the edge between this node_data ==> node_data node.
     *
     * @param node
     */
    @Override
    public void removeNode(node_data node) {
        if (this.ni.containsKey(node.getKey())) {
            this.ni.remove(node.getKey());
        }
    }

    /**
     * This method returns the remark (meta data) associated with this NodeData.
     *
     * @return info
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * This method allows changing the remark (meta data) associated with this NodeData.
     *
     * @param s      the new value of the info.
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * This method temporal returns the tag associated with this NodeData.
     * Used in later functions.
     *
     * @return tag
     */
    @Override
    public int getTag() {
        return tag;
    }

    /**
     * This method allows setting the "tag" value for temporal marking a node.
     *
     * @param t      the new value of the tag.
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    /**
     * toString method
     */
    @Override
    public String toString() {
        String str = "[";
        Collection<node_data> keyArr = ni.values();
        Iterator<node_data> iterator = ni.values().iterator();
        while (iterator.hasNext()) {
            node_data n = iterator.next();
            if (!iterator.hasNext()) {
                str += n.getKey() + "] ";
            }
            else {
                str += n.getKey() + ", ";
            }
        }
        return "NodeData: { Key: " + this.key + ", Neighbors: " + str + "}";
    }

}
