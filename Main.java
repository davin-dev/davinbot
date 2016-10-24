package org.logan.echobot;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

/**
 * created with ♥ by alireza :)
 */
public class Main {

    public static void main(String[] args) {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new PictureRepeater());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}