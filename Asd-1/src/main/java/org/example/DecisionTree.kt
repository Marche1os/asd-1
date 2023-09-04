package org.example

import kotlin.math.ln

val dataSet = listOf(
    PlayTennisData(mapOf("outlook" to "Sunny", "temperature" to "Hot", "humidity" to "High", "wind" to "Weak"), false),
    PlayTennisData(
        mapOf("outlook" to "Sunny", "temperature" to "Hot", "humidity" to "High", "wind" to "Strong"),
        false
    ),
    PlayTennisData(
        mapOf("outlook" to "Overcast", "temperature" to "Hot", "humidity" to "High", "wind" to "Weak"),
        true
    ),
    PlayTennisData(mapOf("outlook" to "Rain", "temperature" to "Mild", "humidity" to "High", "wind" to "Weak"), true),
    PlayTennisData(mapOf("outlook" to "Rain", "temperature" to "Cool", "humidity" to "Normal", "wind" to "Weak"), true),
    PlayTennisData(
        mapOf("outlook" to "Rain", "temperature" to "Cool", "humidity" to "Normal", "wind" to "Strong"),
        false
    ),
    PlayTennisData(
        mapOf("outlook" to "Overcast", "temperature" to "Cool", "humidity" to "Normal", "wind" to "Strong"),
        true
    ),
    PlayTennisData(mapOf("outlook" to "Sunny", "temperature" to "Mild", "humidity" to "High", "wind" to "Weak"), false),
    PlayTennisData(
        mapOf("outlook" to "Sunny", "temperature" to "Cool", "humidity" to "Normal", "wind" to "Weak"),
        true
    ),
    PlayTennisData(mapOf("outlook" to "Rain", "temperature" to "Mild", "humidity" to "Normal", "wind" to "Weak"), true),
    PlayTennisData(
        mapOf("outlook" to "Sunny", "temperature" to "Mild", "humidity" to "Normal", "wind" to "Strong"),
        true
    ),
    PlayTennisData(
        mapOf("outlook" to "Overcast", "temperature" to "Mild", "humidity" to "High", "wind" to "Strong"),
        true
    ),
    PlayTennisData(
        mapOf("outlook" to "Overcast", "temperature" to "Hot", "humidity" to "Normal", "wind" to "Weak"),
        true
    ),
    PlayTennisData(mapOf("outlook" to "Rain", "temperature" to "Mild", "humidity" to "High", "wind" to "Strong"), false)
)

class DecisionTree {

    fun calculateEntropy(positive: Double, negative: Double, total: Double = positive + negative): Double {
        val log2 = { v1: Double, v2: Double ->
            if (v1 == 0.0) {
                1.0
            } else {
                ln(v1 / (v1 + v2)) / Math.log(2.0)
            }
        }

        return -(positive / total * log2(positive, negative) + negative / total * log2(negative, positive))
    }

    fun calculateConditionalEntropy(
        data: List<PlayTennisData>,
        parentValue: String
    ): Double {
        val total = data.size.toDouble()
        var positiveCount = 0.0
        var negativeCount = 0.0

        data
            .filter { it.data.keys.contains(parentValue) }
            .forEach { item ->
                val isPlay: Boolean? = item.isPlay
                if (isPlay == true) {
                    positiveCount++
                } else {
                    negativeCount++
                }
            }

        return calculateEntropy(positiveCount, negativeCount, total)
    }

    fun calculateGain(dataPoints: List<PlayTennisData>, extractor: (PlayTennisData) -> Pair<String, Boolean>): Double {
        val (positive, negative) = dataPoints.map { extractor(it) }.partition { it.second }
        val attributes = dataPoints.map { extractor(it) }
            .groupBy { it.first }
            .map {
                val (positiveSv, negativeSv) = it.value.partition { it.second }
                Triple(it.key, positiveSv.size.toDouble(), negativeSv.size.toDouble())
            }
        return (calculateEntropy(positive.size.toDouble(), negative.size.toDouble())
                + attributes.sumOf { -calculateEntropy(it.second, it.third, dataPoints.size.toDouble()) })

    }

    fun build(dataPoints: List<PlayTennisData>): Tree {
        val (positive, negative) = dataPoints.partition { it.isPlay!! }

        if (positive.isEmpty()) return Tree.Leaf
        if (negative.isEmpty()) return Tree.Leaf

        val attributes = dataPoints.map { it.data.keys }
            .flatten()
            .distinct()

        if (attributes.size == 1) {
            return if (dataPoints.count { it.isPlay!! } > dataPoints.count { !it.isPlay!! }) Tree.Leaf else Tree.Leaf
        } else {
            val (attribute) = attributes.map { attr ->
                Pair(
                    attr,
                    calculateGain(dataPoints) { Pair(it.data[attr].toString(), it.isPlay!!) }
                )
            }.maxByOrNull { it.second }!!

            val remaining = dataPoints.groupBy { it.data[attribute] }
            val filteredRemaining = remaining.entries.map { entry ->
                Pair(entry.key, entry.value.map { dataPoint ->
                    PlayTennisData(dataPoint.data.filterKeys { it != attribute }, dataPoint.isPlay)
                })
            }

            val children = filteredRemaining.map { Pair(it.first.toString(), build(it.second)) }.toMap();
            return Tree.Node
        }
    }
}

data class PlayTennisData(
    val data: Map<String, Any?>,
    val isPlay: Boolean? = null
) {
    val outlook: String by data
    val temperature: String by data
    val humidity: String by data
    val wind: String by data
}

sealed class Tree {
    data object Leaf : Tree()
    data object Node : Tree()
}