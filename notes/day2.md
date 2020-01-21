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
- A `Node` containing an `Int` OR
- A `Branch` containing a `left` `IntTree` AND `right` `IntTree`
