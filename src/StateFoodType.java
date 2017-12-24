import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StateFoodType implements State{

    @Override
    public void message() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        SendMessage msg = new SendMessage(MyBot.chat_id, "...");
        row.add(new InlineKeyboardButton("غذا").setCallbackData("ّFOOD"));
        row.add(new InlineKeyboardButton("پیش غذا").setCallbackData("DESERT"));
        row.add(new InlineKeyboardButton("نوشیدنی").setCallbackData("DRINK"));

        matrix.add(row);

        try {
            Main.bot.sendMessage(msg.setReplyMarkup(inlineKeyboardMarkup.setKeyboard(matrix)));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ChangeState(String state) {
        MyBot.state = new StateFoodName();
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
            switch (update.getCallbackQuery().getData()){
                case "FOOD" : StateMenue.model.type = 0;
                case "DESERT": StateMenue.model.type = 1;
                case "DRINK": StateMenue.model.type = 2;
            }

            ChangeState(MyBot.STATE_FOOD_NAME);
        }
    }
}
