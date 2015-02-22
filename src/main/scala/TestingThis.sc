case class Point(x: Int, y: Int)

case class LineSegment(firstP: Point, secondP: Point) {
  require(firstP != secondP || firstP.x != secondP.x
    && firstP.y != secondP.y, "cannot create line segment with the same point")

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
      }
      val intersectPy: Int = (line1.vertical, line2.vertical) match {
        case(true, false) => (intersectPx * eq2._1 + eq2._2).toInt
        case _ => (intersectPx * eq1._1 + eq1._2).toInt
      }
      val intP: Point = Point(intersectPx, intersectPy)

      //checks if point of intersection is on either line segment
      // TODO fix DRY code
      val a = List(line1.firstP.x, line1.secondP.x)
      val b = List(line1.firstP.y, line1.secondP.y)
      val c = List(line2.firstP.x, line2.secondP.x)
      val d = List(line2.firstP.y, line2.secondP.y)
      (a.min <= intP.x && intP.x <= a.max && b.min <= intP.y && intP.y <= b.max) ||
        (c.min <= intP.x && intP.x <= c.max && d.min <= intP.y && intP.y <= d.max)
    }
  }
}
val line1 = LineSegment(Point(-5, 5), Point(5, 5)) //blueTop
val line2 = LineSegment(Point(-5, 5), Point(-5, -5)) //blueLeft
val eq1 = line1.equation()
val eq2 = line2.equation()
val intersectPx: Int = (line1.vertical, line2.vertical) match {
  case (true, false) => eq1._2.toInt
  case (false, true) => eq2._2.toInt
  case (false, false) => (eq2._2 - eq1._2 / eq1._1 - eq2._1).toInt
}
val intersectPy: Int = (line1.vertical, line2.vertical) match {
  case(true, false) => (intersectPx * eq2._1 + eq2._2).toInt
  case _ => (intersectPx * eq1._1 + eq1._2).toInt
}