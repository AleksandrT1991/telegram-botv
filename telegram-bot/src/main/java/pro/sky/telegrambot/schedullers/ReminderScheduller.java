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

@Component
public class ReminderScheduller {
    private Logger logger = LoggerFactory.getLogger(ReminderScheduller.class);

    @Autowired
    private RemindRepository repository;

    @Autowired
    private TelegramBot telegramBot;

    @Scheduled(cron = "0 0/1 * * * *")
    public void sendremind() {
        Iterable<RemindEntity> all = repository.findAll();
        LocalDateTime ltd = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        int day = ltd.getDayOfMonth();
        int month = ltd.getMonthValue();
        int year = ltd.getYear();
        int hour = ltd.getHour();
        int minute = ltd.getMinute();
        logger.info("Текущее время {}.{}.{} {}.{}", day, month, year, hour, minute);
        all.forEach(x -> {
            LocalDateTime ltd2 = LocalDateTime.ofInstant(x.getTime(), ZoneId.systemDefault());
            int day2 = ltd.getDayOfMonth();
            int month2 = ltd.getMonthValue();
            int year2 = ltd.getYear();
            int hour2 = ltd.getHour();
            int minute2 = ltd.getMinute();
            logger.info("Время из напоминания {}.{}.{} {}.{}", day2, month2, year2, hour2, minute2);
            if (day == day2 && month == month2 && year == year2 && hour == hour2 && minute == minute2) {
                SendMessage message = new SendMessage(x.getChatid(), x.getText());
                telegramBot.execute(message);
            }
        });
    }
}
//