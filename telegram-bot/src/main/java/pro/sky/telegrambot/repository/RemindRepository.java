package pro.sky.telegrambot.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.repository.entity.RemindEntity;

@Repository
public interface RemindRepository extends CrudRepository<RemindEntity, Long> {

}
