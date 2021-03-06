import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;

/**
 * Created by hosna on 12/17/2017 AD.
 */
public class StateDiscount implements State {
    @Override
    public void message() {
        SendMessage msg = new SendMessage(MyBot.chat_id, "اگر رستوران شما تخفیف دارد مقدار آن را وارد نمایید : ");
        try {
            Main.bot.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void ChangeState(String state) {
        MyBot.state = new StateMenue();
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
        if(update.getMessage().getText() != null) {
            StateManage.model.discount = Integer.parseInt(update.getMessage().getText());
            try {
                DbHelper.insertRestaurant(StateManage.model);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            ChangeState(MyBot.STATE_MENUE);
        }

    }
}
