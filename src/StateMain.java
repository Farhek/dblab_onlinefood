import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;

import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 29/10/2017.
 */
public class StateMain implements State {

    static String ORDER_FOOD = "سفارش غذا";
    static String MANAGE_FOOD = "مدیریت رستوران";


    private final static String Main_Callback_Food ="Main_Keyboard_Callback_Food";
    private final static String Main_Callback_Manage ="Main_Keyboard_Callback_Manage";
    final static String Main_Callback_Confirm="Main_Keyboard_Callback_CONFIRM";
    final static String Main_Callback_End ="Main_Keyboard_Callback_END";


    @Override
    public void message() {

    }

    @Override
    public void ChangeState(String state) {
        switch (state){
            case MyBot.STATE_SEARCH : MyBot.state = new StateSearch();
            MyBot.user_sate = MyBot.STATE_SEARCH;
                break;

            case MyBot.STATE_MANAGE : MyBot.state = new StateManage();
                MyBot.user_sate = MyBot.STATE_MANAGE;
                break;
        }

        MyBot.state.message();

        try {
            DbHelper.insertUserState(MyBot.chat_id, MyBot.user_sate);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void Validate(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            if(message_text.equals("/start")){
                try {
                    DbHelper.insertUserState(update.getMessage().getChatId(), MyBot.STATE_MAIN);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                SendMessage welcome= new SendMessage(chat_id, "به آنلاین فود خوش آمدید"); // Create a message object object
                SendMessage choose = new SendMessage(update.getMessage().getChatId(), "گزینه مورد نظر خود را انتخاب کنید"); // غذای مورد نظر خود را وارد کنید تا نزدیکترین رستوران ها را برایتان نمایش دهیم


                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> matrix = new ArrayList<>();

                List<InlineKeyboardButton> row = new ArrayList<>();

                row.add(new InlineKeyboardButton(ORDER_FOOD).setCallbackData(StateMain.Main_Callback_Food));
                row.add(new InlineKeyboardButton(MANAGE_FOOD).setCallbackData(StateMain.Main_Callback_Manage));

                matrix.add(row);

                try {
                    Main.bot.execute(welcome);
                    Main.bot.sendMessage(choose.setReplyMarkup(inlineKeyboardMarkup.setKeyboard(matrix)));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
        }
        else if(update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();

            if (call_data.equals(StateMain.Main_Callback_Food)) {
                ChangeState(MyBot.STATE_SEARCH);
            } else if (call_data.equals(StateMain.Main_Callback_Manage)) {
                ChangeState(MyBot.STATE_MANAGE);
            }
        }
    }
}


