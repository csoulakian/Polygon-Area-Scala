package edu.luc.cs.laufer.cs372.shapes
import TestFixtures._
import org.scalatest.FunSuite


class TestArea extends FunSuite {

  test("A rectangle should have an area equal to its bounding box") {
    assert(area(simpleRectangle) === area(boundingBox(simpleRectangle).child))
    assert(area(Location(60, 70, simpleRectangle)) ===
      area(boundingBox(Location(60, 70, simpleRectangle)).child))
  }

  test("A polygon in the shape of a rectangle should have an area equal to its bounding box") {
    assert(area(polyRect) === area(boundingBox(polyRect).child))
    assert(area(Location(60, 70, polyRect)) ===
      area(boundingBox(Location(60, 70, polyRect)).child))
  }

  test("A polygon in the shape of a triangle should have an area of about 250000") {
    info("calculated area is " + area(polyTri))
  }

  test("The area of this group of shapes should be about 500000") {
    info("calculated area is " + area(group1))
  }

  test("The area of this group of shapes at a different location should still be about 500000") {
    info("calculated area is " + area(Location(60, 70, group1)))
  }

  test("The area of overlapping shapes should be about 312500") {
    info("calculated area is " + area(group2))
  }

  test("The area of overlapping shapes at a different location should still be about 312500") {
    info("calculated area is " + area(Location(60, 70, group2)))
  }

}
