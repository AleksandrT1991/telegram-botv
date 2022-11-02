package pro.sky.telegrambot.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "remind")
public class RemindEntity {
    @Id
    private String id;
    private String text;
    private Instant time;
    private Long chatid;

    public Long getChatid() {
        return chatid;
    }

    public void setChatid(Long chatid) {
        this.chatid = chatid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RemindEntity{" +
                "text='" + text + '\'' +
                ", time=" + time +
                ", chatid=" + chatid +
                ", id='" + id + '\'' +
                '}';
    }
}

