package pro.sky.telegrambot.processors;

import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.repository.RemindRepository;
import pro.sky.telegrambot.repository.entity.RemindEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class MessageProcessor {
    public static final Pattern PATTERN = Pattern.compile("\\/remind [0-9]{2}.[0-9]{2}.[0-9]{4} [0-9]{2}.[0-9]{2} [\\s\\S]+");
    private static final String TEXT = " [\\s\\D]+";
    public static final Pattern TEXT_PATTERN = Pattern.compile(TEXT);
    public static final Pattern DATETIME = Pattern.compile(" [0-9]{2}.[0-9]{2}.[0-9]{4} [0-9]{2}.[0-9]{2}");
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy mm.HH");
    private final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
    private final RemindRepository repository;

    public String process(Update update) {
        logger.info("Запускам метод");
        if (Objects.nonNull(update.message().text())) {
            String inputMessage = update.message().text();
            logger.info("Входящее сообщение {}", inputMessage);
            //   /remind 31.11.2022 12.30 Сделай ДЗ
            if (PATTERN.matcher(inputMessage).matches()){
                logger.info("Сохраняем в базу напоминание");
                repository.save(generateEntity(update));
            }
        }
        return null;
    }

    private RemindEntity generateEntity(Update update) {
        RemindEntity remindEntity = new RemindEntity();
        remindEntity.setChatid(update.message().chat().id());
        remindEntity.setText(getText(update.message().text()));
        remindEntity.setTime(getTime(update.message().text()));
        logger.info("Remind Entity {}", remindEntity);
        return remindEntity;
    }

    public Instant getTime(String text) {
        Matcher matcher = DATETIME.matcher(text);
        if (matcher.find()) {
            try {
                return SIMPLE_DATE_FORMAT.parse(matcher.group(0)).toInstant();
            } catch (ParseException e) {
                logger.error("Произошла ошибка во время парсинга текста в дату");
            }
        }
        return null;
    }

    public String getText(String text) {
        Matcher matcher = TEXT_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }
}
//