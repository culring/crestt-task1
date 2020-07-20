object NodeParser {
  def parseNodes(rows: List[Row]): List[Node] = {
    def getChildren(siblingsLevel: Int, rows: List[Row], siblings: List[Node]): (List[Node], List[Row]) = {
      if(rows.isEmpty || siblingsLevel > 2) {
        return (siblings, rows)
      }
      val currentRow = rows.head
      if(currentRow.level < siblingsLevel) {
        return (siblings, rows)
      }
      // currentLevel == siblingsLevel
      val (children, rowsLeft) = getChildren(siblingsLevel+1, rows.tail, List())
      val currentNode = Node(currentRow.Id, currentRow.name, children)
      getChildren(siblingsLevel, rowsLeft, siblings :+ currentNode)
    }

    val (nodes, _) = getChildren(0, rows, List())
    nodes
  }
}
