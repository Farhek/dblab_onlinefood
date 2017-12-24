import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;

/**
 * Created by hosna on 12/17/2017 AD.
 */
public class StateDescription implements State{
    @Override
    public void message() {
        SendMessage msg = new SendMessage(MyBot.chat_id, "توضیحات رستوران را وارد کنید : ");
        try {
            Main.bot.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void ChangeState(String state) {
        MyBot.state = new StatePhone();
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
            StateManage.model.description= update.getMessage().getText();
            ChangeState(MyBot.STATE_PHONE);
        }

    }
}
