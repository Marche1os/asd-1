package org.example

class DsuGraph<T> {
    private val adjacencyList: MutableMap<T, MutableSet<T>> = mutableMapOf()

    fun addEdge(node1: T, node2: T) {
        adjacencyList.getOrPut(node1) { mutableSetOf() }.add(node2)
        adjacencyList.getOrPut(node2) { mutableSetOf() }.add(node1)
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
}

fun main() {
    val graph = DsuGraph<Int>()

    graph.addEdge(1, 2)
    graph.addEdge(2, 3)
    graph.addEdge(3, 4)

    println(graph.areNodesConnected(1, 4)) //must be true

    graph.removeEdge(2, 3)

    println(graph.areNodesConnected(1, 4)) //must be false
}
