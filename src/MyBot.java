/**
 * Created by Asus on 15/10/2017.
 */

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.*;

public class MyBot extends TelegramLongPollingBot  {


    private String user_sate = null;

    final static String STATE_UNKNOWN = "UNKNOWN_STATE";
    final static String STATE_START = "START_STATE";
    final static String STATE_MAIN = "MAIN_STATE";
    final static String STATE_SEARCH = "SEARCH_STATE";


    final static String url = "jdbc:mysql://localhost:3306/new_schema";
    final static String username = "newuser";
    final static String password = "Mysqlpass95/";

    @Override
    public void onUpdateReceived(Update update) {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        };


        try {
            user_sate = fetchUserState(update.getMessage().getChatId(), connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(user_sate == null)
            user_sate = STATE_MAIN;

        if(user_sate.equals(STATE_UNKNOWN)){

        } else if(user_sate.equals(STATE_MAIN)){
            StateMain main = new StateMain(update.getMessage().getChatId());
            main.Validate(update.getMessage());
        } else if(user_sate.equals(STATE_SEARCH)){

        }


        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            if(message_text.equals("/start") || message_text.equals("/Start")){
                SendMessage message= new SendMessage(chat_id, "به آنلاین فود خوش آمدید"); // Create a message object object
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    System.out.println("Database connected!");
                    Statement statement;
                    if(connection != null) {
                        statement = connection.createStatement();
                        statement.setQueryTimeout(30);  // set timeout to 30 sec.
                        statement.executeUpdate("insert into new_schema.users (user_id, user_message) values (" + chat_id + ",'" + message_text + "');");
                    }
                } catch (SQLException e) {
                    throw new IllegalStateException("Cannot connect the database!", e);
                }
            }
        }
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

    private String fetchUserState(long chatID, Connection connection) throws SQLException {
        String state = null;
        ResultSet result = connection.createStatement().executeQuery("select user_state from new_schema.user_state where user_id = '" + chatID + "';" );
        while (result.next()) {
            state = result.getString("state");
            break;
        }

        return state;
    }

}