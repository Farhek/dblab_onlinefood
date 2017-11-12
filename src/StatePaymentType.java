import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 12/11/2017.
 */
public class StatePaymentType implements State{

    final static String PAY_ONLINE = "PAY_ONLINE";
    final static String PAY_CASH = "PAY_CASH";

    @Override
    public void message() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();
        List<InlineKeyboardButton> rows = new ArrayList<>();

        rows.add(new InlineKeyboardButton("پرداخت نقدی ").setCallbackData(PAY_CASH));
        rows.add(new InlineKeyboardButton("پرداخت آنلاین ").setCallbackData(PAY_ONLINE));

        matrix.add(rows);
        SendMessage msg = new SendMessage(MyBot.chat_id, "نوع پرداخت را انتخاب کنید:").setReplyMarkup(inlineKeyboardMarkup.setKeyboard(matrix));

        try {
            Main.bot.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void ChangeState(String state) {
        MyBot.state = new StatePayment();
        MyBot.user_sate = MyBot.STATE_PAYMENT;

        MyBot.state.message();

        try {
            DbHelper.insertUserState(MyBot.chat_id, MyBot.user_sate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Validate(Update update) {
        if(update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();

            if (call_data.equals(PAY_CASH)) {
                ChangeState(MyBot.STATE_PAYMENT);
            } else if (call_data.equals(PAY_ONLINE)) {
                ChangeState(MyBot.STATE_PAYMENT);
            }
        }
    }
}
