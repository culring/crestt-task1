import java.io.InputStream

import org.apache.poi.ss.usermodel.{DataFormatter, Row, WorkbookFactory}

import scala.jdk.CollectionConverters._

object XlsxReader {
  def read(inputStream: InputStream): List[List[Option[String]]] = {
    val workbook = WorkbookFactory.create(inputStream)
    val sheet = workbook.getSheetAt(0)
    val formatter = new DataFormatter()
    val output = for(row <- sheet.asScala.drop(1)) yield {
      for(i <- List.range(0, 4)) yield {
        val cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)
        if(cell != null) Some(formatter.formatCellValue(cell))
        else None
      }
    }
    inputStream.close()
    output.toList
  }
}
