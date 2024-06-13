# Burger Builder Project

This project demonstrates the implementation of the Builder Design Pattern in Scala, utilizing Generalized Type Constraints (GTC) for validation. The goal is to create a flexible and robust system for building burgers with various ingredients while ensuring certain constraints are met.

## Table of Contents

- [Overview](#overview)
- [Ingredients](#ingredients)
- [Burger](#burger)
- [BurgerBuilder](#burgerbuilder)
- [Cook](#cook)
- [Tests](#tests)
- [Usage](#usage)
- [Improvements](#improvements)

## Overview

The main purpose of this project is to implement the Builder Design Pattern and utilize Generalized Type Constraints (GTC) to ensure that a burger always starts and ends with a bun. This project consists of the following main components:

1. **Ingredients**: Different types of ingredients that can be used in a burger.
2. **Burger**: A case class representing the burger.
3. **BurgerBuilder**: A builder class for constructing burgers.
4. **Cook**: A class containing predefined recipes for various types of burgers.
5. **Tests**: A set of test cases to demonstrate the usage of the builder and cook classes.

## Ingredients

The `Ingredient` trait and its subclasses represent different types of ingredients that can be added to a burger.

```scala
sealed trait Ingredient {
  val calories: Int
}

sealed trait Bun extends Ingredient
case object RegularBun extends Bun {
  override val calories: Int = 88
}
case object CrispyBun extends Bun {
  override val calories: Int = 102
}

sealed trait Patty extends Ingredient
case object RegularPatty extends Patty {
  override val calories: Int = 134
}
case object FishPatty extends Patty {
  override val calories: Int = 112
}
case object VeganPatty extends Patty {
  override val calories: Int = 93
}

case object Cheese extends Ingredient {
  override val calories: Int = 68
}
case object Lettuce extends Ingredient {
  override val calories: Int = 5
}
case object Tomato extends Ingredient {
  override val calories: Int = 3
}
```

## Burger

The `Burger` case class represents a burger, which is composed of a list of ingredients. It also provides methods to get the description and calories of the burger.

```scala
case class Burger(ingredients: List[Ingredient]) {
  def description: String = ingredients.mkString(", ")
  def calories: Int = ingredients.foldLeft(0)((totalCalories, ingredient) => totalCalories + ingredient.calories)
}
```

## BurgerBuilder

The `BurgerBuilder` class is used to construct a burger in a step-by-step manner, ensuring that the burger always starts and ends with a bun using GTC for validation.

```scala
sealed trait BuildStep
sealed trait HasLowerBunStep extends BuildStep
sealed trait HasUpperBunStep extends BuildStep

class BurgerBuilder[PassedStep <: BuildStep] private(var ingredients: List[Ingredient]) {
  protected def this() = this(Nil)

  def addLowerBun(bun: Bun)(implicit ev: PassedStep =:= BuildStep) = {
    new BurgerBuilder[HasLowerBunStep](ingredients :+ bun)
  }

  def addUpperBun(bun: Bun)(implicit ev: PassedStep =:= HasLowerBunStep) = {
    new BurgerBuilder[HasUpperBunStep](ingredients :+ bun)
  }

  def addPatty(patty: Patty)(implicit ev: PassedStep =:= HasLowerBunStep) = {
    new BurgerBuilder[HasLowerBunStep](ingredients :+ patty)
  }

  def addCheese()(implicit ev: PassedStep =:= HasLowerBunStep) = {
    new BurgerBuilder[HasLowerBunStep](ingredients :+ Cheese)
  }

  def addLettuce()(implicit ev: PassedStep =:= HasLowerBunStep) = {
    new BurgerBuilder[HasLowerBunStep](ingredients :+ Lettuce)
  }

  def addTomato()(implicit ev: PassedStep =:= HasLowerBunStep) = {
    new BurgerBuilder[HasLowerBunStep](ingredients :+ Tomato)
  }

  def create()(implicit ev: PassedStep =:= HasUpperBunStep): Burger = Burger(ingredients)

  def clearIngredients() = new BurgerBuilder[BuildStep](Nil)
}

object BurgerBuilder {
  def apply() = new BurgerBuilder[BuildStep]()
}
```

## Cook

The `Cook` class uses `BurgerBuilder` to create predefined recipes for different types of burgers.

```scala
class Cook() {
  val burgerBuilder: BurgerBuilder[BuildStep] = BurgerBuilder()

  def cookStandardBurger(): Burger = {
    val standardBurger: Burger = burgerBuilder
      .clearIngredients()
      .addLowerBun(RegularBun)
      .addPatty(RegularPatty)
      .addLettuce()
      .addTomato()
      .addCheese()
      .addUpperBun(CrispyBun)
      .create()

    standardBurger
  }

  def cookFishBurger(): Burger = {
    val fishBurger: Burger = burgerBuilder
      .clearIngredients()
      .addLowerBun(CrispyBun)
      .addPatty(FishPatty)
      .addLettuce()
      .addUpperBun(RegularBun)
      .create()

    fishBurger
  }

  def cookVeganBurger(): Burger = {
    val veganBurger: Burger = burgerBuilder
      .clearIngredients()
      .addLowerBun(RegularBun)
      .addPatty(VeganPatty)
      .addTomato()
      .addLettuce()
      .addUpperBun(CrispyBun)
      .create()

    veganBurger
  }

  def cookCheeseBurger(): Burger = {
    val cheeseBurger: Burger = burgerBuilder
      .clearIngredients()
      .addLowerBun(RegularBun)
      .addPatty(RegularPatty)
      .addCheese()
      .addCheese()
      .addUpperBun(CrispyBun)
      .create()

    cheeseBurger
  }

  def cookBunsOnlyBurger(): Burger = {
    val bunsOnlyBurger: Burger = burgerBuilder
      .clearIngredients()
      .addLowerBun(RegularBun)
      .addUpperBun(CrispyBun)
      .create()

    bunsOnlyBurger
  }
}
```

## Tests

The `Test` object contains various tests demonstrating the creation of different types of burgers using `Cook` and `BurgerBuilder`.

```scala
object Test extends App {
  // Creating a cook
  val cook = new Cook

  // Standard Burger
  val standardBurger: Burger = cook.cookStandardBurger()
  println(s"Standard Burger: ${standardBurger.description}")
  println(s"Standard Burger: ${standardBurger.calories} Calories ")

  // Fish Burger
  val fishBurger: Burger = cook.cookFishBurger()
  println(s"Fish Burger: ${fishBurger.description}")
  println(s"Fish Burger: ${fishBurger.calories} Calories ")

  // Vegan Burger
  val veganBurger: Burger = cook.cookVeganBurger()
  println(s"Vegan Burger: ${veganBurger.description}")
  println(s"Vegan Burger: ${veganBurger.calories} Calories ")

  // Cheese Burger
  val cheeseBurger: Burger = cook.cookCheeseBurger()
  println(s"Cheese Burger: ${cheeseBurger.description}")
  println(s"Cheese Burger: ${cheeseBurger.calories} Calories ")

  // Buns Only Burger
  val bunsOnlyBurger: Burger = cook.cookBunsOnlyBurger()
  println(s"Buns Only Burger: ${bunsOnlyBurger.description}")
  println(s"Buns Only Burger: ${bunsOnlyBurger.calories} Calories ")

  // Creating custom burgers

  // Very meaty burger
  val veryMeatyBurger: Burger = BurgerBuilder()
    .addLowerBun(CrispyBun)
    .addPatty(RegularPatty)
    .addPatty(RegularPatty)
    .addPatty(RegularPatty)
    .addPatty(RegularPatty)
    .addPatty(RegularPatty)
    .addUpperBun(CrispyBun)
    .create()

  println(s"Very Meaty Burger: ${veryMeatyBurger.description}")
  println(s"Very Meaty Burger: ${veryMeatyBurger.calories} Calories ")

  // Invalid Burger (This will not compile)
  // val invalidBurger: Burger = BurgerBuilder()
  //   .addPatty(RegularPatty)
  //   .addUpperBun(CrispyBun)
  //   .create()
}
```

## Usage

To run the tests, simply execute the `Test` object. This will create various burgers using predefined recipes and custom configurations, and print their descriptions and calorie counts.

## Improvements

- Add more ingredients and recipes.
- Improve the validation logic to handle more complex constraints.
- Add support for different types of buns and patties.
- Implement additional design patterns to further enhance the flexibility and maintainability of the code.
