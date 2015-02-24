import edu.luc.cs.laufer.cs372.shapes._
sealed trait Shape
val good = Seq(Point(2,3), Point(5,6), Point(7,4), null)
val simplePolygon = Polygon(good: _*)
val lineS = simplePolygon.polyLineSeg()

val p = Point(5,5)


val ray = Ray(p)
val hits: List[Boolean] = for(l:LineSegment <- lineS) yield l.lineRayIntersect(ray)
lineS(0).lineRayIntersect(ray)

if (hits.count(_ == true) %2 == 0){
  false
}
else true

val line1 = lineS(0)
val line2 = ray.apply(p)
line2.originalRay = true
val eq1 = line1.equation()
val eq2 = line2.equation()

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