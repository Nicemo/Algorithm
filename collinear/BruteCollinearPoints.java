import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] segments;
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        List<LineSegment> lst = new ArrayList<>();
        Point[] cp = points.clone();
        Arrays.sort(cp);
        int pSize = points.length;
        for (int i = 0; i < pSize; i++) {
            for (int j = i + 1; j < pSize; j++) {
                for (int h = j + 1; h < pSize; h++) {
                    for (int k = h + 1; k < pSize; k++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[h];
                        Point s = points[k];
                        Double slopeOfpq = p.slopeTo(q);
                        Double slopeOfpr = p.slopeTo(r);
                        Double slopeOfps = p.slopeTo(s);
                        if (slopeOfpq == slopeOfpr && slopeOfpq == slopeOfps) {
                            lst.add(new LineSegment(p, s));
                        }
                    }
                }
            }
        }
        segments = lst.toArray(new LineSegment[lst.size()]);
    }// finds all line segments containing 4 points
    public int numberOfSegments() {
        return segments.length;
    }        // the number of line segments
    public LineSegment[] segments() {
        return segments;
    }                // the line segments
}
