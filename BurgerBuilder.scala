package asyncProgramming.BuilderCreationalDesignPattern


// Steps for validation by GTC (Burger always should start with and end with a Bun)
sealed trait BuildStep

sealed trait HasLowerBunStep extends BuildStep

sealed trait HasUpperBunStep extends BuildStep


// Builder
class BurgerBuilder[PassedStep <: BuildStep] private(var ingredients: List[Ingredient]) {
  protected def this() = this(Nil)

  // Adding lower bun
  // only takes CrispyBun or RegularBun as an argument
  def addLowerBun(bun: Bun)(implicit ev: PassedStep =:= BuildStep) = {
    new BurgerBuilder[HasLowerBunStep](ingredients :+ bun)
  }

  // Adding upper bun
  // only takes CrispyBun or RegularBun as an argument
  def addUpperBun(bun: Bun)(implicit ev: PassedStep =:= HasLowerBunStep) = {
    new BurgerBuilder[HasUpperBunStep](ingredients :+ bun)
  }

  // Adding patty
  // only takes RegularPatty, FishPatty or VeganPatty as an argument
  def addPatty(patty: Patty)(implicit ev: PassedStep =:= HasLowerBunStep) = {
    new BurgerBuilder[HasLowerBunStep](ingredients :+ patty)
  }

  // Adding cheese
  def addCheese()(implicit ev: PassedStep =:= HasLowerBunStep) = {
    new BurgerBuilder[HasLowerBunStep](ingredients :+ Cheese)
  }

  // Adding lettuce
  def addLettuce()(implicit ev: PassedStep =:= HasLowerBunStep) = {
    new BurgerBuilder[HasLowerBunStep](ingredients :+ Lettuce)
  }

  // Adding tomato
  def addTomato()(implicit ev: PassedStep =:= HasLowerBunStep) = {
    new BurgerBuilder[HasLowerBunStep](ingredients :+ Tomato)
  }

  // Returns the burger
  def create()(implicit ev: PassedStep =:= HasUpperBunStep): Burger = Burger(ingredients)

  // Needed for reusing the same builder for many Burgers
  def clearIngredients() = {
    new BurgerBuilder[BuildStep](Nil)
  }
}

object BurgerBuilder {
  def apply() = new BurgerBuilder[BuildStep]()
}
