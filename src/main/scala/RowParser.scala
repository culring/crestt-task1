object RowParser {
  def parse(rows: List[List[Option[String]]]): List[Row] = {
    for(row <- rows) yield {
      val Id = row(3).get.toInt
      val level = getLevel(row)
      val name = row(level).get
      Row(Id, level, name)
    }
  }

  def getLevel(row: List[Option[String]]): Int = {
    row.indexWhere(cell =>
      cell match {
        case Some(_) => true
        case None => false
      }
    )
  }
}
