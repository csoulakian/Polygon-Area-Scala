package edu.luc.cs.laufer.cs372.shapes

object TestFixtures {

  /** fixtures for TestLines
    * @see LinesFixture.jpg
    */
  val singlePoint = Point(2, 3)

  val blueTop = LineSegment(Point(-5, 5), Point(5, 5))
  val blueBottom = LineSegment(Point(5, -5), Point(-5, -5))
  val blueLeft = LineSegment(Point(-5, 5), Point(-5, -5))
  val blueRight = LineSegment(Point(5, -5), Point(5, 5))

  val redLeft = LineSegment(Point(-8, -3), Point(3, 8))
  val redRight = LineSegment(Point(8, 3), Point(-3, -8))

  val greenLeft = LineSegment(Point(-8, 3), Point(3, -8))
  val greenRight = LineSegment(Point(8, -3), Point(-3, 8))


  /** fixtures for TestPolygon
    *
    */
  val good = Seq(Point(2,3), Point(5,6), Point(7,4), null)
  val simplePolygon = Polygon(good: _*)


  /** fixtures for TestBoundingBox
    *
    */
  val good2 = Seq(Point(7,5), Point(10,2), Point(13,6))
  val simplePolygon2 = Polygon(good2: _*)

  val simpleRectangle = Rectangle(80, 120)

  val simpleLocation = Location(70, 30, Rectangle(80, 120))

  val basicGroup = Group(simplePolygon, Location(50, 30, Rectangle(20, 40)))

  val simpleGroup =
    Group(Location(-200, -100, simplePolygon), Location(400, 300, simplePolygon2))

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

  /** fixtures for TestArea
    *
    */
  val good3 = Seq(Point(-500,0), Point(500,0), Point(0,500))
  val polyTri: Polygon = Polygon(good3: _*)

  val good4 = Seq(Point(-500, 500),Point(-500, 750),Point(500, 750),Point(500, 500))
  val polyRect: Polygon = Polygon(good4: _*)

  val good5 = Seq(Point(-500, 0),Point(-500, 250),Point(500, 250),Point(500, 0))
  val smallerPolyRect: Polygon = Polygon(good5: _*)

  val group1: Group = Group(polyTri, polyRect)
  val group2: Group = Group(polyTri, smallerPolyRect)
}
