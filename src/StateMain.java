import org.telegram.telegrambots.api.objects.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 29/10/2017.
 */
public class StateMain implements State {

/*
    private long chat_id;

    StateMain(long chat_id){
        this.chat_id = chat_id;
    }
*/

    static String ORDER_FOOD = "سفارش غذا";
    static String MANAGE_FOOD = "مدیریت رستوران";

    @Override
    public void message() {

    }

    @Override
    public void ChangeState() {
    }

    @Override
    public void Validate(Update update) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(MyBot.url, MyBot.username, MyBot.password);
        } catch (SQLException e) {
            e.printStackTrace();
        };

/*
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton("Update message text").callbackData("update_msg_text"));
        // Set the keyboard to the markup
        rowsInline.add(rowInline);
*/

/*        InlineKeyboardButton[][] inline_keyboard = new InlineKeyboardButton[2][1];
        inline_keyboard[0][0] = new InlineKeyboardButton("جستجوی غذا").callbackData("");
        inline_keyboard[1][0] = new InlineKeyboardButton("پیگیری غذا");

        List<Keyboard> keyboards = new ArrayList<>();
        keyboards.add(new InlineKeyboardMarkup(inline_keyboard));
        */

        // Add it to the message
        SendMessage msg = new SendMessage(update.getMessage().getChatId(), "گزینه مورد نظر خود را انتخاب کنید"); // غذای مورد نظر خود را وارد کنید تا نزدیکترین رستوران ها را برایتان نمایش دهیم

        List<KeyboardRow> keybRows = new ArrayList<>();
        KeyboardRow keyRow = new KeyboardRow();
        keyRow.add(ORDER_FOOD);
        keyRow.add(MANAGE_FOOD);

        keybRows.add(keyRow);
        msg.setReplyMarkup(new ReplyKeyboardMarkup().setKeyboard(keybRows));

        try {
            Main.bot.sendMsg(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}


