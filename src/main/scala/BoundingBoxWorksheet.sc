import edu.luc.cs.laufer.cs372.shapes._
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

bbG.x
bbG.y
bbG.child


val basicGroup = Group(
  Location(-200, -100, simplePolygon), Location(400, 300, simplePolygon2))

val bbG2 = boundingBox(basicGroup)

bbG2.x
bbG2.y
bbG2.child

val complexGroup =
  Location(50, 100,
    Group(
      simplePolygon,
      Location(150, 50,
        Group(
          Rectangle(50, 30),
          Rectangle(300, 60),
          Location(100, 200,
            simplePolygon2
          )
        )),
      Rectangle(100, 200)
    ))

val bbG3 = boundingBox(complexGroup)
(bbG3.x, bbG3.y, bbG3.child)

