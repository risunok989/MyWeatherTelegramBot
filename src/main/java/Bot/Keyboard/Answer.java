package Bot.Keyboard;

import Bot.Bot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public  class Answer {

    // Создаем обьект разметки клавиатуры ( создание клавиатуры)
    InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();

public void  setInlineKeyboard(){
    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(); // выстраиваем положение кнопок
    inlineKeyboardButton.setText("ТЫКНИ МЕНЯ1"); // Текст (Что будет написано на самой кнопке)
    inlineKeyboardButton.setCallbackData("Button \\\"ТЫКНИ МЕНЯ1\\\" has been pressed"); // Что будет отсылатся серверу при нажатии на кнопку

    // создаём list кнопок
    List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
    keyboardButtonsRow1.add(inlineKeyboardButton);

    inlineKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardButtonsRow1));
}

    public static String setMessageKeyboardToStart(String msg) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboadFirstRow = new KeyboardRow();
        KeyboardRow keyboarSecondRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        if (msg.equals("/start") || msg.equals("ы")) {
            keyboard.clear();
            keyboadFirstRow.clear();
            keyboarSecondRow.clear();
            keyboadFirstRow.add("клацни меня, сучка1");
            keyboadFirstRow.add("Нет, лучше меня, малышь2");
            keyboarSecondRow.add("БАН");
            keyboard.add(keyboadFirstRow);
            keyboard.add(keyboarSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "выбирай....";
        }
        return "Если возникли проблемы, воспользуйтесь /help.";
    }

    ;

    public static SendMessage getMessageInline(Long chatID) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Кнопка 1");
        inlineKeyboardButton1.setCallbackData("Кнопка 1");

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Кнопка 2");
        inlineKeyboardButton2.setCallbackData("Кнопка 2");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);


        inlineKeyboardMarkup.setKeyboard(rowList);

        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(chatID.toString());
        sendMessage.setText("Выбирете что Вам нужно?");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);


        return sendMessage;
//        return new SendMessage().setChatId(chatId).setText("Пример").setReplyMarkup(inlineKeyboardMarkup);
    }

    ;

    public static SendMessage getMessageInline2(Long chatID) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Кнопка 1");
        inlineKeyboardButton1.setCallbackData("Кнопка STOP");

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Кнопка 2");
        inlineKeyboardButton2.setCallbackData("Кнопка STOP");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);


        inlineKeyboardMarkup.setKeyboard(rowList);

        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(chatID.toString());
        sendMessage.setText("Выбирете что Вам нужно?");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);


        return sendMessage;

    }
}
