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
        };

        SendMessage msg = new SendMessage(update.getMessage().getChatId(), "غذای مورد نظر خود را وارد کنید:"); // غذای مورد نظر خود را وارد کنید تا نزدیکترین رستوران ها را برایتان نمایش دهیم
        try {
            fetchFood(update.getMessage().getText(), connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String fetchFood(String food, Connection connection) throws SQLException {
        String state = null;
        ResultSet result = connection.createStatement().executeQuery("select * from new_schema.menue where food = '" + food + "';" );
        while (result.next()) {
            state = result.getString("state");
            break;
        }

        return state;
    }
}
