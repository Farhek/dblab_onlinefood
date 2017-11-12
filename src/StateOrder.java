import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;

/**
 * Created by Asus on 10/11/2017.
 */
public class StateOrder implements State {

    static String order_address;
    @Override
    public void message() {
        SendMessage msg = new SendMessage(MyBot.chat_id, "آدرس خود را وارد کنید:");
        try {
            Main.bot.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void ChangeState(String state) {
        MyBot.state = new StatePaymentType();
        MyBot.user_sate = MyBot.STATE_TYPE;

        MyBot.state.message();

        try {
            DbHelper.insertUserState(MyBot.chat_id, MyBot.user_sate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Validate(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            order_address = update.getMessage().getText();
            ChangeState(MyBot.STATE_TYPE);
        }
    }
}
