import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> segmentList = new ArrayList<>();
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        Point[] aux = Arrays.copyOf(points, points.length);
        for (int i = 0; i < aux.length; i++) {
            Point p = points[i];
            Arrays.sort(aux);
            Arrays.sort(aux, p.slopeOrder());
            int min = 1;
            int max = min;
            while (min < aux.length) {
                while (max < aux.length && Double.compare(p.slopeTo(aux[min]), p.slopeTo(aux[max])) == 0) max++;
                if (max - min >= 3) {
                    Point pMin = (p.compareTo(aux[min]) < 0) ? aux[min] : p;
                    Point pMax = (p.compareTo(aux[max]) > 0) ? aux[max] : p;
                    if (p == pMin) segmentList.add(new LineSegment(pMin, pMax));
                }
                min = max;
            }
        }

    }     // finds all line segments containing 4 or more points
    public int numberOfSegments() {
        return segmentList.size();
    }      // the number of line segments
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[segmentList.size()];
        return segmentList.toArray(segments);
    }                // the line segments
}
