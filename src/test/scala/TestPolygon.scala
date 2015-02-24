package edu.luc.cs.laufer.cs372.shapes
import TestFixtures._
import org.scalatest.FunSuite


class TestPolygon extends FunSuite {

  test("A polygon should have at least 3 non-null points") {

    assert(simplePolygon.points === Seq(Point(2,3), Point(5,6), Point(9,12)))

    val bad = Seq(Point(2,3), Point(5,6), null)
    val thrown = intercept[IllegalArgumentException] {
      val badPolygon = Polygon(bad: _*)
    }
    assert(thrown.getMessage === "requirement failed: not enough non-null points!")

  }

  test("polyLineSeg should return the correct list of Line Segments"){

    assert(simplePolygon.polyLineSeg() === List(LineSegment(Point(2,3),Point(5,6)),
      LineSegment(Point(5,6),Point(9,12)), LineSegment(Point(9,12),Point(2,3))))

  }

}
