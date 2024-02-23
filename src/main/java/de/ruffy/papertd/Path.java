package de.ruffy.papertd;

import org.apache.commons.lang3.ObjectUtils;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private final List <Vector> path = new ArrayList<>();



    public Path(List<Vector> path) {
        this.path.addAll(path);

    }

    public Vector getStartPositon() {
        return path.get(0);
    }

    public Vector getPositionOnPath(double distanceMoved) {

        if (distanceMoved < 0) {
            return path.get(0);
        }
        if (distanceMoved > getTotalPathLength()) {
            return null;
        }
        double distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Vector start = path.get(i);
            Vector end = path.get(i + 1);
            double segmentLength = start.distance(end);
            if (distance + segmentLength > distanceMoved) {
                return start.clone().add(end.clone().subtract(start).normalize().multiply(distanceMoved - distance));
            }
            distance += segmentLength;
        }
        return path.get(path.size() - 1);


    }

    private double getTotalPathLength() {

        double length = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            length += path.get(i).distance(path.get(i + 1));
        }
        return length;
    }
}
