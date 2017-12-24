import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;

public class StateFoodName implements State {

    @Override
    public void message() {
        SendMessage msg = new SendMessage(MyBot.chat_id, "نام غذا/نوشیدنی را وارد نمایید : ");
        try {
            Main.bot.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ChangeState(String state) {
        MyBot.state = new StateFoodPrice();
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
        if(update.getMessage().hasText()){
            StateMenue.model.food = update.getMessage().getText();

            ChangeState(MyBot.STATE_FOOD_PRICE);
        }
    }
}
