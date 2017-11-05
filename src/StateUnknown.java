import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 29/10/2017.
 */
public class StateUnknown implements State {

    private long chat_id;

    StateUnknown(long id){
        this.chat_id = id;
    }

    @Override
    public void message() {

    }

    @Override
    public void ChangeState() {
    }

    @Override
    public void Validate(Update update) {

    }
}
