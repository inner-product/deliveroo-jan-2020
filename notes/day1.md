# Deliveroo: Day 1

## Setup

Course repository: https://github.com/inner-product/deliveroo-jan-2020
    
## Introductions

- Name
- Scala experience and general programming experience
- Challenges at work; work related things that are interesting
- Goals for the course

Noel Welsh
- 10 years of Scala, lots of functional programming, Java, etc.
- Teaching Scala (and FP). FP, Machine learning, creative coding
- Help you get better with Scala

Dave Gurnell
- 10 years of Scala, lots of functional programming, Java, etc.
- REST, front end, changing code; accomodating different needs in the same code base
- Help you get better with Scala

Joe
- 5 years professional experience; Scala for 2.5 yrs. Backend stuff. 
- Don't get to write enough Scala. Would like to learn more about modelling business problems with algebraic data types

Daria
- A few months Scala. Web developer. Front-end. JS, little bit of TypeScript.
- Get better at functional programming.

David
- 3 months Scala. First foray into FP. Java and Go.
- Learning a new style of coding
- Get better and understand FP.

Jack
- Frontend web developer. JS. Little bit of FP.
- First Scala beyond beginners course. (Lightbend Professional Scala)
- Remaining on the front-end primarily

Omnia
- 10 years coding. 7 years of Scala.
- Kafka, tooling, low level work
- Refresh knowledge / teaching others.

Padric
- 2 years Scala. 10 years programming. Python and Erlang
- Colleagues.

Egis
- 6-7 years Scala
- Refresh knowledge. Learn new techniques.

Marty
- 25 years dev. Scala for ~2 years. Java 10+ years
- Get more indepth knowledge of idiomatic Scala

Daniel
- 10 years Ruby. ~1 year Scala
- Learn about types

Alexei
- 3 months programming experience
- Get a good foundation
- Fortran, Physics PhD. 
- Product management
- Bootcamp

Tom
- Scala 1.5 years. Back-end dev on product team maintaining web apps
- Would benefit from exploring deeper FP patterns

Brian
- 2 wks Scala. 15 years Java.
- Weird Akka stuff
- Read Akka code.

Carlos
- 2 years Scala; no formal training
- Like to learn fundamentals; FP

Ruma
- A few months Scala / Deliveroo. BSc / MSc CS
- Know how to use Scala specific language features

Distia
- 2 years Scala. Back end / data
- Prior to that data scientist. Python.
- Lots of self study, FP. But how to apply it?

Phil
- New to Scala
- Lightbend Professional; read first Underscore book.
- 18 years C#, Node, Go, Ruby

Mike
- Scala 3 years
- Self taught. Modelling business problems.

Clara
- Web engineer. JS.
- Lightbend Scala Course.
- Get basic understanding of Scala.

Geoff
- Backend SE. 18 months Scala.
- Experience JVM and others
- Modelling business problems with ADTs

Melanie
- Little Scala


## Course Overview

- Problem solving strategies
- Pairs / groups

- Where is the toilet?
  - Head left
  - Look for signs
  - Open each door
  - Breadth first search
  - Ask someone
- What is the weather like right here, right now?
  - Look out the window
  - Add / remove clothes
  - Ask Google
  - Go outside
- What do the interior angles of triangle sum to?
  - Proof
  - Cut up triangles
  - Draw a large number of triangles and sum the angles
  - Ask a community of experts

Proof: logic, deduction
Observing the natural world: science, empiricism, experimental, inductive
Communication: appeal to authority

Appeal to authority:
- RTFM
- StackOverflow
- Using dependency
- Luke, use the source
- phone a friend
- do what Marty tells you

Science:
- unit tests
- debugging
- run it!
- logging / tracing

Logic:
- formal methods
- types
- compilation
- mental machine model (notional machine)


## Algebraic Data Types

- An algebraic data type consists of product types and sum types.

- Product type: logical and

```scala
// A is a B AND C
final case class A(b: B, c: C)
```

- Sum type: logical or

```scala
// A is a B OR C
sealed trait A
final case class B() extends A
final case class C() extends A
```

Closed world. Cannot extend. Gives safety guarantees.

```scala
sealed trait Customer
final case class Rider(name: String, vehicle: Vehicle) extends Customer
final case class Consumer(name: String, address: String) extends Customer
final case class Restaurant(name: String, licensed: Boolean) extends Customer
sealed trait Vehicle
case object Bike extends Vehicle
case object Moped extends Vehicle
case object Scooter extends Vehicle
case object Car extends Vehicle
```


## Structural Recursion

Whenever we want to do ANYTHING to an algebraic data type we can do it using structural recursion.

Two patterns:
- for and
- for or

Two implementation strategies:
- pattern matching (FP)
- polymorphism (OO)

```scala
// A is a B AND C
final case class A(b: B, c: C)
```

```scala
// A is a B OR C
sealed trait A
final case class B() extends A
final case class C() extends A
```

### Pattern Matching

```scala
expr match {
    case pattern1 => rhsExpr1
    case pattern2 => rhsExpr2
    ...
}
```

expr is an expression. Scala code that evaluates to some value that we match against.
We then match each pattern in turn (top to bottom) against the value of expr.
We execute / evaluate / run the right side expression of the first pattern to match
The result of the entire expression is the result of the right hand side expression that we run.

```scala
sealed trait Vehicle
final case class Bike(gears: Int) extends Vehicle
final case class Car(seats: Int) extends Vehicle
```

```scala
def isHipster(vehicle: Vehicle): Boolean =
  vehicle match {
    case Bike(g) => g == 0
    case Car(s) => s <= 2
  }
```

Kinds of patterns:

- `_` matches anything and does not give it a name
- alphanumeric identifier (e.g. `g`) matches anything and gives it a name on the RHS
- case class constructor matches an instance of that case class (e.g. `Car(_)` matches a `Car` and does not give a name to the number of seats)

Compiler checks we handle all the cases (exhaustivity checking) unless we do fancy patterns.

Write a method that accepts a `Vehicle` and returns true if the `Vehicle` is motorised.

```scala
// A is a B AND C
final case class A(b: B, c: C)

case A(b, c) => ???
```

```scala
// A is a B OR C
sealed trait A
final case class B() extends A
final case class C() extends A

anA match {
  case B() => ???
  case C() => ???
}
```

When the data is recursive the method is also recursive.


### Polymorphism

Abstract method
- declares a method type but has no implementation

Implement a method `doSomething`

```scala
// A is a B AND C
final case class A(b: B, c: C) {
  def doSomething =
    ???  // concrete implementation here
}
```

```scala
// A is a B OR C
sealed trait A {
  // abstract method
  def doSomething
}
final case class B() extends A {
  def doSomething = ???
}
final case class C() extends A {
  def doSomething = ???
}
```
    
