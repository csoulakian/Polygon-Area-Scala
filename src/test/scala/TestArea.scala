package edu.luc.cs.laufer.cs372.shapes
import TestFixtures._
import org.scalatest.FunSuite


class TestArea extends FunSuite {

  test("A polygon in the shape of a rectangle should have an area equal to its bounding box") {
    assert(area(polyRect) === area(boundingBox(polyRect).child))
  }

  test("A polygon in the shape of a triangle should have an area of about 250000") {
    info("calculated area is " + area(polyTri))
  }

  test("The area of this group of shapes should be about 500000") {
    info("calculated area is " + area(group1))
  }

  test("The area of overlapping shapes should be about 312500") {
    info("calculated area is " + area(group2))
  }

}
