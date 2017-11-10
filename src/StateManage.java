import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Asus on 29/10/2017.
 */
public class StateManage implements State {


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
