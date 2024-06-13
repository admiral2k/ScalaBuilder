package asyncProgramming.BuilderCreationalDesignPattern


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
