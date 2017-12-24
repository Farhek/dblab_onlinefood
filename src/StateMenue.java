import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hosna on 12/17/2017 AD.
 */
public class StateMenue implements State {

    static FoodModel model;

    @Override
    public void message() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        SendMessage msg = new SendMessage(MyBot.chat_id, " ... ");
        row.add(new InlineKeyboardButton("اضافه کردن غذا/نوشیدنی جدید").setCallbackData("ADD_NEW_FOOD"));

        matrix.add(row);

        try {
            Main.bot.sendMessage(msg.setReplyMarkup(inlineKeyboardMarkup.setKeyboard(matrix)));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ChangeState(String state) {
        MyBot.state = new StateFoodType();
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
            model = new FoodModel();
            model.id_restuarants = Math.toIntExact(MyBot.chat_id);
            ChangeState(MyBot.STATE_FOOD_TYPE);
        }
    }
}
