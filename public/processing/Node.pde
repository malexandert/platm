class Node {
	ArrayList neighbors = new ArrayList();
  int x;
  int y;
  color nodeColor = 255;

	Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

  void addEdge(Node o) {
    if(!neighbors.contains(o)) {
      neighbors.add(o);
    }
  }

  boolean hasNeighborWithColor(color c) {
    for (Node n: neighbors) {
      if (this.nodeColor == n.nodeColor) {
        return true;
      }
    }
    return false;
  }

	void draw() {
		stroke(0);
    fill(nodeColor);
    ellipse(x, y, radius, radius);
	}
}