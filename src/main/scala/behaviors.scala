package edu.luc.cs.laufer.cs372.shapes

import scala.util.Random
import scala.language.postfixOps

/** Abstract behaviors of determining if a point is found inside a shape
  * and calculating its bounding box and area.
  * Area of a shape is calculated using its boundingBox.
  * A boundingBox object calculates the bounding box, that is, the smallest
  * rectangle containing a shape. The resulting bounding box is returned as a
  * rectangle at a specific location. Group and Location case algorithms from
  * project-3-solution from COMP 313 - Fall 2014.
  *
  * Random hits code from http://langref.org/scala/numbers/random/random-integer
  *
  * */

object pointInsideShape {
  def apply(s: Shape, p: Point): Boolean = s match {
    case r: Rectangle =>
      val rectPoints = Seq(Point(0,0), Point(0,r.height), Point(r.width,r.height), Point(r.width,0))
      pointInsideShape(Polygon(rectPoints: _*), p)
    case l: Location =>
      val transPoint: Point = Point(p.x - l.x, p.y - l.y)
      pointInsideShape(l.child, transPoint)
    case poly: Polygon =>
      val perChecks: List[Boolean] = for(l:LineSegment <- poly.polyLineSeg()) yield l.pointOnLine(p)
      //first check if point is on perimeter of polygon, point is considered inside polygon
      if(perChecks.contains(true)) true
      else {
        val ray = new Ray(p)
        val hits: List[Boolean] = for(l:LineSegment <- poly.polyLineSeg()) yield l.lineLineIntersect(ray)
        if (hits.count(_ == true) %2 == 0){
          false
        }
        else true
      }
    case g: Group =>
      var check: Boolean = false
      for (shape <- g.children) {
        if (pointInsideShape(shape, p)) check = true
      }
      check
  }
}


object boundingBox {
  def apply(s: Shape): Location = s match {
    case r: Rectangle =>
      Location(0, 0, r)
    case l: Location =>
      val bBox = boundingBox(l.child)
      Location(l.x + bBox.x, l.y + bBox.y, bBox.child)
    case poly: Polygon =>
      val allX: List[Int] = poly.points.map { case Point(x, _) => x}.toList
      val allY: List[Int] = poly.points.map { case Point(_, y) => y}.toList
      Location(allX.min, allY.min, Rectangle(allX.max - allX.min, allY.max - allY.min))
    case g: Group =>
      var xL: Int = Int.MaxValue
      var xR: Int = Int.MinValue
      var yD: Int = Int.MaxValue
      var yU: Int = Int.MinValue
      for (shape <- g.children) {
        val bBox = boundingBox(shape)
        xL = List(xL, bBox.x).min
        xR = List(xR, bBox.x + bBox.child.asInstanceOf[Rectangle].width).max
        yD = List(yD, bBox.y).min
        yU = List(yU, bBox.y + bBox.child.asInstanceOf[Rectangle].height).max
      }
      Location(xL, yD, Rectangle(xR - xL, yU - yD))
  }
}

object area {
  def apply(s: Shape): Int = s match {
    case r: Rectangle => r.width * r.height
    case l: Location => area(l.child)
    case polygon: Polygon =>
      val bBox: Location = boundingBox(polygon)
      val bBoxRect: Rectangle = bBox.child.asInstanceOf[Rectangle]
      val bBoxArea: Int = bBoxRect.height * bBoxRect.width

      val randForX = new Random()
      val randForY = new Random()
      val rangeX = bBox.x to (bBox.x + bBoxRect.width)
      val rangeY = bBox.y to (bBox.y + bBoxRect.height)

      val hits = for {i <- 1 to 100000
                      xx = rangeX(randForX.nextInt(rangeX length))
                      yy = rangeY(randForY.nextInt(rangeY length))
      } yield pointInsideShape(polygon, Point(xx, yy))

      val hitPercentage: Double = hits.count(_ == true) / hits.length.toDouble
      (hitPercentage * bBoxArea).toInt
    case g: Group =>
      val bBox: Location = boundingBox(g)
      val bBoxRect: Rectangle = bBox.child.asInstanceOf[Rectangle]
      val bBoxArea: Int = bBoxRect.height * bBoxRect.width

      val randForX = new Random()
      val randForY = new Random()
      val rangeX = bBox.x to (bBox.x + bBoxRect.width)
      val rangeY = bBox.y to (bBox.y + bBoxRect.height)

      val hits = for {i <- 1 to 100000
                      xx = rangeX(randForX.nextInt(rangeX length))
                      yy = rangeY(randForY.nextInt(rangeY length))
      } yield (for{shape <- g.children} yield pointInsideShape(shape, Point(xx, yy))).contains(true)

      val hitPercentage: Double = hits.count(_ == true) / hits.length.toDouble
      (hitPercentage * bBoxArea).toInt
  }
}
