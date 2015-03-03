package edu.luc.cs.laufer.cs372.shapes

import scala.util.Random

/** Abstract behaviors of calculating the area and bounding box of a shape.
 *  Area of a shape is calculated using its boundingBox.
  * A boundingBox object calculates the bounding box, that is, the smallest
  * rectangle containing a shape. The resulting bounding box is returned as a
  * rectangle at a specific location. Group and Location case algorithms from
  * project-3-solution from COMP 313 - Fall 2014.
  *
  * */

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
        } yield polygon.pointInsidePoly(Point(xx, yy))

        val hitPercentage: Double = hits.count(_ == true) / hits.length.toDouble
        (hitPercentage * bBoxArea).toInt
      case g: Group => (for {shape <- g.children} yield area(shape)).sum
    }
  }
