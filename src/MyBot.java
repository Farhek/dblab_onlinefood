/**
 * Created by Asus on 15/10/2017.
 */

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyBot extends TelegramLongPollingBot implements State {

    private final static String url = "jdbc:mysql://localhost:3306/new_schema";
    private final static String username = "newuser";
    private final static String password = "Mysqlpass95/";

    @Override
    public void onUpdateReceived(Update update) {
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
                try (Connection connection = DriverManager.getConnection(url, username, password)) {
                    System.out.println("Database connected!");
                    Statement statement = connection.createStatement();
                    statement.setQueryTimeout(30);  // set timeout to 30 sec.
                    statement.executeUpdate("insert into new_schema.users (user_id, user_message) values (" + chat_id + ",'" + message_text + "');" );
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

    @Override
    public void message() {
        

    }

    @Override
    public void ChangeState() {

    }

    @Override
    public void Validate() {

    }
}