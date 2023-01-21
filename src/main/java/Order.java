import java.util.List;
public class Order {

    private String[] ingredients;
    public Order(List<String> ingredients) {

        this.ingredients = ingredients.toArray(new String[0]);
    }

     public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
