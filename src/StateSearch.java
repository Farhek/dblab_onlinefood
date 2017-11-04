import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 29/10/2017.
 */
public class StateSearch implements State{

    static final String SEARCH_FOOD = "جستجوی غذا";

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
        }
        RestaurantsModel model = null;
        SendMessage msg; // غذای مورد نظر خود را وارد کنید تا نزدیکترین رستوران ها را برایتان نمایش دهیم
        try {
            model = fetchFood(update.getMessage().getText(), connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        msg = new SendMessage(update.getMessage().getChatId(), "رستوران" + model.names + "\n" + model.description);

        try {
            Main.bot.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private RestaurantsModel fetchFood(String food, Connection connection) throws SQLException {
        String output = null;
        RestaurantsModel model = null;

        ResultSet result = connection.createStatement().executeQuery("select id_restaurants from new_schema.menue , new_schema.restaurants where food = '" + food + "' and restaurants.id_restaurants = menue.id_restaurants;" );
        while (result.next()) {
            model =  new RestaurantsModel(result.getInt("restaurants_id"), result.getString("names"),
                    result.getString("addresses"), result.getString("telephone_numbers"),
                    result.getString("description"), result.getInt("startofwork"),
                    result.getInt("endofwork"));
            break;
        }

        return model;
    }

}
