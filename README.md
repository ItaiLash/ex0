# Assignment 0

### Introduction
This project is an assignment in an object-oriented course at Ariel University.
The project consists of six classes, 3 interfaces and 3 implementations that I will detail below.
This assignment is an infrastructure of data structure and algorithms for the duration of the course.
The task implements a data structure of an unweighted and undirectional graph.
The project implements a number of algorithms on the graph including the ability to duplicate a graph, check if the graph is linked, calculate a short path with a minimum of edges and find the shortest path between two vertices in the graph.
In the paragraphs below I will detail the classes in the project.

## NodeData class

NodeData class is an implementation of node_data interface.
NodeData class implement Set of operations applicable on a
node (vertex) in an (unidirectional) unweighted graph.
Each NodeData contains few fields:
* key : A unique key that is used as each NodeData's ID.
* tag & info : Variables that use some of the functions (represent position in the array, etc.)
* ni : HashMap data structure that is used to store each NodeData neighbors(by neighbor I mean another node that has an edge with this node).

In fact each NodeData contains a list of all the nodes that it has an edge with.
The list of neighbors is implemented by HashMap.
I chose to use HashMap data structure because it is easy to store data with the help of the unique key of each node and in addition it allows quick access to each of the neighbors of the node O(1).
Detailed explanation about HashMap:
https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html

### main methods 

* getKey : returns the key (id) associated with this NodeData - O(1).
* getNi : returns a collection with all the neighbors of this NodeData - O(1).
* hasNi : checks if the nodes are adjacent (there is an edge between them) - O(1).
* addNi : add an edge between the nodes (in other words the method adds the node to this node HashMap) - O(1).
* removeNode: removes the edge between the nodes (in other words the method delete the node from this node HashMap) - O(1)


## Graph_DS class

This class is an implementation of graph interface.
Graph_DS class implement an undirectional and unweighted graph.
It support a large number of nodes (over 10^6, with average degree of 10).
This implementation is also based on HashMap data structure.
Each Graph_DS contains few fields:
*‫ ‬g : HashMap data structure that represent a graph, used to store all the node_data in the graph.
‫*‬ numOfEdge : A variable that stored the amount of edges in this graph.
‫*‬ numOfNode : A variable that stored the amount of nodes in this graph.
‫*‬ mc : Mode Count, a variable that stored the amount of changes(add node, remove node, add edge, remove edge)made in this graph.

In fact each Graph_DS contains a list of all the nodes in the graph,
And at the same time each node contains a list of its neighbors(=edges).

### main methods 

*nodeDeepCopy : private method gets a graph and return a duplicate of his HashMap (In fact the method deep copies only the nodes without the edges).
* edgeDeepCopy : private method gets a graph and adds to this HashMap the same edges(In fact the method deep copies only the edges assuming the nodes are already in the graph).
* getNode : return the node_data by the node unique key - O(1).
* hasEdge : returns true if and only if there is an edge between node1 and node2 - O(1).
* addNode : adds a new node to the graph with the given node_data - O(1).
* connect : connects an edge between node1 and node2 (if the edge node1-node2 already exists or node1=node2 the method simply does nothing) - O(1).
* getV : returns a pointer (shallow copy) for the collection representing all the nodes in the graph - O(1).
* getV : returns a collection of the neighbors of the node by his key - O(1).
* removeNode : delete the node (with the given ID) from the graph and removes all edges which starts or ends at this node - O(n), |v|=n.
* removeEdge : delete the edge node1-node2 from the graph (if the edge does not exists in the graph the method simply does nothing) - O(1).
* nodeSize : returns the number of nodes in the graph - O(1).
* edgeSize : returns the number of edges in the graph - O(1).
* getMC : returns the Mode Count for testing changes in the graph - O(1).


## Graph_Algo class

This class is an implementation of graph_algorithms interface.
Graph_Algo class implement some well known algorithms on a graph.
The only filed in the class is graph g on which we want to activate the methods.

### main methods 

* init : initializes the graph on which this set of algorithms operates - O(1).
* copy : computes a deep copy of this graph using Graph_DS copy constructor that used NodeData copy constructor - O(n^2), |V|=n.
* isConnected : returns true iff there is a valid path from every node to each other node, The method uses BFS algorithm that will be detailed below ‫-‬ O(|V|+|E|), |V|=number of nodes, |E|=number of edges.
* shortestPathDist : returns the length of the shortest path between src to dest, returns -1 if no such path. Also used BFS algorithm - O(|V|+|E|), |V|=number of nodes, |E|=number of edges.
* shortestPath : returns  the shortest path between src to dest - as an ordered List of nodes:src --> n1 --> n2 --> ... --> dest - O(|V|+|E|), |V|=number of nodes, |E|=number of edges.
bfs(graph g) : private method based on breadth-first search, BFS is an algorithm for traversing or searching graph data structures.
The method checks whether or not the graph is linked, in other words it checks whether there is a path between each node and each node.
The method stored an array of the visited nodes and ensure not to visit them twice and stored a queue of the visited nodes:
1. Pop the first node from the queue
2. Check if the node has already been visited, if so skip it
  Otherwise go to array in node's tag index and mark it as visited and add the node to the queue.
3. Add this node's neighbors to the queue and repeat these steps
In order to be able to access the correct cell in the array, the method uses the node's tag whose value is the index of the node in the array.
Complexity: O(|V|+|E|), |V|=number of nodes, |E|=number of edges.
* bfs(node_data[] pred, int[] dist, node_data src, node_data dest) : private method based on breadth-first search.
To do so it holds an array that represents the shortest path between the source node to the other nodes the function visited.
The method should gets 2 empty arrays whose size is the number of nodes in the graph.
In addition its stored an array of the visited nodes and ensure not to visit them twice and stored a queue of the visited nodes:
1. Pop the first node from the queue
2. Check if the node has already been visited, if so skip it
3. If that node is the one we're searching for, then the search is over.
  Otherwise, add it to the visited nodes.
4. Add this node's neighbors to the queue and repeat these steps.
If the queue is empty ,this means that there is no path between the two nodes.
In order to be able to access the correct cell in the three arrays, the method uses the node's tag whose value is the index of the node in the array.
Complexity: O(|V|+|E|), |V|=number of nodes, |E|=number of edges.
restTag : private method resets the values of all the tags of the nodes in the graph.
Reset the value = change it back to default value: -1.
This method should be used immediately after each use of bfs method as it changes the node's tags.
