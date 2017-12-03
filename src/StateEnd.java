import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;

/**
 * Created by hosna on 11/26/2017 AD.
 */
public class StateEnd implements State {
    @Override
    public void message() {
        }



    @Override
    public void ChangeState(String state) {

    }

    @Override
    public void Validate(Update update) {

    }
}
