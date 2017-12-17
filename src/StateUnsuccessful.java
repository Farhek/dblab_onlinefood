import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class StateUnsuccessful implements State {

    @Override
    public void message() {

        SendMessage msg = new SendMessage(MyBot.chat_id, "سفارش شما لغو شد");
        try {
            Main.bot.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void ChangeState(String state) {

    }

    @Override
    public void Validate(Update update) {

    }
}
