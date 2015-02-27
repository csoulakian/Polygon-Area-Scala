import edu.luc.cs.laufer.cs372.shapes._
sealed trait Shape
val good = Seq(Point(2,3), Point(5,6), Point(7,4), null)
val simplePolygon = Polygon(good: _*)


val points = simplePolygon.points

val allX: List[Int] = points.map {
  case Point(x,_) => x
}.toList

val allY: List[Int] = points.map {
  case Point(_,y) => y
}.toList

val l: Location = Location(allX.min, allY.min,
  Rectangle(allX.max - allX.min, allY.max - allY.min))


l.x
l.y
l.child
