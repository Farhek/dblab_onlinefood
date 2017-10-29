import org.telegram.telegrambots.api.objects.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import org.telegram.telegrambots.api.methods.send.SendMessage;
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

    long chat_id;

    public StateMain(long chat_id){
        this.chat_id = chat_id;
    }


    @Override
    public void message() {

    }

    @Override
    public void ChangeState() {

    }

    @Override
    public void Validate(Message command) {
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
        SendMessage msg = new SendMessage(chat_id, "غذای مورد نظر خود را وارد کنید تا نزدیکترین رستوران ها را برایتان نمایش دهیم");

        List<KeyboardRow> keybRows = new ArrayList<>();
        KeyboardRow keyRow = new KeyboardRow();
        keyRow.add("جستجو");
        keyRow.add("پیگیری");
        keybRows.add(keyRow);
        msg.setReplyMarkup(new ReplyKeyboardMarkup().setKeyboard(keybRows));

        try {
            Main.bot.sendMsg(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        if (command.equals("سفارش غذا") || command.equals("سفارش غذا")) {

            } else if (command.equals("جست و جو رستوران") || command.equals("جست و جو رستوران")) {

            } else if (command.equals("جست و جو رستوران") || command.equals("جست و جو رستوران")) {

            } else{

            }
        }
    }


