package edu.luc.cs.laufer.cs372.shapes
import TestFixtures._

import org.scalatest.FunSuite

class TestLines extends FunSuite{

  test("A ray from a single point should return a line segment"){
    val myRay = Ray(singlePoint)
    val mySegment = myRay.apply(singlePoint)
    assert(mySegment.firstP === singlePoint)
    assert(mySegment.secondP === Point(singlePoint.x + 500, singlePoint.y))
  }

}
