package edu.luc.cs.laufer.cs372.shapes


object boundingBox {

  def apply(s: Shape): Location = s match {
    case Polygon(inputPoints) => bbOfPolygon(Polygon(inputPoints))
    case Group(children @ _*) => boundingBox(children)
  }

  def bbOfPolygon(polygon: Polygon): Location = {
    //val points = polygon.points

    // separates x and y values into their own lists
    val allX: List[Int] = polygon.points.map {case Point(x,_) => x}.toList
    val allY: List[Int] = polygon.points.map {case Point(_,y) => y}.toList

    Location(allX.min, allY.min, Rectangle(allX.max - allX.min, allY.max - allY.min))
  }

  def bbOfGroup(group: Group): Location = {

    val allShapes: (List[Int], List[Int], List[Rectangle]) = for{shape <- Group} yield (
      boundingBox(shape).x,
      boundingBox(shape).y,
      boundingBox(shape).child
      )

    val absX = allShapes._1.min
    val absY = allShapes._2.min

    val rectX = (for{rectangle <- allShapes._3} yield rectangle.width).max
    val rectY = (for{rectangle <- allShapes._3} yield rectangle.height).max

    Location(absX, absY, Rectangle(rectX,rectY))

  }


}
