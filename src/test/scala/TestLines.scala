package edu.luc.cs.laufer.cs372.shapes
import TestFixtures._
import org.scalatest.FunSuite

/** Tests the behaviors of line segments and rays.
 * @see LinesFixture.jpg for graphical representation of line segments
  */
class TestLines extends FunSuite{

  test("A line segment cannot be created with a single point") {
    val thrown = intercept[IllegalArgumentException] {
      val badLineSegment = LineSegment(Point(2,3), Point(2,3))
    }
    assert(thrown.getMessage ===
      "requirement failed: cannot create line segment with the same point")
  }

  test("Equation function should return the correct slope intercept values") {
    val eqHor = blueTop.equation()
    assert(eqHor._1 === 0.0)
    assert(eqHor._2 === 5.0)

    val eqVer = blueLeft.equation()
    assert(eqVer._1 === Double.NegativeInfinity || eqVer._1 === Double.PositiveInfinity)
    assert(eqVer._2 === -5.0)

    val eqNeg = greenRight.equation()
    assert(eqNeg._1 === -1.0)
    assert(eqNeg._2 === 5.0)

    val eqPos = redRight.equation()
    assert(eqPos._1 === 1.0)
    assert(eqPos._2 === -5.0)
  }

  test("A line segment with no slope is vertical") {
    assert(blueLeft.vertical)
    assert(!blueTop.vertical)
  }

  test("Check if a point on a line is on a particular line segment") {
    assert(!blueTop.pointOnLine(Point(0,6)))

    assert(blueTop.pointOnLine(Point(-6,5)))
    assert(!blueTop.pointOnLineSeg(Point(-6,5)))

    assert(blueTop.pointOnLine(Point(-5,5)))
    assert(blueTop.pointOnLineSeg(Point(-5,5)))
  }

  test("A ray from a single point should return the correct line segment"){
    val myRay = new Ray(singlePoint)
    val mySegment: LineSegment = myRay.asInstanceOf[LineSegment]
    assert(mySegment.firstP === singlePoint)
    assert(mySegment.secondP === Point(singlePoint.x + 1000, singlePoint.y))
  }

  test("Parallel lines should not intersect") {
    assert(!blueTop.lineLineIntersect(blueBottom))
    assert(!blueLeft.lineLineIntersect(blueRight))
    assert(!redLeft.lineLineIntersect(redRight))
    assert(!greenLeft.lineLineIntersect(greenRight))

    assert(!blueBottom.lineLineIntersect(blueTop))
    assert(!blueRight.lineLineIntersect(blueLeft))
    assert(!redRight.lineLineIntersect(redLeft))
    assert(!greenRight.lineLineIntersect(greenLeft))
  }

  test("Blue lines that touch should intersect") {
    assert(blueTop.lineLineIntersect(blueLeft))
    assert(blueTop.lineLineIntersect(blueRight))

    assert(blueBottom.lineLineIntersect(blueLeft))
    assert(blueBottom.lineLineIntersect(blueRight))

    assert(blueLeft.lineLineIntersect(blueTop))
    assert(blueLeft.lineLineIntersect(blueBottom))

    assert(blueRight.lineLineIntersect(blueTop))
    assert(blueRight.lineLineIntersect(blueBottom))
  }

  test("Red and green intersecting lines should intersect") {
    assert(redLeft.lineLineIntersect(greenLeft))
    assert(redLeft.lineLineIntersect(greenRight))

    assert(redRight.lineLineIntersect(greenLeft))
    assert(redRight.lineLineIntersect(greenRight))

    assert(greenLeft.lineLineIntersect(redLeft))
    assert(greenLeft.lineLineIntersect(redRight))

    assert(greenRight.lineLineIntersect(redLeft))
    assert(greenRight.lineLineIntersect(redRight))
  }

  test("Check for intersection of a ray and vertical line segment") {
    // ray starts to the left of segment
    assert(blueRight.lineLineIntersect(new Ray(Point(2, 0))))

    // ray starts above segment
    assert(!blueRight.lineLineIntersect(new Ray(Point(5, 8))))

    // ray starts to the right of segment
    assert(!blueRight.lineLineIntersect(new Ray(Point(8, 0))))

    // ray starts below segment
    assert(!blueRight.lineLineIntersect(new Ray(Point(5, -8))))
  }

  test("Check for intersection of a ray and horizontal line segment") {
    // ray starts to the left of segment
    assert(blueTop.lineLineIntersect(new Ray(Point(-8, 5))))

    // ray starts above segment
    assert(!blueTop.lineLineIntersect(new Ray(Point(8, 0))))

    // ray starts to the right of segment
    assert(!blueTop.lineLineIntersect(new Ray(Point(8, 5))))

    // ray starts below segment
    assert(!blueTop.lineLineIntersect(new Ray(Point(0, 2))))
  }

}
