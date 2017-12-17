import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by hosna on 12/17/2017 AD.
 */
public class StatePhone implements State {

    @Override
    public void message() {
        SendMessage msg = new SendMessage(MyBot.chat_id, "شماره تماس رستوران را وارد کنید : ");
        try {
            Main.bot.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void ChangeState(String state) {
        MyBot.state = new StateDiscount();
        MyBot.user_sate = state;

        MyBot.state.message();
    }

    @Override
    public void Validate(Update update) {
        if(update.getMessage().getText() != null) {
            StateManage.model.telephone_numbers = update.getMessage().getText();
            ChangeState(MyBot.STATE_DISCOUNT);
        }

    }
}
