import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] foundSegments;

    private void nullCheck(Point p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
    }

    private double degenerateCheck(double slope) {
        if (slope == Double.NEGATIVE_INFINITY) {
            throw new java.lang.IllegalArgumentException();
        }
        return slope;
    }

    // finds all line foundSegments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        ArrayList<LineSegment> segments = new ArrayList<>();

        if (points == null) {
            throw new java.lang.NullPointerException();
        }

        Arrays.sort(points);

        for (int i = 0; i < points.length - 3; i++) {
            this.nullCheck(points[i]);
            for (int j = points.length - 1; j > i + 2; j--) {
                this.nullCheck(points[j]);
                for (int k = j - 1; k > i + 1; k--) {
                    this.nullCheck(points[k]);
                    this.degenerateCheck(points[k].slopeTo(points[j]));
                    for (int l = k - 1; l > i; l--) {
                        this.nullCheck(points[l]);
                        this.degenerateCheck(points[l].slopeTo(points[k]));
                        this.degenerateCheck(points[l].slopeTo(points[j]));

                        double slopeQ = this.degenerateCheck(points[i].slopeTo(points[l]));
                        double slopeR = this.degenerateCheck(points[i].slopeTo(points[k]));
                        double slopeS = this.degenerateCheck(points[i].slopeTo(points[j]));

                        if (slopeQ == slopeR && slopeQ == slopeS) {
                            segments.add(new LineSegment(points[i], points[j]));
                        }
                    }
                }
            }
        }

        this.foundSegments = segments.toArray(new LineSegment[segments.size()]);
    }

    // the number of line foundSegments
    public int numberOfSegments() {
        return this.foundSegments.length;
    }

    // the line foundSegments
    public LineSegment[] segments() {
        return this.foundSegments;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}