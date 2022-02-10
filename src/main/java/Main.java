import Bot.Bot;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class Main {
    public static final String PATH_TO_PROPERTIES = "src/main/resources/config.properties";

    public static void main(String[] args) throws TelegramApiException, IOException, ParseException, InterruptedException {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

}
