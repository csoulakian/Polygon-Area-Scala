package edu.luc.cs.laufer.cs372.shapes
import TestFixtures._
import org.scalatest.FunSuite


class TestShapes extends FunSuite {

  test("A polygon should have at least 3 non-null points") {
    assert(simplePolygon.points === Seq(Point(2,3), Point(5,6), Point(7,4)))

    val bad = Seq(Point(2,3), Point(5,6), null)
    val thrown = intercept[IllegalArgumentException] {
      val badPolygon = Polygon(bad: _*)
    }
    assert(thrown.getMessage === "requirement failed: not enough non-null points!")
  }

  test("polyLineSeg should return the correct list of Line Segments"){
    assert(simplePolygon.polyLineSeg() === List(LineSegment(Point(2,3),Point(5,6)),
      LineSegment(Point(5,6),Point(7,4)), LineSegment(Point(7,4),Point(2,3))))
  }

  test("A point inside the polygon should correctly list inside polygon") {
    info("but does not account for points whose ray goes through a vertex of the polygon")
    assert(pointInsideShape(simplePolygon, Point(5,6)))
    assert(pointInsideShape(simplePolygon, Point(5,5)))
    assert(pointInsideShape(simplePolygon, Point(6,5)))
    assert(!pointInsideShape(simplePolygon, Point(3,5)))
    assert(!pointInsideShape(simplePolygon, Point(6,2)))
  }


}
