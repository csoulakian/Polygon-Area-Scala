package edu.luc.cs.laufer.cs372.shapes

import org.scalatest.FunSuite

import TestFixtures._

class TestBoundingBox extends FunSuite {

  def testBoundingBox(description: String, s: Shape, x: Int, y: Int, width: Int, height: Int) = {
    test(description) {
      val b = boundingBox(s)
      val r = b.child.asInstanceOf[Rectangle]
      assert(x === b.x)
      assert(y === b.y)
      assert(width === r.width)
      assert(height === r.height)
    }
  }

  testBoundingBox("simple polygon", simplePolygon, 2, 3, 5, 3)
  testBoundingBox("simple rectangle", simpleRectangle, 0, 0, 80, 120)
  testBoundingBox("simple location", simpleLocation, 70, 30, 80, 120)
  testBoundingBox("basic group", basicGroup, 2, 3, 68, 67)
  testBoundingBox("simple group", simpleGroup, -198, -97, 611, 403)
  testBoundingBox("complex group", complexGroup, 50, 100, 450, 256)
}
