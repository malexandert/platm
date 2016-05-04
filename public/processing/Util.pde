/**
 * Simmple graph layout system
 * http://processingjs.nihongoresources.com/graphs
 * This code is in the public domain
 */

// =============================================
//      Some universal helper functions
// =============================================

// universal helper function: get the angle (in radians) for a particular dx/dy
float getDirection(double dx, double dy) {
  // quadrant offsets
  double d1 = 0.0;
  double d2 = PI/2.0;
  double d3 = PI;
  double d4 = 3.0*PI/2.0;
  // compute angle basd on dx and dy values
  double angle = 0;
  float adx = abs((float)dx);
  float ady = abs((float)dy);
  // Vertical lines are one of two angles
  if(dx==0) { angle = (dy>=0? d2 : d4); }
  // Horizontal lines are also one of two angles
  else if(dy==0) { angle = (dx>=0? d1 : d3); }
  // The rest requires trigonometry (note: two use dx/dy and two use dy/dx!)
  else if(dx>0 && dy>0) { angle = d1 + atan(ady/adx); }		// direction: X+, Y+
  else if(dx<0 && dy>0) { angle = d2 + atan(adx/ady); }		// direction: X-, Y+
  else if(dx<0 && dy<0) { angle = d3 + atan(ady/adx); }		// direction: X-, Y-
  else if(dx>0 && dy<0) { angle = d4 + atan(adx/ady); }		// direction: X+, Y-
  // return directionality in positive radians
  return (float)(angle + 2*PI)%(2*PI);
}

// universal helper function: rotate a coordinate over (0,0) by [angle] radians
int[] rotateCoordinate(float x, float y, float angle) {
  int[] rc = {0,0};
  rc[0] = (int)(x*cos(angle) - y*sin(angle));
  rc[1] = (int)(x*sin(angle) + y*cos(angle));
  return rc;
}

// universal helper function for Processing.js - 1.1 does not support ArrayList.addAll yet
void addAll(ArrayList a, ArrayList b) {
  for(Object o: b) {
    a.add(o);
  }
}

// a permutation function for int arrays [1..n]
int[] pi(int n) {
  int[] base = new int[n];
  for (int i = 0; i < n; i++) {
    base[i] = i;
  }
  for (int i = 0; i < n; i++) {
    int k = int(random(i));
    int temp = base[k];
    base[k] = base[i];
    base[i] = temp;
  }
  return base;
}

color[] mixColors(color[] colors, color mix) {
  for (int i = 0; i < colors.length; i++) {
    int r = red(colors[i]);
    int g = green(colors[i]);
    int b = blue(colors[i]);

    r = int((r + red(mix)) / 2);
    g = int((g + green(mix)) / 2);
    b = int((b + blue(mix)) / 2);

    colors[i] = color(r, g, b);
  }
  return colors;
}

// Returns true with probability 1/p and false with probability 1 - 1/p
boolean coinFlip(int p) {
  int seed = int(random(p));
  if (seed == 0) {
    return true;
  }
  return false
}
