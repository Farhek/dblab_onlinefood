import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by Asus on 29/10/2017.
 */
public interface State {

    void message();
    void ChangeState(String state);
    void Validate(Update update);
}
