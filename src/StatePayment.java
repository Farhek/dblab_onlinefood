import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 12/11/2017.
 */
public class StatePayment implements State {
    @Override
    public void message() {
        SendMessage msg = new SendMessage(MyBot.chat_id ,"نام غذا : " + StateSearch.selected.food +  "\n" + "قیمت غذا : " + StateSearch.selected.price  );
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        SendMessage choose2 = new SendMessage(MyBot.chat_id, " آیا تایید می کنید؟ ");
        row.add(new InlineKeyboardButton(CONFIRM_ORDER).setCallbackData(StateMain.Main_Callback_Confirm));
        row.add(new InlineKeyboardButton(END_ORDER).setCallbackData(StateMain.Main_Callback_End));

        matrix.add(row);

        try {
            Main.bot.execute(msg);
            Main.bot.sendMessage(choose2.setReplyMarkup(inlineKeyboardMarkup.setKeyboard(matrix)));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void ChangeState(String state) {
        MyBot.state = new StateEnd();
        MyBot.user_sate = MyBot.STATE_END;

        MyBot.state.message();

        try {
            DbHelper.insertUserState(MyBot.chat_id, MyBot.user_sate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static String CONFIRM_ORDER = " تایید ";
    static String END_ORDER = " لغو ";
    private final static String Main_Callback_CONFIRM="Main_Keyboard_Callback_Food";
    private final static String Main_Callback_Manage ="Main_Keyboard_Callback_Manage";


    @Override
    public void Validate(Update update) {


      if(update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();

            if (call_data.equals(StateMain.Main_Callback_Confirm)) {
                ChangeState(MyBot.STATE_END);
            } else if (call_data.equals(StateMain.Main_Callback_End)) {
                ChangeState(MyBot.STATE_END);
            }
        }

    }
}



