/**
 * Created by hosna on 11/8/2017 AD.
 */



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {

    private static Connection _con = null;

    static void init() {
        checkConnection();
    }

    private static void checkConnection() {
        if (_con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                _con = DriverManager.getConnection(MyBot.url, MyBot.username, MyBot.password);
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(2);
            }
        }
    }
     static String fetchUserState(long chatID) throws SQLException {
        getConnection();
        String state = null;
        ResultSet result = connection.createStatement().executeQuery("select user_state from new_schema.user_state where user_id = '" + chatID + "';" );
        while (result.next()) {
            state = result.getString("user_state");
        }

        return state;
    }

    private static Connection connection;

    private static void getConnection(){
        if(connection == null)
            try {
                connection = DriverManager.getConnection(MyBot.url, MyBot.username, MyBot.password);
            } catch (SQLException e) {
                e.printStackTrace();
            };

    }

    static void insertUserState(long chat_id, String state) throws SQLException{
        getConnection();
        System.out.println("Database connected!");
        Statement statement;
        if(connection != null) {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("insert into new_schema.users (user_id, user_message) values (" + chat_id + ",'');");
            statement.executeUpdate("insert into new_schema.user_state (user_id, user_state) values (" + chat_id + ",'" + state + "');");
        }
    }

    static List<RestaurantsFoodModel> fetchFood(String food) throws SQLException {
        getConnection();
        List<RestaurantsFoodModel> models = new ArrayList<>();

        ResultSet result = connection.createStatement().executeQuery("select * from new_schema.menue , new_schema.restaurants where food = '" + food + "' and restaurants.id_restaurants = menue.id_restuarants;" );
        while (result.next()) {
            models.add(new RestaurantsFoodModel(result.getInt("id_restaurants"), result.getString("names"),
                    result.getString("addresses"), result.getString("telephone_numbers"),
                    result.getString("description"), result.getInt("startofwork"), result.getInt("discount"),
                    result.getInt("endofwork"), result.getInt("idmenue"), result.getInt("price") , result.getString("food")));

        }

        return models;
    }

    static void insertOrderAddress(long chat_id, String address) throws SQLException {
        getConnection();
        System.out.println("Database connected!");
        Statement statement;
        if(connection != null) {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("insert into new_schema.user_state (user_id, address) values (" + chat_id + ",'" + address + "');");
        }
    }



}
