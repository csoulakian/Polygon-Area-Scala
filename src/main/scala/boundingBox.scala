package edu.luc.cs.laufer.cs372.shapes

/** An object that calculates the bounding box, that is, the smallest
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
      val allX: List[Int] = poly.points.map {case Point(x,_) => x}.toList
      val allY: List[Int] = poly.points.map {case Point(_,y) => y}.toList
      Location(allX.min, allY.min, Rectangle(allX.max - allX.min, allY.max - allY.min))
    case g: Group =>
      var xL: Int = Int.MaxValue
      var xR: Int = Int.MinValue
      var yD: Int = Int.MaxValue
      var yU: Int = Int.MinValue
      for(shape <- g.children) {
        val bBox = boundingBox(shape)
        xL = List(xL, bBox.x).min
        xR = List(xR, bBox.x + bBox.child.asInstanceOf[Rectangle].width).max
        yD = List(yD, bBox.y).min
        yU = List(yU, bBox.y + bBox.child.asInstanceOf[Rectangle].height).max
      }
      Location(xL, yD, Rectangle(xR - xL, yU - yD))
  }

}
