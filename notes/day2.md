# Deliveroo: Day 2

## Goals

- map, flatMap, fold in the context of collections (directors and films)
- other collection class methods (find etc.)
- map, flatMap in the context of sequencing operations


### Session 1

- IntList: contains, filter, addNToX, sum, compare IntList to List
- MyList: generics, functions, contains, map, flatMap


### Session 2

- map and flatMap
  - collections
    - Directors and films
  - sequencing
  - List, Option, Future, Either

- for comprehensions

## IntList

An `IntList` is
- `Empty` OR
- `Pair` with a `head` that is an `Int` AND a `tail` that is an `IntList`

```scala
sealed trait IntList
final case class Empty() extends IntList
final case class Pair(head: Int, tail: IntList) extends IntList
```

A method:
- `sum` adds all the elements in the list and returns the result
- `contains` accepts an `Int` parameter and returns `true` if the list contains that element, `false` otherwise
- `product` multiplys the elements of the list
- `append` appends two `IntList`
- `find` returns the first element that matches a predicate (which is a `Int => Boolean` function)

```scala
def find(predicate: Int => Boolean): Option[Int] =
  ???
```

- Scala function literals
1 --- a literal `Int`
1.0 --- a literal `Double`
(x: Int) => x + x --- a function literal

```scala
(a: type1, b: type2, ...) => expr
```

Function type

```scala
(Type1, Type2, ...) => ResultType
```


## IntTree

An `IntTree` is:
- A `Node` containing an `element` `Int` OR
- A `Branch` containing a `left` `IntTree` AND a `right` `IntTree`


## Generic Types

Introduce / declare a generic type
`[TypeName]`

Can occur as part of a class / trait definition, or as part of a method definition
Use as any other type when we are in the scope of the generic type declaration

```scala
final case class Box[A](value: A) {
  def contains(x: A): Boolean =
    value == x
    
  def transform(f: A => A): Box[A] =
    Box(f(value))
    
  def map[B](f: A => B): Box[B] =
    Box(f(value))
}

Box(1).transform(x => x + 42) // Box[Int]
Box("Noel").transform(name => s"Hi $name") // Box[String]
```


Make `IntList` generic, so it can store any type.
I.e. A `MyList` is
- `MyPair` or
- `MyEmpty` where `MyList` is generic.

```scala
sealed trait MyList[A] {
  def length: Int
  def contains(x: A): Boolean
  def find(predicate: A => Boolean): Option[A] 
  def map[B](f: A => B): MyList[B]
  def flatMap[B](f: A => MyList[B]): MyList[B]
}
final case class MyPair[A](head: A, tail: MyList[A]) extends MyList[A]
final case class MyEmpty[A]() extends MyList[A]
```


## Sequencing Operations

- map: transform elements in some collection
- flatMap: transform elements into a collection and flatten the nested collections (mapFlat?)

Sequencing operations (saying one thing happens after another) inside some context.

List: a collection of possible states
- map transforms those states but doesn't add or remove states
- flatMap taking the next step in the search

val next: Board => List[Board]

next(board).flatMap(board2 => next(board2)): List[Board]

Error handling
- Fail fast: stop if there is an error, otherwise continue to the next step

```scala
Option[OrderId]

def route: OrderId => Option[Route]

Option[OrderId].flatMap(route _): Option[Route]

def getOrder: Option[Order]
def route: OrderId => Option[Route]
def price: Route => Option[Price]

Option[Order] Option[Route]    Option[Price]
getOrder.     flatMap(r => route(r)).flatMap(p => price(p))

Option[A] map (A => B) = Option[B]
Option[A] flatMap (A => Option[B]) = Option[B]

Option[Order] Option[Option[Route]] ???
getOrder.     map(r => route(r))    .map(p price(p))
```

### Data Validation

```scala
def parseInt(string: String): Option[Int]
def isPositive(int: Int): Option[Int]
def isNotEmpty(string: String): Option[String]
def isAlphaNumeric(string: String): Option[String]

def isPositiveInt(string: String): Option[Int]
```

- Rewrite `isNonNegativeInt` using a for comprehension
- What is wrong with using `Option` for error handling? What would a better type be? Change your implementation to use this type. Could you do this without changing the implementation of some methods?

- Change from `Option` to `Either`. Choose an error (maybe `String` or `List[String]`?)
- Check that `flatMap` still works with `Either`.

What about combining our predicates?
- What is a predicate? A predicate is `A => Either[Error, A]` where `Error` is a type of your choosing (but I recommend `List[String]`). What does this mean? It checks the value `A` and returns it if the check is successful, otherwise it returns some kind of error message.
- What kind of combinations are possible? Think about logical operators like `and` and `or`.
- Can you define methods to implement these operators?

```scala
final case class Predicate[A](f: A => Either[List[String], A]) {
  def apply(value: A): Either[List[String], A] =
    f(value)
}

val positiveInt = Predicate[Int](x => if(x > 0) Right(x) else Left(List(s"$x must be greater than zero")))
positiveInt(1)
positiveInt(-1)
```


### For comprehensions

For comprehensions are just syntax to make it easier to write lots of `flatMaps` (and a `map`)

```scala
for {
  a <- optA
  b <- optB(a)
  c <- optC
} yield a + b + c

optA.flatMap(a => 
  optB(a).flatMap(b =>
    optC.map(c => a + b + c)
  )
)
```

Every line except the last in a for comprehension becomes a `flatMap`. The last line becomes a `map`.
