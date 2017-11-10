import org.telegram.telegrambots.api.methods.send.SendMessage;
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
public class StateSearch implements State{

    static final String SEARCH_FOOD = "جستجوی غذا";

    final static String Search_Callback_Manage ="Search_Keyboard_Callback";

    static List<RestaurantsModel> searchResult;
    static RestaurantsModel selected;

    @Override
    public void message() {
        SendMessage msg = new SendMessage(MyBot.chat_id, "غذای مورد نظر را وارد کنید:");
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

        SendMessage msg; // غذای مورد نظر خود را وارد کنید تا نزدیکترین رستوران ها را برایتان نمایش دهیم

        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                searchResult= MyBot.fetchFood(update.getMessage().getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (searchResult != null) {
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> matrix = new ArrayList<>();
                List<InlineKeyboardButton> rows = new ArrayList<>();

                for (RestaurantsModel model : searchResult)
                    rows.add(new InlineKeyboardButton("رستوران " + model.names + "\n" + model.description).setCallbackData(model.names));

                matrix.add(rows);
                msg = new SendMessage(update.getMessage().getChatId(), "نتایج جستجو:").setReplyMarkup(inlineKeyboardMarkup.setKeyboard(matrix));

            } else
                msg = new SendMessage(update.getMessage().getChatId(), "موردی مطابق با جستجوی شما یافت نشد، دوباره امتحان کنید");

            try {
                Main.bot.execute(msg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if(update.hasCallbackQuery()){
            for(RestaurantsModel model : searchResult)
                if(model.names.equals(update.getCallbackQuery().getData())) {
                    selected = model;
                    return;
                }



        }

    }

}
