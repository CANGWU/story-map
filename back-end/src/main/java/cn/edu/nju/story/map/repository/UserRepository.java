package cn.edu.nju.story.map.repository;

import cn.edu.nju.story.map.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * @author xuan
 * @create 2019-01-05 15:37
 **/
public interface UserRepository extends CrudRepository<UserEntity, Long>{


    /**
     * 邮箱是否已存在
     * @param email
     * @return
     */
    boolean existsByEmail(String email);

    /**
     * 根据邮箱查找用户
     * @param email
     * @return
     */
    UserEntity findByEmail(String email);


    /**
     * 根据邮箱模糊查找
     * @param email
     * @return
     */
    Page<UserEntity> findByEmailIsLike(String email, Pageable pageable);

}
