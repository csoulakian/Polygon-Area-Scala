[![Build Status](https://travis-ci.org/csoulakian/Polygon-Area-Scala.svg)](https://travis-ci.org/csoulakian/Polygon-Area-Scala)
[![Coverage Status](https://coveralls.io/repos/csoulakian/Polygon-Area-Scala/badge.svg?branch=master&service=github)](https://coveralls.io/github/csoulakian/Polygon-Area-Scala?branch=master)

# Overview
This [project](https://trello.com/c/OGBjqxYg/23-project-2) computes the area of a polygon shape,
composed of three or more points, or a group of shapes using the
[Monte Carlo method](http://en.wikipedia.org/wiki/Monte_Carlo_method).

- algebraic data types and their representation as case classes
- pattern matching
- recursive functions
- relationship with composite, decorator, and visitor patterns
- test-driven development (TDD) in Scala and Intellij IDEA
- some simple computational geometry problems
- Monte Carlo method

The features of this program can be viewed through the implementation of the test suites.

Code references:

- [shapes-oo-scala](https://github.com/LoyolaChicagoCode/shapes-oo-scala)
- [shapes-android-scala](https://github.com/LoyolaChicagoCode/shapes-android-scala)

# Testing
To run the tests:

    sbt test

To determine test coverage:

    sbt coverage test

Then open this file in a web browser:

    target/scala-2.10/scoverage-report/index.html

# Additional Comments

As I was coding what I considered one of the most difficult parts, checking if line segments intersect,
I found that it was better to pull as many smaller functions and methods as I could out of lineLineIntersect
into the lineSegment case. It allowed me to debug and test the code more easily
by first running the outer methods and functions that were then used to determine if two line segments intersect.

The behaviors of checking if a point can be found inside a shape, determining the location
and rectangle components of a shape's bounding box, and computing the area of a shape are all
abstractly implemented as objects and use pattern matching. This abstraction allows for
the reuse of the rectangle and polygon cases when applying these behaviors to a group of shapes
or shapes transposed to a different location.

At first, I had the pointInsideShape function inside the polygon case class,
but when I wanted to use the function on a group of shapes, I realized it there would be a lot of DRY code.
I also made the bounding box and area computations abstract because it's easier to modify the behavior
as needed for the location and group cases. It also makes it easier to account for overlapping shapes.