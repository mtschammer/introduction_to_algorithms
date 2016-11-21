import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> foundSegments = new ArrayList<>();

    // finds all line foundSegments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.NullPointerException();
        }

        Point[] pointsCopied = new Point[points.length];
        System.arraycopy(points, 0, pointsCopied, 0, points.length);

        Arrays.sort(pointsCopied);

        for (int i = 0; i < pointsCopied.length; i++) {
            this.nullCheck(pointsCopied[i]);
            for (int j = i+1; j < pointsCopied.length; j++) {
                this.nullCheck(pointsCopied[j]);
                this.degenerateCheck(pointsCopied[i].slopeTo(pointsCopied[j]));
                for (int k = j + 1; k < pointsCopied.length; k++) {
                    this.nullCheck(pointsCopied[k]);
                    this.degenerateCheck(pointsCopied[i].slopeTo(pointsCopied[k]));
                    for (int l = k + 1; l < pointsCopied.length; l++) {
                        this.nullCheck(pointsCopied[l]);

                        double slopeQ = this.degenerateCheck(pointsCopied[i].slopeTo(pointsCopied[l]));
                        double slopeR = pointsCopied[i].slopeTo(pointsCopied[k]);
                        double slopeS = pointsCopied[i].slopeTo(pointsCopied[j]);

                        if (slopeQ == slopeR && slopeQ == slopeS) {
                            foundSegments.add(new LineSegment(pointsCopied[i], pointsCopied[l]));
                        }
                    }
                }
            }
        }
    }

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}