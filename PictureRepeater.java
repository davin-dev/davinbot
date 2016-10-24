package org.logan.echobot;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * created with ♥ by alireza :)
 */
public class PictureRepeater extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();

            if (message.getPhoto() != null) {
                List<PhotoSize> photos = message.getPhoto();
                forward(photos.get(photos.size() - 1), chatId);
            }
        }
    }

    private URLConnection getConnection(String filePath) {
        URLConnection conn = null;
        try {
            URL url = new URL("https://api.telegram.org/file/bot"
                              + getBotToken() + "/" + filePath);
            try {
                conn = url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            System.out.println("That wasn't meant to happen.");
        }
        return conn;
    }

    private void forward(PhotoSize photo, String chatId) {
        GetFile gf = new GetFile();
        gf.setFileId(photo.getFileId());

        SendPhoto sp = new SendPhoto();
        sp.setChatId(chatId);

        try {
            String path = getFile(gf).getFilePath();
            try (InputStream in = getConnection(path).getInputStream()) {
                sp.setNewPhoto("photo.jpg", in);
                sendPhoto(sp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BotInfo.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BotInfo.BOT_TOKEN;
    }

}