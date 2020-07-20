object Main extends App {
  val inputStream = getClass.getResourceAsStream("Scala_zadanie.xlsx")
  val nodes = StructureReader.read(inputStream)
  val nodesJson = JsonConverter.convert(nodes)
  println(nodesJson)
}