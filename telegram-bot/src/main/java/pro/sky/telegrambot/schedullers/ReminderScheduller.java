package pro.sky.telegrambot.schedullers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.repository.RemindRepository;
import pro.sky.telegrambot.repository.entity.RemindEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Component
public class ReminderScheduller {
    private Logger logger = LoggerFactory.getLogger(ReminderScheduller.class);
    Instant start;
    Instant end;
    @Autowired
    private RemindRepository repository;

    @Autowired
    private TelegramBot telegramBot;

    @Scheduled(cron = "0 0/1 * * * *")
    public void sendremind() {
        LocalDateTime ltd = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        Iterable<RemindEntity> all = repository.findByTime(ltd);
            if (Objects.nonNull(all)) {
                all.forEach(e -> {
                    SendMessage message = new SendMessage(e.getChatid(), e.getText());
                    telegramBot.execute(message);
                });
            }
    }
}
//