import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> foundSegments = new ArrayList<>();
    private ArrayList<EndPoint> endPoints = new ArrayList<>();

    // finds all line foundSegments containing 4 points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }

        Point[] pc = new Point[points.length];
        System.arraycopy(points, 0, pc, 0, points.length);

        Arrays.sort(pc);

        for (int i = 0; i < pc.length; i++) {
            Arrays.sort(pc, i + 1, pc.length,
                    pc[i].slopeOrder());

            int pointCounter = 2;
            double currentSegmentSlope = Double.NEGATIVE_INFINITY;
            for (int j = i + 1; j < pc.length; j++) {
                double currentPointSlope = this.degenerateCheck(pc[i].slopeTo(pc[j]));

                if (currentSegmentSlope == currentPointSlope) {
                    pointCounter++;

                    if (j == pc.length - 1) {
                        saveLineSegment(pointCounter, i, j+1, pc, currentPointSlope);
                    }
                } else {
                    saveLineSegment(pointCounter, i, j, pc, currentPointSlope);
                    pointCounter = 2;
                    currentSegmentSlope = currentPointSlope;
                }
            }
            Arrays.sort(pc, i + 1, pc.length);
        }
    }

    private void saveLineSegment(int pointCounter, int i, int j,
                                 Point[] pc, double currentPointSlope) {
        if (pointCounter > 3) {
            int beginning = j - (pointCounter - 1);
            int end = j - 1;
            Arrays.sort(pc, beginning, j);

            if (pc[i].compareTo(pc[beginning]) < 1) beginning = i;
            if (pc[i].compareTo(pc[end]) > 0) end = i;

            if (!alreadyAdded(currentPointSlope, pc[end])) {
                this.foundSegments.add(new LineSegment(pc[beginning], pc[end]));
                this.endPoints.add(new EndPoint(currentPointSlope, pc[end]));
            }
        }
    }

    private class EndPoint {
        public double slope;
        public Point point;

        public EndPoint(double slope, Point point) {
            this.slope = slope;
            this.point = point;
        }
    }

    private boolean alreadyAdded(double slope, Point endPoint) {
        for (EndPoint ep : this.endPoints) {
            if (ep.point.compareTo(endPoint) == 0 && ep.slope == slope) {
                return true;
            }
        }

        return false;
    }

    private void nullCheck(Point p) {
        if (p == null) {
            throw new NullPointerException();
        }
    }

    private double degenerateCheck(double slope) {
        if (slope == Double.NEGATIVE_INFINITY) {
            throw new IllegalArgumentException();
        }
        return slope;
    }

    // the number of line foundSegments
    public int numberOfSegments() {
        return this.foundSegments.size();
    }

    // the line foundSegments
    public LineSegment[] segments() {
        return this.foundSegments.toArray(new LineSegment[this.foundSegments.size()]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line foundSegments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}