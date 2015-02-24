import edu.luc.cs.laufer.cs372.shapes._
sealed trait Shape
case class Polygon(inputPoints: Point*) extends Shape {
  val points = inputPoints.filter(_ != null)
  require(points.size > 2, "not enough non-null points!")

  def polyLineSeg(): List[LineSegment] = {
    val line = this.points.sliding(2).map {case Seq(a, b) => LineSegment(a, b)}.toList
    line :+ LineSegment(this.points.last, this.points(0))
  }
}
val good = Seq(Point(2,3), Point(5,6), Point(9,12), null)
val simplePolygon = Polygon(good: _*)
simplePolygon.polyLineSeg()

