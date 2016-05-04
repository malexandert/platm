class Graph {
  ArrayList nodes = new ArrayList();
  ArrayList edges = new ArrayList();

  void addNode(Node node) {
    if(!nodes.contains(node)) {
      nodes.add(node);
    }
  }

  void addEdge(Node n1, Node n2) {
    if (nodes.contains(n1) && nodes.contains(n2)) {
      n1.addEdge(n2);
      n2.addEdge(n1);
      edges.add(new Edge(n1, n2));
    }
  }

  int size() {
    return nodes.size();
  }

  ArrayList getNodes() {
    return nodes;
  }

  void draw() {
    for (Edge e: edges) {
      e.draw();
    }
    for (Node n: nodes) {
      n.draw();
    }
  }
}