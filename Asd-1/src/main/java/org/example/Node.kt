package org.example

import java.util.*

data class Node(
    val character: Char?,
    val frequency: Int,
    val left: Node? = null,
    val right: Node? = null
)

class HuffmanCode {
    fun encode(text: String): Map<Char, Int> {
        val frequencies = mutableMapOf<Char, Int>()
        text
            .replace(' ', '_')
            .forEach {
                frequencies[it] = frequencies.getOrDefault(it, 0) + 1
            }

        val root = buildTree(frequencies)
        val huffmanCodes = buildCodes(root)

        return huffmanCodes.map { it.key to it.value.toInt() }
            .asReversed()
            .toMap()
    }

    private fun buildTree(frequencies: MutableMap<Char, Int>): Node {
        val priorityQueue = PriorityQueue<Node> { n1, n2 -> n1.frequency - n2.frequency }

        frequencies.forEach { (char, freq) ->
            priorityQueue.offer(
                Node(
                    character = char,
                    frequency = freq,
                )
            )
        }

        while (priorityQueue.size > 1) {
            val left = priorityQueue.poll()
            val right = priorityQueue.poll()

            val prior = left.frequency + right.frequency
            val node = Node(
                character = null,
                frequency = prior,
                left = left,
                right = right
            )
            priorityQueue.offer(node)
        }

        return priorityQueue.poll()
    }

    private fun buildCodes(root: Node): Map<Char, String> {
        val huffmanCodes = mutableMapOf<Char, String>()
        traverse(root, "", huffmanCodes)
        return huffmanCodes
    }

    private fun traverse(root: Node, code: String, huffmanCodes: MutableMap<Char, String>) {
        if (root.character != null) {
            huffmanCodes[root.character] = code
        } else {
            traverse(root.left!!, code + "0", huffmanCodes)
            traverse(root.right!!, code + "1", huffmanCodes)
        }
    }
}