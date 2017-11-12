/**
 * Created by hosna on 11/4/2017 AD.
 */
public class RestaurantsModel {
    int id_restaurants;
    String names;
    String addresses;
    int discount;

    public RestaurantsModel(int id_restaurants, String names, String addresses, String telephone_numbers, String description, int startofwork, int endofwork , int discount) {
        this.id_restaurants = id_restaurants;
        this.names = names;
        this.addresses = addresses;
        this.telephone_numbers = telephone_numbers;
        this.description = description;
        this.startofwork = startofwork;
        this.endofwork = endofwork;
        this.discount = discount;
    }

    String telephone_numbers;
    String description;
    int startofwork;
    int endofwork;
}
