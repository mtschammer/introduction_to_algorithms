import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] foundSegments;

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

    // finds all line foundSegments containing 4 points
    public FastCollinearPoints(Point[] points) {
        ArrayList<LineSegment> segments = new ArrayList<>();

        if (points == null) {
            throw new NullPointerException();
        }

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(points);
            Arrays.sort(points, points[i].slopeOrder());

            int j = 1;
            int end;
            while (j < points.length) {
                double slope = this.degenerateCheck(points[0].slopeTo(points[j]));
                int segmentsCounter = 1;

                while (j + segmentsCounter < points.length -1  &&
                        points[0].slopeTo(points[j + segmentsCounter]) == slope) {
                    segmentsCounter++;
                }
                segmentsCounter++;
                if (segmentsCounter > 3) {
                    end = j + segmentsCounter-1;
                    end = end > points.length ? points.length: end;
                    Arrays.sort(points, j, end);

                    int smallest = j;
                    if(points[0].compareTo(points[j]) < 1) smallest = 0;

                    int largest = j + segmentsCounter-2;
                    if(points[0].compareTo(points[largest]) > 0) largest = 0;

                    LineSegment lsNew = new LineSegment(points[smallest],
                            points[largest]);
                    boolean found = false;
                    for (LineSegment lsExisting : segments) {
                        if (lsExisting.toString().equals(lsNew.toString())){
                            found = true;
                        }
                    }

                    if(!found){
                        segments.add(lsNew);
                    }
                } else {
                    end = j+1;
                }

                j = end;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}