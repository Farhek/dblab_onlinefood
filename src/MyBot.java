/**
 * Created by Asus on 15/10/2017.
 */

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot  {

    public static String user_sate = null;

    final static String STATE_MAIN = "MAIN_STATE";
    final static String STATE_SEARCH = "SEARCH_STATE";
    final static String STATE_MANAGE = "MANAGE_STATE";
    final static String STATE_ORDER = "ORDER_STATE";
    final static String STATE_TYPE = "TYPE_STATE";
    final static String STATE_PAYMENT = "PAYMENT_STATE";
    final static String STATE_END = "PAYMENT_END";
    final static String STATE_UNSUCCESSFUL = "UNSUCCESSFUL";


    final static String STATE_ADDRESS = "ADDRESS";
    final static String STATE_DESCRIPTION = "Description";
    final static String STATE_PHONE = "PHONE";
    final static String STATE_DISCOUNT = "DISCOUNT";

    final static String STATE_MENUE = "MENUE";
    final static String STATE_FOOD_NAME = "food_name";
    final static String STATE_FOOD_TYPE = "food_type";
    final static String STATE_FOOD_PRICE = "food_price";

    final  static String url = "jdbc:mysql://localhost:3306/new_schema";
    final  static String username = "newuser";
    final  static String password = "Mysqlpass95/";

    static State state = null;
    static long chat_id;

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().getChatId() != chat_id)
            chat_id = update.getMessage().getChatId();

        if(update.getMessage().getText().equals("/start"))
            state = new StateMain();
        else {

            try {
                if (update.getMessage() != null)
                    user_sate = DbHelper.fetchUserState(update.getMessage().getChatId());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (user_sate == null) {
                state = new StateMain();
            } else if (user_sate.equals(STATE_MAIN)) {
                state = new StateMain();
            } else if (user_sate.equals(STATE_SEARCH)) {
                state = new StateSearch();
            } else if (user_sate.equals(STATE_MANAGE)) {
                state = new StateManage();
            } else if (user_sate.equals(STATE_ORDER)) {
                state = new StateOrder();
            } else if (user_sate.equals(STATE_TYPE)) {
                state = new StatePaymentType();
            } else if (user_sate.equals(STATE_PAYMENT)) {
                state = new StatePayment();
            } else if (user_sate.equals(STATE_END)) {
                state = new StateEnd();
            } else
                state = new StateMain();
        }

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



}