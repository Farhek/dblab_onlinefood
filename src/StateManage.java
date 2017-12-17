
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;


/**
 * Created by Asus on 29/10/2017.
 */
public class StateManage implements State {

    static RestaurantModel model;
    @Override
    public void message() {
        SendMessage msg = new SendMessage(MyBot.chat_id, "به قسمت مدیریت رستوران خوش آمدید. ");
        SendMessage msg2 = new SendMessage(MyBot.chat_id, "نام رستوران خود را وارد کنید : ");
        try {
            Main.bot.execute(msg);
            Main.bot.execute(msg2);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void ChangeState(String state) {
        MyBot.state = new StateAddress();
        MyBot.user_sate = state;

        MyBot.state.message();
    }

    @Override
    public void Validate(Update update) {
        if(update.getMessage().getText() != null) {
            model = new RestaurantModel(update.getMessage().getText());
            ChangeState(MyBot.STATE_ADDRESS);
        }

    }

}
