package cn.edu.nju.story.map.repository;

import cn.edu.nju.story.map.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author xuan
 * @create 2019-01-05 15:37
 **/
public interface UserRepository extends CrudRepository<UserEntity, Long>{


    boolean existsByEmail(String email);

}
