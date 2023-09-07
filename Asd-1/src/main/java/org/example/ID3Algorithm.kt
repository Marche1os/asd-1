package org.example

class ID3Algorithm {
    private val decisionTree = DecisionTree()

    /**
     * Result:
     * key = outlook
     * children = {Sunny=Node(key=humidity, children={High=Leaf(isPlay=false), Normal=Leaf(isPlay=true)}), Overcast=Leaf(isPlay=true), Rain=Node(key=wind, children={Weak=Leaf(isPlay=true), Strong=Leaf(isPlay=false)})}
     *
     */
    fun build(criterias: List<PlayTennisData>): Tree {
        val (yesPlay, noPlay) = criterias.partition { it.isPlay!! }

        if (yesPlay.isEmpty())
            return Tree.Leaf(false)
        if (noPlay.isEmpty())
            return Tree.Leaf(true)

        val features = criterias
            .asSequence()
            .flatMap { it.data.keys }
            .distinct()
            .toList()


        val node = features.takeIf { it.size > 1 }
            ?.let {
                val (feature, _) = it.map { feature ->
                    Pair(
                        feature,
                        decisionTree.calculateGain(
                            criterias
                        ) { tennisData ->
                            Pair(
                                tennisData.data[feature].toString(),
                                tennisData.isPlay!!
                            )
                        }
                    )
                }.maxByOrNull { it.second }!!

                val rest = criterias.groupBy { it.data[feature] }
                    .entries
                    .map { entry ->
                        Pair(
                            entry.key,
                            entry.value.map {tennisData ->
                                PlayTennisData(
                                    tennisData.data.filterKeys { key -> key != feature },
                                    tennisData.isPlay
                                )
                            }
                        )
                    }
                val children = rest.map { (key, tennisData) ->
                    Pair(
                        key.toString(),
                        build(
                            tennisData
                        )
                    )
                }

                Tree.Node(
                    children = children.toMap(),
                    key = feature
                )
            }
        return features.takeIf { it.size == 1 }
            ?.let {
                val countOfPlay = criterias.count { it.isPlay!! }
                Tree.Leaf(
                    isPlay = countOfPlay > criterias.size / 2
                )
            } ?: node!!
    }

}

sealed class Tree {
    data class Leaf(val isPlay: Boolean) : Tree()
    data class Node(val key: String, val children: Map<String, Tree>?) : Tree()
}