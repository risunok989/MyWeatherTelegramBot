package Bot.Keyboard;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface KeyboardMarkupBuilder {

    //методы для имплеминтирования, а так же ШАГИ для СОЗДАНИЯ клавиатуры.
    void setChatID(Long chatID);
    KeyboardMarkupBuilder setText (String text);
    KeyboardMarkupBuilder row ();
    KeyboardMarkupBuilder endRow();
    SendMessage build();

}
