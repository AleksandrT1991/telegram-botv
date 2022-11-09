package pro.sky.telegrambot.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.repository.entity.RemindEntity;

import java.time.Instant;
import java.time.LocalDateTime;

@Repository
public interface RemindRepository extends CrudRepository<RemindEntity, Long> {

    @Query(
      value = "SELECT * FROM REMIND r WHERE  r.time = :time", nativeQuery = true)
    Iterable<RemindEntity> findByTime (@Param("time") LocalDateTime time);
}
