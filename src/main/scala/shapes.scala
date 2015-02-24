package edu.luc.cs.laufer.cs372.shapes

import edu.luc.cs.laufer.cs372.shapes

/**
 * Location, Group code from shapes-android-scala
 *
 * data Shape = Rectangle(w, h) | Location(x, y, Shape)
 */
sealed trait Shape

/** A point with a specified x and y value. */
case class Point(x: Int, y: Int)

/** A line segment representing a line between two different points.
  * @param firstP starting point of a line segment
  * @param secondP ending point of a line segment
  */
case class LineSegment(firstP: Point, secondP: Point) {
  require(firstP != secondP || firstP.x != secondP.x
    && firstP.y != secondP.y, "cannot create line segment with the same point")

  /** Finds the equation of a line that goes through two points
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
    val m: Double = (p2.y.toDouble - p1.y) / (p2.x - p1.x)
    val b: Double = m match {
      case 0.00 => p1.y.toDouble
      case Double.NegativeInfinity => p1.x.toDouble
      case Double.PositiveInfinity => p1.x.toDouble
      case _ => p1.y.toDouble / (m * p1.x)
    }
    (m, b)
  }

  // defines if a line segment is vertical
  val vertical: Boolean = {
    val line: LineSegment = LineSegment.this
    val eq = line.equation()
    if(eq._1 == Double.NegativeInfinity || eq._1 == Double.PositiveInfinity) true
    else false
  }

  // property of a line segment to indicate it was originally a ray. default is false
  var originalRay: Boolean = false

  /** Determines if two line segments intersect.
    *
    * @param line2 second line segment
    * @return true if they intersect, otherwise false
    */
  def lineLineIntersect(line2: LineSegment): Boolean = {
    val line1: LineSegment = LineSegment.this
    val eq1 = line1.equation()
    val eq2 = line2.equation()

    // determine if lines are parallel with same slope or both vertical
    val parallel: Boolean = {
      if(eq1._1 == eq2._1 || (line1.vertical && line2.vertical)) true
      else false
    }

    // case where lines are parallel
    if(parallel) false
    // case for non-parallel lines
    else{
      // finds the point of intersection depending if either line is vertical
      val intersectPx: Int = (line1.vertical, line2.vertical) match {
        case (true, false) => eq1._2.toInt
        case (false, true) => eq2._2.toInt
        case (false, false) => (eq2._2 - eq1._2 / eq1._1 - eq2._1).toInt
        case (true, true) => sys.exit()
      }
      val intersectPy: Int = (line1.vertical, line2.vertical) match {
        case(true, false) => (intersectPx * eq2._1 + eq2._2).toInt
        case _ => (intersectPx * eq1._1 + eq1._2).toInt
      }
      val intP: Point = Point(intersectPx, intersectPy)

      // TODO fix DRY code
      // if line2 was originally a ray, checks if point of intersection is on
      // line segment 1 only
      if (line2.originalRay) {
        val a = List(line1.firstP.x, line1.secondP.x)
        val b = List(line1.firstP.y, line1.secondP.y)
        a.min <= intP.x && intP.x <= a.max && b.min <= intP.y && intP.y <= b.max
      }
      // else - checks if point of intersection is on either line segment
      else {
        val a = List(line1.firstP.x, line1.secondP.x)
        val b = List(line1.firstP.y, line1.secondP.y)
        val c = List(line2.firstP.x, line2.secondP.x)
        val d = List(line2.firstP.y, line2.secondP.y)
        (a.min <= intP.x && intP.x <= a.max && b.min <= intP.y && intP.y <= b.max) ||
          (c.min <= intP.x && intP.x <= c.max && d.min <= intP.y && intP.y <= d.max)
      }
    }
  }

  /** Determines if a line segment and ray intersect
    *
    * @param ray second line segment to check for intersection as a ray
    * @return true if they intersect, otherwise false
    */
  def lineRayIntersect(ray: Ray): Boolean = {
    val line: LineSegment = LineSegment.this
    val rayAsSegment = ray.apply(ray.startingP)
    line.lineLineIntersect(rayAsSegment)
  }
}

/** A ray representing a horizontal line segment that starts at a
  * particular point and ends at a point 500 units away.
  * When apply method is called, sets resulting line segment's
  * originalRay property to true.
  *
  * @param startingP the initial starting point of a ray
  */
case class Ray(startingP: Point) {
  def apply(sP: Point): LineSegment = {
    val ls = LineSegment(sP, Point(sP.x + 500, sP.y))
    ls.originalRay = true
    ls
  }
}

/** A simple closed polygon composed of three or more points.
  * Sequence of non-null inputPoints is in val points.
  *
  * @param inputPoints arbitrary number of input points
  */
case class Polygon(inputPoints: Point*) extends Shape {
  val points = inputPoints.filter(_ != null)
  require(points.size > 2, "not enough non-null points!")

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
