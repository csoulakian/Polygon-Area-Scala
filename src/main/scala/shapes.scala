package edu.luc.cs.laufer.cs372.shapes

/**
 * Location, Group code from shapes-android-scala
 *
 * data Shape = Rectangle(w, h) | Polygon(Point*) | Location(x, y, Shape) | Group(Shape*)
 */
sealed trait Shape

/** A point with a specified x and y value. */
case class Point(x: Int, y: Int)

/** A rectangle with specified width and height. */
case class Rectangle(width: Int, height: Int) extends Shape

/** A line segment representing a line between two different points.
  * @param firstP starting point of a line segment
  * @param secondP ending point of a line segment
  */
case class LineSegment(firstP: Point, secondP: Point) {
  require(firstP != secondP || firstP.x != secondP.x
    && firstP.y != secondP.y, "cannot create line segment with the same point")

  /** Finds the equation of a line that goes through the two points
    * of a line segment. If a vertical line is formed and
    * m = +- infinity, then equation is of the form x = b.
    *
    * @return slope-intercept equation y = mx + b as (m, b)
    *         or vertical line equation x = b as (+-Infinity, b)
    */
  def equation(): (Double, Double) = {
    val line: LineSegment = LineSegment.this
    val p1: Point = line.firstP
    val p2: Point = line.secondP
    val m: Double = ((p2.y.toDouble - p1.y) / (p2.x - p1.x)) + 0.00
    val b: Double = m match {
      case 0.00 => p1.y.toDouble
      case Double.NegativeInfinity => p1.x.toDouble
      case Double.PositiveInfinity => p1.x.toDouble
      case _ => p1.y.toDouble - (m * p1.x)
    }
    (m, b)
  }

  /** Determines if a line segment is vertical,
    * meaning it has no slope but its equation returns +- Infinity
    */
  val vertical: Boolean = {
    val line: LineSegment = LineSegment.this
    val eq = line.equation()
    if(eq._1 == Double.NegativeInfinity || eq._1 == Double.PositiveInfinity) true
    else false
  }

  // property of a line segment to indicate it was originally a ray
  val originalRay: Boolean = this.isInstanceOf[Ray]

  /** Determines if a point found on a line is within a particular line segment.
    * Only checks x and y constrains from the line segment.
    *
    * @param point given point found on the line
    * @return true if point is on line segment
    */
  def pointOnLineSeg(point: Point): Boolean = {
    val line: LineSegment = LineSegment.this
    val a = List(line.firstP.x, line.secondP.x)
    val b = List(line.firstP.y, line.secondP.y)
    a.min <= point.x && point.x <= a.max && b.min <= point.y && point.y <= b.max
  }

  def pointOnLine(p: Point): Boolean = {
    val line = LineSegment.this
    val eq = line.equation()
    if(vertical) {
      p.x == eq._2
    }
    else p.x * eq._1 + eq._2 == p.y
  }

  /** Determines if two line segments intersect.
    * Overlapping aspects of algorithm from http://stackoverflow.com/a/16314158
    *
    * @param line2 second line segment
    * @return true if they intersect, otherwise false
    */
  def lineLineIntersect(line2: LineSegment): Boolean = {
    val line1: LineSegment = LineSegment.this
    val eq1 = line1.equation()
    val eq2 = line2.equation()

    // determine if lines are parallel with same slope
    val parallel: Boolean = {
      if (eq1._1 == eq2._1) true
      else false
    }

    // if lines are parallel, check if they overlap
    if(parallel) {
      if(eq1._2 != eq2._2) false
      else if (line1.pointOnLineSeg(line2.firstP) || line1.pointOnLineSeg(line2.secondP) ||
        line2.pointOnLineSeg(line1.firstP) || line2.pointOnLineSeg(line1.secondP)) true
      else false
    }

    // if both lines are vertical, check if they overlap
    else if(line1.vertical && line2.vertical){
      if(line1.firstP.x != line2.firstP.x) false
      else if (line1.pointOnLineSeg(line2.firstP) || line1.pointOnLineSeg(line2.secondP)) true
      else false
    }

    // case for non-parallel lines
    else{
      // finds the point of intersection depending on which line is vertical or neither
      val intersectPx: Int = (line1.vertical, line2.vertical) match {
        case (true, false) => eq1._2.toInt
        case (false, true) => eq2._2.toInt
        case (false, false) => ((eq2._2 - eq1._2) / (eq1._1 - eq2._1)).toInt
        case (true, true) => sys.exit()
      }
      val intersectPy: Int = (line1.vertical, line2.vertical) match {
        case(true, false) => (intersectPx * eq2._1 + eq2._2).toInt
        case _ => (intersectPx * eq1._1 + eq1._2).toInt
      }
      val intP: Point = Point(intersectPx, intersectPy)

      // checks if intersection point is on both line segments
      line1.pointOnLineSeg(intP) && line2.pointOnLineSeg(intP)
    }
  }

}

/** A ray representing a horizontal line segment that starts at a
  * particular point and ends at a point 1000 units away.
  *
  * @param sP the initial starting point of a ray
  */
class Ray(sP: Point) extends LineSegment(sP, Point(sP.x + 1000, sP.y))


/** A simple closed polygon composed of three or more points.
  * Sequence of non-null inputPoints is in val points.
  *
  * @param inputPoints arbitrary number of input points
  */
case class Polygon(inputPoints: Point*) extends Shape {
  val points = inputPoints.filter(_ != null)
  require(points.size > 2, "not enough non-null points!")

  // returns a list of the line segments that compose the polygon
  def polyLineSeg(): List[LineSegment] = {
    val line = this.points.sliding(2).map {case Seq(a, b) => LineSegment(a, b)}.toList
    line :+ LineSegment(this.points.last, this.points(0))
  }
}

/**
 * A decorator for specifying a shape's location.
 * In Scala, this cannot be a case class because we want to inherit from it.
 */
class Location(val x: Int, val y: Int, val child: Shape) extends Shape {
  require(child != null, "null child in " + getClass.getSimpleName)
}
/** The companion object that allows us to use this class like a case class. */
object Location {
  def apply(x: Int, y: Int, child: Shape) = new Location(x, y, child)
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
}
