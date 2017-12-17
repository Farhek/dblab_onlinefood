/**
 * Created by hosna on 12/17/2017 AD.
 */
public class RestaurantModel {

    int id_restaurants;
    String names;
    String addresses;
    int discount;
    String telephone_numbers;
    String description;
    int startofwork;
    int endofwork;

    public RestaurantModel(int id_restaurants, String names, String addresses, String telephone_numbers, String description, int startofwork, int endofwork, int discount ) {
        this.id_restaurants = id_restaurants;
        this.names = names;
        this.addresses = addresses;
        this.telephone_numbers = telephone_numbers;
        this.description = description;
        this.startofwork = startofwork;
        this.endofwork = endofwork;
        this.discount = discount;
    }

    public RestaurantModel(String name) {
        names  = name;
    }
}
