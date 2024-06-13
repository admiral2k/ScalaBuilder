package asyncProgramming.BuilderCreationalDesignPattern

// Burger Class
case class Burger(ingredients: List[Ingredient]) {
  def description: String = ingredients.mkString(", ")

  def calories: Int = ingredients.foldLeft(0)((totalCalories, ingredient) => totalCalories + ingredient.calories)
}
