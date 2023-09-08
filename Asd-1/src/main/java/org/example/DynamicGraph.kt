package org.example

class DynamicGraph<T> {
    private val components = mutableSetOf<MutableSet<Node<T>>>()
    private val edges = mutableSetOf<Pair<T, T>>()

    fun addEdge(vertex1: T, vertex2: T) {
        val (component1, component2) = find(vertex1) to find(vertex2)

        if (component1 == null && component2 == null) {
            val newComponent = mutableSetOf(Node(vertex1), Node(vertex2))
            components.add(newComponent)
        } else if (component1 != null && component2 == null) {
            component1.add(vertex2.asNode())
        } else if (component1 == null && component2 != null) {
            component2.add(vertex1.asNode())
        } else if (component1 != null && component2 != null && component1 != component2) {
            components.union(component1 + component2)
        }

        edges.add(vertex1 to vertex2)
    }

    fun removeEdge(vertex1: T, vertex2: T) {
        val edgeToRemove = vertex1 to vertex2
        if (edges.contains(edgeToRemove)) {
            edges.remove(edgeToRemove)

            val actualComponents = mutableSetOf<MutableSet<Node<T>>>()
            val nodeVertex1 = vertex1.asNode()
            val nodeVertex2 = vertex2.asNode()
            components.forEach { item ->
                if (item.contains(nodeVertex1) && item.contains(nodeVertex2)) {
                    item.remove(nodeVertex1)
                    item.remove(nodeVertex2)
                    if (item.isNotEmpty()) {
                        actualComponents.add(item)
                    }
                } else {
                    actualComponents.add(item)
                }
            }
            components.clear()
            components.addAll(actualComponents)
        }
    }

    fun find(value: T): MutableSet<Node<T>>? {
        return components.find { item -> item.contains(value.asNode()) }
    }

    fun isConnected(vertex1: T, vertex2: T): Boolean {
        val (component1, component2) = find(vertex1) to find(vertex2)
        return component1 != null && component1 == component2
    }

    data class Node<T>(
        val value: T,
        var visited: Boolean = false
    )

    private fun <T> T.asNode(): Node<T> {
        return Node(value = this)
    }
}

fun main() {
    val graph = DynamicGraph<Int>()
    graph.addEdge(1, 2)
    graph.addEdge(2, 3)
    graph.addEdge(4, 5)

    println(graph.isConnected(1, 2))
    println(graph.isConnected(1, 3))
    println(graph.isConnected(1, 4))
}