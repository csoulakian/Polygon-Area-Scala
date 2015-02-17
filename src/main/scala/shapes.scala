package edu.luc.cs.laufer.cs372.shapes

/**
 * Circle, Location, Group code from shapes-android-scala
 *
 * data Shape = Rectangle(w, h) | Location(x, y, Shape)
 */
sealed trait Shape

/** A point with a specified x and y value. */
case class Point(x: Int, y: Int)

/** A line segment representing a line between two different points. */
case class LineSegment(firstP: Point, secondP: Point) {
  require(firstP != secondP || firstP.x != secondP.x
    && firstP.y != secondP.y, "cannot create line segment with the same point")
}

case class Ray(startingP: Point) {
  def apply(sP: Point): LineSegment = LineSegment(sP, Point(sP.x + 500, sP.y))
}

/**
 * A decorator for specifying a shape's location.
 * In Scala, this cannot be a case class because we want to inherit from it.
 */
class Location(val x: Int, val y: Int, val child: Shape) extends Shape {
  require(child != null, "null child in " + getClass.getSimpleName)
  override def equals(o: Any) = o match {
    case that: Location => this.x == that.x && this.y == that.y && this.child == that.child
    case _ => false
  }
}
/** The companion object that allows us to use this class like a case class. */
object Location {
  def apply(x: Int, y: Int, child: Shape) = new Location(x, y, child)
  def unapply(l: Location) = Some((l.x, l.y, l.child))
}

/**
 * A composite for grouping edu.luc.etl.cs313.android.shapes.model.
 * In Scala, this cannot be a case class because we want to inherit from it.
 */
class Group(val children: Shape*) extends Shape {
  require(children != null, "null children in " + getClass.getSimpleName)
  require(! children.contains(null), "null child in " + getClass.getSimpleName)
}
/** The companion object that allows us to use this class like a case class. */
object Group {
  def apply(children: Shape*) = new Group(children: _*)
  def unapply(g: Group) = Some(g.children)
}

// TODO add missing case classes (see test fixtures)
// TODO must include validity checking for constructor arguments
