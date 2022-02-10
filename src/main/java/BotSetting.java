import org.telegram.telegrambots.meta.TelegramBotsApi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BotSetting {
    private BotSetting() {
    } // приватный конструктор, чтобы доступ к нему был закрыть за пределами класса,
    // тогда он не сможет возвращать новые объекты

    /*
    В классе BotSettings используется порождающий шаблон
    проектирования СИНГЛТОН, служит он для того, чтобы не было
    возможности создать несколько экземпляров класса в одном потоке.
     */

    public static final String PATH_TO_PROPERTIES = "src/main/resources/config.properties";
    private static BotSetting instance; //приватное статическое поле, содержащее одиночный объект
    private Properties properties;
    private String token;
    private String userName;
    private TelegramBotsApi telegramBotsApi;

    public String getToken() {
        return token;
    }

    public String getUserName() {
        return userName;
    }

    public static BotSetting getInstance() { //статический создающий метод
        // , который будет использоваться для получения одиночки

        if (instance == null)
            instance = new BotSetting();
        return instance;

    }

    {
        try {
            properties = new Properties();
            try (FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES) {
            }) {
                properties.load(fileInputStream);
            } catch (IOException e) {
                try {
                    throw new IOException(String.format("Error loading properties file '%s'", PATH_TO_PROPERTIES));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            token = properties.getProperty("tokenBot");
            if (token == null) {
                throw new RuntimeException("Token value is NULL");
            }
            userName = properties.getProperty("nameBot");
            if (userName == null)
                throw new RuntimeException("UserName value is NULL");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}

