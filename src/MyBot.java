/**
 * Created by Asus on 15/10/2017.
 */

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot  {

    private String user_sate = null;

    final static String STATE_MAIN = "MAIN_STATE";
    final static String STATE_SEARCH = "SEARCH_STATE";
    final static String STATE_MANAGE = "MANAGE_STATE";


    final private static String url = "jdbc:mysql://localhost:3306/new_schema";
    final private static String username = "newuser";
    final private static String password = "Mysqlpass95/";

    static State state = null;
    static long chat_id;

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().getChatId() != chat_id)
            chat_id = update.getMessage().getChatId();


        try {
            if(update.getMessage() != null)
            user_sate = fetchUserState(update.getMessage().getChatId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(user_sate == null){
            state = new StateMain();
        } else if(user_sate.equals(STATE_MAIN)){
            state = new StateMain();
        } else if(user_sate.equals(STATE_SEARCH)){
            state = new StateSearch();
        } else if(user_sate.equals(STATE_MANAGE)){
            state = new StateManage();
        }else
            state = new StateMain();
        
        //******************//
        state.Validate(update);


/*            if (call_data.equals(StateMain.ORDER_FOOD)) {
                String answer = "";
                EditMessageText new_message = new EditMessageText()
                        .setChatId(chat_id)
                        .setMessageId(toIntExact(message_id))
                        .setText(answer);
                try {
                    execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }*/

    }

    @Override
    public String getBotUsername() {
        return "onlinefoodBot";
    }

    @Override
    public String getBotToken() {
        return "459213601:AAGgv8zwspiYq0xwbLW83U5iH4hAH043HF0";
    }



    public void sendMsg(SendMessage message) throws TelegramApiException {
        execute(message);
    }

    private String fetchUserState(long chatID) throws SQLException {
        getConnection();
        String state = null;
        ResultSet result = connection.createStatement().executeQuery("select user_state from new_schema.user_state where user_id = '" + chatID + "';" );
        while (result.next()) {
            state = result.getString("user_state");
            break;
        }

        return state;
    }

    private static Connection connection;

    static void getConnection(){
        if(connection == null)
            try {
                connection = DriverManager.getConnection(url, username, password);
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


    static List<RestaurantsModel> fetchFood(String food) throws SQLException {
        getConnection();
        List<RestaurantsModel> models = new ArrayList<>();

        ResultSet result = connection.createStatement().executeQuery("select id_restaurants from new_schema.menue , new_schema.restaurants where food = '" + food + "' and restaurants.id_restaurants = menue.id_restaurants;" );
        while (result.next()) {
            models.add(new RestaurantsModel(result.getInt("restaurants_id"), result.getString("names"),
                    result.getString("addresses"), result.getString("telephone_numbers"),
                    result.getString("description"), result.getInt("startofwork"),
                    result.getInt("endofwork")));

        }

        return models;
    }

}