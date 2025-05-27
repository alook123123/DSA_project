package pathfinding;

import java.awt.Point;
import java.util.*;

public class AStar {
    private boolean[][] walkableMap;
    private int rows, cols;

    private static class Node {
        int x, y;
        int g;
        int h;
        int f;
        Node parent;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public AStar(boolean[][] walkableMap) {
        this.walkableMap = walkableMap;
        this.rows = walkableMap.length;
        this.cols = walkableMap[0].length;
    }

    public List<Point> findPath(int startX, int startY, int goalX, int goalY) {
        if (!isValid(startX, startY) || !isValid(goalX, goalY) || !walkableMap[goalX][goalY]) {
            return null;
        }

        PriorityQueue<Node> openList = new PriorityQueue<>((a, b) -> a.f - b.f);
        boolean[][] closedList = new boolean[rows][cols];
        Node startNode = new Node(startX, startY);
        startNode.g = 0;
        startNode.h = manhattanDistance(startX, startY, goalX, goalY);
        startNode.f = startNode.g + startNode.h;
        openList.add(startNode);

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            int x = current.x;
            int y = current.y;

            if (x == goalX && y == goalY) {
                return reconstructPath(current);
            }

            closedList[x][y] = true;

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (isValid(newX, newY) && !closedList[newX][newY] && walkableMap[newX][newY]) {
                    int newG = current.g + 1;
                    Node neighbor = new Node(newX, newY);
                    neighbor.g = newG;
                    neighbor.h = manhattanDistance(newX, newY, goalX, goalY);
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.parent = current;

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }
        return null;
    }

    private int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    private List<Point> reconstructPath(Node node) {
        List<Point> path = new ArrayList<>();
        while (node != null) {
            path.add(new Point(node.x, node.y));
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }
}