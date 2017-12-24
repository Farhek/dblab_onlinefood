import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hosna on 11/26/2017 AD.
 */
public class StateEnd implements State {
    @Override
    public void message() {

        SendMessage msg = new SendMessage(MyBot.chat_id, "سفارش شما با موفقیت ثبت شد و ارسال خواهد شد");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        SendMessage msg2 = new SendMessage(MyBot.chat_id, "...");
        row.add(new InlineKeyboardButton("سفارش مجدد").setCallbackData("ORDER_AGAIN"));

        matrix.add(row);

        try {
            Main.bot.execute(msg);
            Main.bot.sendMessage(msg2.setReplyMarkup(inlineKeyboardMarkup.setKeyboard(matrix)));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void ChangeState(String state) {
        MyBot.state = new StateSearch();
        MyBot.user_sate = state;

        MyBot.state.message();

        try {
            DbHelper.insertUserState(MyBot.chat_id, MyBot.user_sate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Validate(Update update) {
        if(update.hasCallbackQuery()){
            if(update.getCallbackQuery().getData().equals("ORDER_AGAIN"))
                ChangeState(MyBot.STATE_SEARCH);
        }
    }
}
