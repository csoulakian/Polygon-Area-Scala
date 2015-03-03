import edu.luc.cs.laufer.cs372.shapes._
import scala.util.Random
sealed trait Shape

val good = Seq(Point(2,3), Point(5,6), Point(7,4))
val simplePolygon: Polygon = Polygon(good: _*)
val good2 = Seq(Point(7,5), Point(10,2), Point(13,6))
val simplePolygon2: Polygon = Polygon(good2: _*)

val box1 = boundingBox(simplePolygon)
(box1.x, box1.y, box1.child)
val box2 = boundingBox(simplePolygon2)
(box2.x, box2.y, box2.child)

val group = Group(simplePolygon, simplePolygon2)

val bbG = boundingBox(group)
(bbG.x, bbG.y, bbG.child)

val bBox: Location = boundingBox(simplePolygon)
val bBoxRect: Rectangle = bBox.child.asInstanceOf[Rectangle]
val bBoxArea: Int = bBoxRect.height * bBoxRect.width

val randForX = new Random()
val randForY = new Random()
val rangeX = bBox.x to (bBox.x + bBoxRect.width)
val rangeY = bBox.y to (bBox.y + bBoxRect.height)

val hitPercentage = for{i <- 1 to 10000
xx = rangeX(randForX.nextInt(rangeX length))
yy = rangeY(randForY.nextInt(rangeY length))
} yield simplePolygon.pointInsidePoly(Point(xx, yy))


hitPercentage.count(_ == true) / hitPercentage.length.toDouble

val good3 = Seq(Point(-500,0), Point(500,0), Point(0,500))
val simplePolygon3: Polygon = Polygon(good3: _*)

simplePolygon3.computeArea()

