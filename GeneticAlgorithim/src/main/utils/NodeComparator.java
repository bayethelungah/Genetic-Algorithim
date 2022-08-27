package main.utils;

import java.util.Comparator;
import main.Node;

public class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node node1, Node node2) {
        if (node1.getSpeed() == node2.getSpeed()) {
            return 0;
        } else if (node1.getSpeed() > node2.getSpeed()) {
            return 1;
        } else {
            return -1;
        }
    }

}