Graph g = new Graph();
ArrayList edgeArray = new ArrayList();
color[] baseColors = [color(255,0,0), color(0,255,255), color(128,255,0)];
color[] graphColors = mixColors(baseColors, color(255,255,255));
int currentNode = 0;
int[] order = pi(numNodes);
boolean isThreeColored = true;

void setup() {
  size(sketchWidth, sketchHeight);
  makeEdgeArray();
  makeGraph();
  frameRate(30);
}

void draw() {
  background(0);
  g.draw();
  if (currentNode == numNodes && !certifyThreeColor(g)) {
    for (Node n: g.nodes) {
      n.nodeColor = 255;
    }
    order = pi(numNodes);
    currentNode = 0;
  } else if (currentNode < numNodes) {
    colorNextNode(order[currentNode]);
    currentNode = (currentNode + 1);
  }
}

void makeGraph() {
  for (int i = 0; i < numNodes; i++) {
    g.addNode(new Node(int(random(20, width-20)), int(random(20, height-20))));
  }

  for (int[] e: edgeArray) {
    int coin = coinFlip(20);
    if (coin) {
      g.addEdge((Node) g.nodes.get(e[0]), (Node) g.nodes.get(e[1]));
    }
  }
}

void makeEdgeArray() {
  for (int i = 0; i < numNodes; i++) {
    for (int j = i; j < numNodes; j++) {
      if (j != i) {
        edgeArray.add([i,j]);
      }
    }
  }
}

void colorNextNode(int i) {
  int numColor =  int(random(graphColors.length));
  g.nodes.get(i).nodeColor = graphColors[numColor];
}

void certifyThreeColor(Graph g) {
  for (Edge e: g.edges) {
    if (e.n1.nodeColor == e.n2.nodeColor) {
      return false;
    }
  }
  return true;
}
