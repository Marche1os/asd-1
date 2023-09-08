package org.example

class DynamicGraph<T> {
    private val data = mutableSetOf<MutableSet<T>>()
    private val edges = mutableSetOf<Pair<T, T>>()

    fun addEdge(vertex1: T, vertex2: T) {
        val (component1, component2) = find(vertex1) to find(vertex2)

        if (component1 == null && component2 == null) {
            val newComponent = mutableSetOf(vertex1, vertex2)
            data.add(newComponent)
        } else if (component1 != null && component2 == null) {
            component1.add(vertex2)
        } else if (component1 == null && component2 != null) {
            component2.add(vertex1)
        } else if (component1 != null && component2 != null && component1 != component2) {
            data.union(component1 + component2)
        }

        edges.add(vertex1 to vertex2)
    }

    fun removeEdge(vertex1: T, vertex2: T) {
        val edgeToRemove = vertex1 to vertex2
        if (edges.contains(edgeToRemove)) {
            edges.remove(edgeToRemove)

            val actualComponents = mutableSetOf<MutableSet<T>>()
            data.forEach { item ->
                if (item.contains(vertex1) && item.contains(vertex2)) {
                    item.remove(vertex1)
                    item.remove(vertex2)
                    if (item.isNotEmpty()) {
                        actualComponents.add(item)
                    }
                } else {
                    actualComponents.add(item)
                }
            }
            data.clear()
            data.addAll(actualComponents)
        }
    }

    fun find(value: T): MutableSet<T>? {
        return data.find { item -> item.contains(value) }
    }

    fun isConnected(vertex1: T, vertex2: T): Boolean {
        val (component1, component2) = find(vertex1) to find(vertex2)
        return component1 != null && component1 == component2
    }
}