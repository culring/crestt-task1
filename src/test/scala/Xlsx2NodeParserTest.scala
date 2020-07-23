import org.scalatest.FunSuite
import xlsx2node.Node
import xlsx2node.Xlsx2NodeParser

class Xlsx2NodeParserTest extends FunSuite {
  test("Xlsx2NodeParser.parse task_example") {
    val inputStream = getClass.getResourceAsStream("/task_example.xlsx")
    val nodes = Xlsx2NodeParser.parse(inputStream)
    assert(nodes == List(Node(1, "A", List(Node(2, "AA", List(Node(3, "AA1", List()), Node(4, "AA2", List()))), Node(5, "AB", List()))), Node(6, "B", List()), Node(7, "C", List(Node(8, "CA", List(Node(9, "CA1", List()), Node(10, "CA2", List()))))), Node(11, "D", List(Node(12, "DA", List())))))
  }

  test("Xlsx2NodeParser.parse empty_list") {
    val inputStream = getClass.getResourceAsStream("/empty_list.xlsx")
    val nodes = Xlsx2NodeParser.parse(inputStream)
    assert(nodes == List())
  }

  test("Xlsx2NodeParser.parse single_node") {
    val inputStream = getClass.getResourceAsStream("/single_node.xlsx")
    val nodes = Xlsx2NodeParser.parse(inputStream)
    assert(nodes == List(Node(1, "A", List())))
  }

  test("Xlsx2NodeParser.parse random_example") {
    val inputStream = getClass.getResourceAsStream("/random_example.xlsx")
    val nodes = Xlsx2NodeParser.parse(inputStream)
    assert(nodes == List(Node(1, "A", List(Node(2, "B", List(Node(3, "C", List()))), Node(4, "D", List()))), Node(5, "E", List())))
  }
}