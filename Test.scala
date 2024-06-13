package asyncProgramming.BuilderCreationalDesignPattern

object Test extends App {
  // ------Creating Burgers with defined recipes------
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

  // Burger with only buns (This should compile)
  val bunsOnlyBurger: Burger = cook.cookBunsOnlyBurger()
  println(s"Buns Only Burger: ${bunsOnlyBurger.description}")
  println(s"Buns Only Burger: ${bunsOnlyBurger.calories} Calories ")

  // ------Creating Custom Burgers------

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