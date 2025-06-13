package edu.hw9.task3;

import edu.hw9.Task3.DepthFirstSearch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.assertThat;

class DepthFirstSearchTest {

    private List<Integer> runDFS(ConcurrentHashMap<Integer, List<Integer>> graph, int startNode) {
        ForkJoinPool pool = new ForkJoinPool();
        ConcurrentMap<Integer, Boolean> visited = new ConcurrentHashMap<>();
        DepthFirstSearch dfs = new DepthFirstSearch(graph, visited, startNode);
        return pool.invoke(dfs);
    }

    @Test
    @DisplayName("Линейный граф: все вершины должны быть пройдены в правильном порядке")
    void linearGraphTraversal() {
        var graph = new ConcurrentHashMap<Integer, List<Integer>>();
        DepthFirstSearch.addEdge(graph, 0, 1);
        DepthFirstSearch.addEdge(graph, 1, 2);
        DepthFirstSearch.addEdge(graph, 2, 3);

        List<Integer> traversal = runDFS(graph, 0);

        assertThat(traversal).containsExactly(0, 1, 2, 3);
    }

    @Test
    @DisplayName("Граф с циклом: обход должен корректно обрабатывать цикл")
    void cyclicGraphTraversal() {
        var graph = new ConcurrentHashMap<Integer, List<Integer>>();
        DepthFirstSearch.addEdge(graph, 0, 1);
        DepthFirstSearch.addEdge(graph, 1, 2);
        DepthFirstSearch.addEdge(graph, 2, 0); // цикл

        List<Integer> traversal = runDFS(graph, 0);

        assertThat(traversal).containsExactlyInAnyOrder(0, 1, 2);
    }

    @Test
    @DisplayName("Граф с несколькими путями: все вершины должны быть посещены")
    void graphWithMultiplePaths() {
        var graph = new ConcurrentHashMap<Integer, List<Integer>>();
        DepthFirstSearch.addEdge(graph, 0, 1);
        DepthFirstSearch.addEdge(graph, 0, 2);
        DepthFirstSearch.addEdge(graph, 1, 3);
        DepthFirstSearch.addEdge(graph, 2, 3);

        List<Integer> traversal = runDFS(graph, 0);

        assertThat(traversal).containsExactlyInAnyOrder(0, 1, 2, 3);
    }

    @Test
    @DisplayName("Дисконнектный граф: должны быть посещены только достижимые вершины")
    void disconnectedGraphTraversal() {
        var graph = new ConcurrentHashMap<Integer, List<Integer>>();
        DepthFirstSearch.addEdge(graph, 0, 1);
        DepthFirstSearch.addEdge(graph, 2, 3);

        List<Integer> traversal = runDFS(graph, 0);

        assertThat(traversal).containsExactlyInAnyOrder(0, 1);
    }

    @Test
    @DisplayName("Большой граф: проверка корректной работы с порогом многопоточности")
    void thresholdBehaviorForLargeGraphs() {
        var graph = new ConcurrentHashMap<Integer, List<Integer>>();
        for (int i = 0; i < 20; i++) {
            for (int j = i + 1; j < 20; j++) {
                DepthFirstSearch.addEdge(graph, i, j);
            }
        }

        List<Integer> traversal = runDFS(graph, 0);

        assertThat(traversal).containsExactlyInAnyOrderElementsOf(graph.keySet());
    }

    @Test
    @DisplayName("Граф с множеством ветвлений: обход с параллельными сабтасками")
    void parallelExecutionWithMultipleBranches() {
        var graph = new ConcurrentHashMap<Integer, List<Integer>>();
        for (int i = 1; i <= 4; i++) {
            DepthFirstSearch.addEdge(graph, 0, i);
        }

        List<Integer> traversal = runDFS(graph, 0);

        assertThat(traversal).containsExactlyInAnyOrder(0, 1, 2, 3, 4);
    }
}
