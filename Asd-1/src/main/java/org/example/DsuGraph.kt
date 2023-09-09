package org.example

import java.util.Stack

class DsuGraph<T> {
    val adjacencyList: MutableMap<T, MutableSet<T>> = mutableMapOf()
    private var bridgeEdges: MutableSet<Pair<T, T>> = mutableSetOf()
    private var visitedTime: MutableMap<T, Int> = mutableMapOf()
    private var lowTime: MutableMap<T, Int> = mutableMapOf()
    private var time = 0

    fun addNode(node: T) {
        adjacencyList[node] = mutableSetOf()
    }

    fun addEdge(node1: T, node2: T) {
        adjacencyList.getOrPut(node1) { mutableSetOf() }.add(node2)
        adjacencyList.getOrPut(node2) { mutableSetOf() }.add(node1)

        val visited = mutableSetOf<T>()
        val newComponent = mutableSetOf<Pair<T, T>>()
        dfsAddEdge(
            node = node1,
            target = node2,
            visited = visited,
            newComponent = newComponent
        )
        dfsAddEdge(
            node = node2,
            target = node1,
            visited = visited,
            newComponent = newComponent)

        val componentsToRemove: MutableSet<Pair<T, T>> = mutableSetOf()

        for (component in bridgeEdges) {
            if (component.first == node1 && component.second == node2 ||
                component.first == node2 && component.second == node1) {

                componentsToRemove.add(component)
            }
        }

        bridgeEdges -= componentsToRemove
    }

    private fun dfsAddEdge(node: T, target: T, visited: MutableSet<T>, newComponent: MutableSet<Pair<T, T>>) {
        visited.add(node)
        adjacencyList[node]?.forEach { item ->
            if (!visited.contains(item)) {
                if (node == target || item == target) {
                    newComponent.add(node to item)
                }
                dfsAddEdge(
                    node = item,
                    target = target,
                    visited = visited,
                    newComponent = newComponent)
            }
        }
    }

    fun removeEdge(node1: T, node2: T) {
        adjacencyList[node1]?.remove(node2)
        adjacencyList[node2]?.remove(node1)
    }

    fun findConnectedComponents(node: T): MutableSet<T> {
        val component = mutableSetOf<T>()
        dfs(
            node = node,
            visited = mutableSetOf(),
            component = component
        )
        return component
    }

    private fun dfs(
        node: T,
        visited: MutableSet<T>,
        component: MutableSet<T>
    ) {
        visited.add(node)
        component.add(node)
        adjacencyList[node]?.forEach { item ->
            if (!visited.contains(item)) {
                dfs(
                    node = item,
                    visited = visited,
                    component = component,
                )
            }
        }
    }

    fun areNodesConnected(node1: T, node2: T): Boolean {
        return dfsSearch(
            node = node1,
            target = node2,
            visited = mutableSetOf()
        )
    }

    private fun dfsSearch(
        node: T,
        target: T,
        visited: MutableSet<T>
    ): Boolean {
        if (node == target) {
            return true
        }

        visited.add(node)

        adjacencyList[node]?.forEach { item ->
            if (!visited.contains(item)) {
                if (dfsSearch(
                        node = item,
                        target = target,
                        visited = visited,
                    )
                ) {
                    return true
                }
            }
        }

        return false
    }

    //2
    fun findBridges(): Set<Pair<T, T>> {
        bridgeEdges = mutableSetOf()
        visitedTime = mutableMapOf()
        lowTime = mutableMapOf()
        time = 0

        val visited = mutableSetOf<T>()
        adjacencyList.keys.forEach { key ->
            if (!visited.contains(key)) {
                dfsSearchBridges(node = key, parent = null, visited)
            }
        }

        return bridgeEdges
    }

    private fun dfsSearchBridges(
        node: T,
        parent: T?,
        visited: MutableSet<T>
    ) {
        visited.add(node)
        time++
        visitedTime[node] = time
        lowTime[node] = time

        adjacencyList[node]?.forEach { item ->
            if (!visited.contains(item)) {
                dfsSearchBridges(item, node, visited)
                lowTime[node] = minOf(lowTime[node]!!, lowTime[item]!!)

                if (visitedTime[node]!! < lowTime[item]!!) {
                    bridgeEdges.add(node to item)
                }
            } else if (item != parent) {
                lowTime[node] = minOf(lowTime[node]!!, visitedTime[item]!!)
            }
        }
    }

    //3
    fun findBiconnectedComponents(): Set<Set<Pair<T, T>>> {
        bridgeEdges = mutableSetOf()
        visitedTime = mutableMapOf()
        lowTime = mutableMapOf()
        time = 0
        val biconnectedComponents = mutableSetOf<Set<Pair<T, T>>>()
        val visited = mutableSetOf<T>()
        val stack = Stack<Pair<T, T>>()

        adjacencyList.keys.forEach { node ->
            if (!visited.contains(node)) {
                dfsFindBiconnectedComponents(
                    node = node,
                    visited = visited,
                    parent = mutableMapOf(),
                    visitedTime = visitedTime,
                    lowTime = lowTime,
                    stack = stack,
                    biconnectedComponents = biconnectedComponents
                )
            }
        }

        return biconnectedComponents
    }

    private fun dfsFindBiconnectedComponents(
        node: T,
        visited: MutableSet<T>,
        parent: MutableMap<T, T>,
        visitedTime: MutableMap<T, Int>,
        lowTime: MutableMap<T, Int>,
        stack: Stack<Pair<T, T>>,
        biconnectedComponents: MutableSet<Set<Pair<T, T>>>
    ) {
        visited.add(node)
        visitedTime[node] = time
        lowTime[node] = time
        time++

        adjacencyList[node]?.forEach { neighbor ->
            if (!visited.contains(neighbor)) {
                parent[neighbor] = node
                stack.push(node to neighbor)

                dfsFindBiconnectedComponents(
                    neighbor,
                    visited,
                    parent,
                    visitedTime,
                    lowTime,
                    stack,
                    biconnectedComponents
                )

                if (visitedTime[node]!! <= lowTime[neighbor]!!) {
                    val component = mutableSetOf<Pair<T, T>>()
                    var edge: Pair<T, T>?

                    do {
                        edge = stack.pop()
                        component.add(edge)
                    } while (edge != node to neighbor)

                    biconnectedComponents.add(component)
                }

                lowTime[node] = minOf(lowTime[node]!!, lowTime[neighbor]!!)
            } else if (neighbor != parent[node] && visitedTime[neighbor]!! < visitedTime[node]!!) {
                stack.push(node to neighbor)
                lowTime[node] = minOf(lowTime[node]!!, visitedTime[neighbor]!!)
            }
        }
    }

    fun convertToBiconnectedForest(): Set<DsuGraph<T>> {
        val biconnectedComponents = findBiconnectedComponents()
        val biconnectedForest = mutableSetOf<DsuGraph<T>>()

        biconnectedComponents.forEach { component ->
            val graph = DsuGraph<T>()
            component.forEach { edge ->
                val (node1, node2) = edge
                graph.addNode(node1)
                graph.addNode(node2)
                graph.addEdge(node1, node2)
            }
            biconnectedForest.add(graph)
        }

        return biconnectedForest
    }
}
