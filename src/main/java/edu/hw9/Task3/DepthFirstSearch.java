package edu.hw9.Task3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.RecursiveTask;

public class DepthFirstSearch extends RecursiveTask<List<Integer>> {
    private final ConcurrentHashMap<Integer, List<Integer>> graph;
    private final ConcurrentMap<Integer, Boolean> visited;
    private final int node;

    public DepthFirstSearch(ConcurrentHashMap<Integer, List<Integer>> graph,
        ConcurrentMap<Integer, Boolean> visited, int node) {
        this.graph = graph;
        this.visited = visited;
        this.node = node;
    }

    @Override
    protected List<Integer> compute() {
        List<Integer> result = new ArrayList<>();
        if (visited.putIfAbsent(node, true) != null) {
            return result; // Уже посещена
        }

        result.add(node);

        List<DepthFirstSearch> subTasks = new ArrayList<>();
        for (int neighbor : graph.getOrDefault(node, Collections.emptyList())) {
            if (!visited.containsKey(neighbor)) {
                DepthFirstSearch task = new DepthFirstSearch(graph, visited, neighbor);
                task.fork();
                subTasks.add(task);
            }
        }

        for (DepthFirstSearch task : subTasks) {
            result.addAll(task.join());
        }

        return result;
    }

    public static void addEdge(ConcurrentHashMap<Integer, List<Integer>> graph, int src, int dest) {
        graph.computeIfAbsent(src, k -> Collections.synchronizedList(new ArrayList<>())).add(dest);
        graph.computeIfAbsent(dest, k -> Collections.synchronizedList(new ArrayList<>())).add(src);
    }
}
