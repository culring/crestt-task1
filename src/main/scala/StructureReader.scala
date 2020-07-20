import java.io.InputStream

object StructureReader {
  def read(inputStream: InputStream): List[Node] = {
    val rows = XlsxReader.read(inputStream)
    val parsedRows = RowParser.parse(rows)
    NodeParser.parseNodes(parsedRows)
  }
}
