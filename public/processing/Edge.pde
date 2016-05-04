class Edge {
	Node n1;
  Node n2;

  Edge(Node n1, Node n2) {
    this.n1 = n1;
    this.n2 = n2;
  }

  void draw() {
    line(n1.x, n1.y, n2.x, n2.y);
    stroke(255);
  }
}