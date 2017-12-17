import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class StateFoodName implements State {

    @Override
    public void message() {
        SendMessage msg = new SendMessage(MyBot.chat_id, "نام : ");
        try {
            Main.bot.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ChangeState(String state) {
        MyBot.state = new StatePrice();
        MyBot.user_sate = state;

        MyBot.state.message();
    }

    @Override
    public void Validate(Update update) {
        if(update.getMessage().getText() != null) {
          //  StateManage.model = update.getMessage().getText();
            ChangeState(MyBot.STATE_PRICE);
        }


    }
}
