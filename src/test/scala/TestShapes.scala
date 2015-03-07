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

  test("A point inside a rectangle should correctly list inside rectangle") {
    assert(pointInsideShape(simpleRectangle, Point(53, 70)))
    assert(pointInsideShape(simpleRectangle, Point(5, 120)))
    assert(!pointInsideShape(simpleRectangle, Point(50, 150)))
  }

  test("A point inside a polygon should correctly list inside polygon") {
    info("but does not account for points whose ray goes through a vertex of the polygon")
    assert(pointInsideShape(simplePolygon, Point(5,6)))
    assert(pointInsideShape(simplePolygon, Point(5,5)))
    assert(pointInsideShape(simplePolygon, Point(6,5)))
    assert(!pointInsideShape(simplePolygon, Point(3,5)))
    assert(!pointInsideShape(simplePolygon, Point(6,2)))
  }

  test("A location cannot have a null child shape") {
    intercept[IllegalArgumentException] {
      val badLocation = Location(3, 2, null)
    }
  }

  test("A point inside a polygon at a different location is still inside the polygon") {
    assert(pointInsideShape(Location(5,5,simplePolygon), Point(10,11)))
    assert(pointInsideShape(Location(5,5,simplePolygon), Point(10,10)))
    assert(pointInsideShape(Location(5,5,simplePolygon), Point(11,10)))
    assert(!pointInsideShape(Location(5,5,simplePolygon), Point(8,10)))
    assert(!pointInsideShape(Location(5,5,simplePolygon), Point(11,7)))
  }

  test("A group cannot contain a null child shape") {
    intercept[IllegalArgumentException] {
      val badGroup = Group(simplePolygon, null)
    }
  }

  test("A point inside a group of polygons should correctly list inside the group") {
    assert(pointInsideShape(group, Point(5,6)))
    assert(pointInsideShape(group, Point(7,5)))
    assert(pointInsideShape(group, Point(12,5)))
    assert(!pointInsideShape(group, Point(7,6)))
    assert(!pointInsideShape(group, Point(6,2)))
  }

}
