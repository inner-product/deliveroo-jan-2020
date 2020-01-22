# Deliveroo: Day 3

## Type Classes

A type class is an interface. In Scala implemented as a `trait` with at least one type parameter / generic type.

- we can create implementations of the interface without changing the source code of the type for which we create the implementation.


### Single Abstract Method

new Ordering[Order] {
  def order()
}

SAM means an object that is an instance of a class with a single method is interchangeable with a Java 8 function.
- alternatively, Java 8 added the ability to use a function wherever an instance of a class with a single method was expected


### Implicit Values

An implicit value is a *type class instance* (a type class implementation for a specific type)

An implicit value may be:
- an `implicit val`
- an `implicit object`
- an `implicit def` with *only* implicit parameters

An implicit value cannot be a top-level declaration. It must be wrapped in an object, class, or trait (and usually an object).


## Type Classes Recap

What is a type class?
- an interface
How do we define a type class in Scala?
- with a `trait`

What is a type class instance?
- an implementation of the type class for a specific type
How do we declare a type class instance in Scala?
- using an `implicit value`

What are the three forms of implicit value in Scala?
- `implicit val` `implicit object` `implicit def`

What does implicit def do that the other two cannot?
- type class instance composition. Building more complicated type class instances from simpler instances.

How do we require a type class instance in Scala?
- we add an implicit parameter to a method

(Implicit parameters must be the last parameter list in a list of parameter lists. All of the parameters in the list are implicit.)

Where does the compiler look for implicit values to provide as implicit parameters?
- the companion object of related types
- the lexical scope (up the file within enclosing curly braces)

What does the type class pattern allow us to do that we could not do before?
- add new functionality to existing classes without modifying the source code
- arbitrary computation at compile time (bitcoin mining!)
- type class composition, and getting the compiler to do the composition for you
- an intermediate point between ADTs and generic types
  - an ADT is a specific type
  - a generic type is any type
  - a type class is any type that meets the constraints (i.e. implements the type class interface. i.e. has a type class interface available)


### Json Writer

Given a type `A` convert it into `Json`.

1. Create a type class `JsonWriter`
2. Create some type class instances (e.g. for `Double`, `String`)
3. Create some instances using composition (e.g. for `List`, `Option`)
4. Show they work!


### Monoid

1. What is a monoid? Find out!
2. Represent it as a type class
3. Create some type class instances (e.g. for `Int`)
4. Create some instances using composition (e.g. for `List`, `Option`)
5. Can you compose finite state machines using monoids?
