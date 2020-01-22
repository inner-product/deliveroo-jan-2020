// Json is an algebraic data type
// Write down the definition.
// Json is
//  an Object or
//  an Array or
//  a Number or
//  a Boolean or
//  a String or
//  Null
// Implement it.

sealed trait Json {
  // True if this Json contains the given json somewhere within the tree.
  //
  // Hint: use methods List.exists and Map.exists
  def contains(j: Json): Boolean =
    this match {
	  case JObject(value) => j == JObject(value) || value.exists{ case (_, v) => v.contains(j) }
	  case a @ JArray(value) => a == j || value.exists(json => json.contains(j))
      // case JNumber(_) | JString(_) | JBoolean(_) | JNull => this == j
	  case n: JNumber => j == n
	  case s: JString => j == s
	  case JBoolean(_) => j == this // JBoolean(value)
	  case JNull => j == JNull
    }

  /**
   * Find the first value associated with the given key within any object within
   * this JSON.
   */
  def find(key: String): Option[Json] =
    this match {
	  case JObject(value) =>  // Use Map.get and Map.find
        value.get(key) match {
	      case Some(value) => Some(value)
	      case None => value.values.find(v => v.find(key).isDefined)
        }
	  case JArray(value) => value.find(json => json.find(key) match {
                                         case Some(_) => true
                                         case None => false
                                       })
	  case JNumber(_) => None
	  case JString(_) => None
	  case JBoolean(_) => None
	  case JNull => None
    }

  def print: String =
    this match {
      case JObject(v) =>
        v.map { case (k, v) => s"$k: ${v.print}" }.mkString("{", ",", "}")
      // Methods on List
      // mkString method: commas and []
      // recursion
      case JArray(v)   => v.map(json => json.print).mkString("[", ",", "]")
      case JNumber(v)  => v.toString
      case JString(v)  => s""""${v}""""
      case JBoolean(v) => v.toString
      case JNull       => "null"
    }
}
final case class JObject(value: Map[String, Json]) extends Json
final case class JArray(value: List[Json]) extends Json
final case class JNumber(value: Double) extends Json
final case class JString(value: String) extends Json
final case class JBoolean(value: Boolean) extends Json
case object JNull extends Json

// Implement a method `print` that prints the Json as Json (to a String)

object JsonExamples {
  val garfield: Json =
    JObject(Map("name" -> JString("Garfield"), "food" -> JString("Lasagne")))

  val jon: Json =
    JObject(
      Map(
        "name" -> JString("Jon"),
        "pets" -> JArray(List(garfield, JString("Odie")))
      )
    )

  val cats: Json =
    JArray(
      List(JString("TopCat"), JString("Garfield"), JString("Keyboard Cat"))
    )
}
