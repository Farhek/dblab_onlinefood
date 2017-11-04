/**
 * Created by hosna on 11/4/2017 AD.
 */
public class MenueModel {
    int id_menue;
    int id_restaurants;
    String food;
    int type;

    public MenueModel(int id_menue, int id_restaurants, String food, int type) {
        this.id_menue = id_menue;
        this.id_restaurants = id_restaurants;
        this.food = food;
        this.type = type;
    }
}
