package org.example

class EulerGraph<T> {
    private val adjacency = mutableMapOf<T, MutableList<T>>()
    private val eulerPath = mutableListOf<T>()

    fun addEdge(u: T, v: T) {
        adjacency.computeIfAbsent(u) { mutableListOf() }.add(v)
        adjacency.computeIfAbsent(v) { mutableListOf() }.add(u)
    }

    fun findEulerPath(): List<T> {
        val startNode = findStartNode()
        dfs(startNode)
        return eulerPath
    }

    private fun findStartNode(): T {
        return adjacency.filter { it.value.size % 2 == 0 }
            .map { it.key }
            .firstOrNull()
            ?: adjacency.keys.first()
    }

    private fun dfs(node: T) {
        val neighbours = adjacency[node]
        while (!neighbours.isNullOrEmpty()) {
            val nextNode = neighbours.removeLast()
            adjacency[nextNode]?.remove(node)
            dfs(nextNode)
        }
        eulerPath.add(node)
    }

    fun buildEulerGraphFrom(tree: Map<T, List<T>>) {
        for ((node, neighbours) in tree) {
            for (neighbour in neighbours) {
                addEdge(node, neighbour)
            }
        }
    }

    fun buildEulerPath(graph: MutableMap<T, MutableList<T>>, startNode: T): List<T> {
        clear()
        adjacency.putAll(graph)
        dfs(startNode)
        return eulerPath
    }

    fun changeRootOfGraph(oldRoot: T, newRoot: T): List<T> {
        val oldPath = buildEulerPath(adjacency, oldRoot)
        val index = oldPath.indexOf(newRoot)
        val newPath = oldPath.subList(index, oldPath.size - 1).toMutableList()

        newPath.addAll(oldPath.subList(1, index))

        return newPath
    }

    fun bindTwoGraphs(graph1: Map<T, List<T>>, graph2: Map<T, List<T>>): EulerGraph<T> {
        val newGraph = EulerGraph<T>()

        newGraph.buildEulerGraphFrom(graph1)
        newGraph.buildEulerGraphFrom(graph2)

        return newGraph
    }

    fun isNodesConnected(node1: T, node2: T): Boolean {
        return adjacency[node1]?.contains(node2) ?: false
    }

    private fun clear() {
        adjacency.clear()
        eulerPath.clear()
    }
}