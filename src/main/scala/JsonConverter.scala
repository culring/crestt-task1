import play.api.libs.json.{Json, OWrites}
import xlsx2node.Node

object JsonConverter {
  def convert(nodes: List[Node]): String = {
    implicit val nodesWrites: OWrites[Node] = Json.writes[Node]
    val nodesJsonList = nodes.map(node => Json.toJson(node))
    Json.toJson(nodesJsonList).toString()
  }
}
