import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;

public class StateFoodPrice implements State {
    @Override
    public void message() {
        SendMessage msg = new SendMessage(MyBot.chat_id, "قیمت غذا/نوشیدنی را به تومان وارد کنید: ");
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
        if(update.getMessage().hasText()){
            StateMenue.model.price = Integer.parseInt(update.getMessage().getText());

            SendMessage msg = new SendMessage(MyBot.chat_id, "آیتم با موففیت ثبت شد ");
            try {
                Main.bot.execute(msg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            try {
                DbHelper.insertFood(StateMenue.model);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            ChangeState(MyBot.STATE_MENUE);
        }

    }
}
