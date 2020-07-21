package task1

import xlsx2node.Xlsx2NodeParser

object Main extends App {
  val inputStream = getClass.getResourceAsStream("/Scala_zadanie.xlsx")
  val nodes = Xlsx2NodeParser.parse(inputStream)
  val nodesJson = JsonConverter.convert(nodes)
  println(nodesJson)
}
