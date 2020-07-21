package xlsx2node

import java.io.InputStream

import org.apache.poi.ss.usermodel.{DataFormatter, WorkbookFactory}

import scala.jdk.CollectionConverters._

object Xlsx2NodeParser {
  def parse(inputStream: InputStream): List[Node] = {
    val rows = readXlsx(inputStream)
    val parsedRows = parseToRows(rows)
    parseToNodes(parsedRows)
  }

  def readXlsx(inputStream: InputStream): List[List[Option[String]]] = {
    val workbook = WorkbookFactory.create(inputStream)
    val sheet = workbook.getSheetAt(0)
    val formatter = new DataFormatter()
    val output = for(row <- sheet.asScala.drop(1)) yield {
      for(i <- List.range(0, 4)) yield {
        val cell = row.getCell(i, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)
        if(cell != null) Some(formatter.formatCellValue(cell))
        else None
      }
    }
    inputStream.close()
    output.toList
  }

  def parseToRows(rows: List[List[Option[String]]]): List[Row] = {
    for(row <- rows) yield {
      val Id = row(3).get.toInt
      val level = extractLevelFromRow(row)
      val name = row(level).get
      Row(Id, level, name)
    }
  }

  private def extractLevelFromRow(row: List[Option[String]]): Int = {
    row.indexWhere(cell =>
      cell match {
        case Some(_) => true
        case None => false
      }
    )
  }

  def parseToNodes(rows: List[Row]): List[Node] = {
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
