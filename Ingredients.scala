package asyncProgramming.BuilderCreationalDesignPattern

// Ingredients
sealed trait Ingredient {
  val calories: Int
}

// Buns
sealed trait Bun extends Ingredient

case object RegularBun extends Bun {
  override val calories: Int = 88
}

case object CrispyBun extends Bun {
  override val calories: Int = 102
}

// Patties
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

// Other
case object Cheese extends Ingredient {
  override val calories: Int = 68
}

case object Lettuce extends Ingredient {
  override val calories: Int = 5
}

case object Tomato extends Ingredient {
  override val calories: Int = 3
}
