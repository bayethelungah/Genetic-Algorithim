package main;

import processing.core.PApplet;
import processing.core.PVector;

public class Zone {

    private PVector center = new PVector();
    private int width = 100;
    private int height = 100;
    private boolean active = true;
    private boolean resizing = true;
    private float[] coords = new float[4];
    private float xRef = 0.0f;
    private float yRef = 0.0f;
    private int initWidth = width;
    private int initHeight = height;

    public Zone() {
        coords[0] = 0;
        coords[1] = 0;
        coords[2] = 0;
        coords[3] = 0;
    }

    public void show(GeneticAlgorithim screen) {
        screen.stroke(0);
        screen.noFill();
        screen.rect(center.x - (width / 2), center.y - (height / 2), width, height);

    }

    public void set() {
        if (active) {
            active = false;
        } else {
            resizing = false;
        }
    }

    public void updateCoords(PVector mouse) {
        if (active) {
            center = mouse;
            xRef = mouse.x;
            yRef = mouse.y;
        } else if (!active && resizing) {
            width = (int) (mouse.x - xRef) + initWidth;
            height = (int) (mouse.y - yRef) + initHeight;
        }

    }

    public boolean contains(PVector pos) {
        if (pos.x > center.x - (width / 2) && pos.x < center.x + (width / 2) && pos.y < center.y + (height / 2)
                && pos.y > center.y - (height / 2)) {
            return true;
        } else {
            return false;
        }

    }

    public Node[] getClosestDuoToZone(Node[] nodes) {
        Node[] duo = new Node[2];
        float smallestDist = Float.POSITIVE_INFINITY;

        for (Node node : nodes) {
            float dist = PApplet.dist(center.x, center.y, node.getPos().x, node.getPos().y);
            if (dist < smallestDist) {
                dist = smallestDist;
                duo[0] = node;
                smallestDist = 0;
            }
        }

        for (Node node : nodes) {
            float dist = PApplet.dist(center.x, center.y, node.getPos().x, node.getPos().y);
            if (dist < smallestDist && !node.equals(duo[0])) {
                dist = smallestDist;
                duo[1] = node;
            }
        }

        return duo;

    }

}