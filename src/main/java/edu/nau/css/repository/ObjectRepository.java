package edu.nau.css.repository;

import edu.nau.css.domain.ObjectMetaInfo;
import edu.nau.css.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObjectRepository extends JpaRepository<ObjectMetaInfo, Long> {

    @Query("select o from ObjectMetaInfo o where o.objectName = ?1 and o.user = ?2")
    Optional<ObjectMetaInfo> findByObjectNameAndUser(String key, User user);

    @Query("select o from ObjectMetaInfo o where o.objectName = ?1")
    Optional<ObjectMetaInfo> findByObjectName(String key);

    @Query("select (count(o) > 0) from ObjectMetaInfo o where o.objectName = ?1 and o.user = ?2")
    boolean existsObjectByObjectNameAndUser(String key, User user);

    @Transactional
    @Modifying
    @Query("delete from ObjectMetaInfo o where o.objectName = ?1 and o.user = ?2")
    void deleteByObjectNameAndUser(String key, User user);

    @Query("select o from ObjectMetaInfo o where o.objectName in :objectNames and o.user = :user")
    List<ObjectMetaInfo> findByObjectNameInAndUser(@Param("objectNames") List<String> objectNames, @Param("user") User user);

    @Query("select (count(o) > 0) from ObjectMetaInfo o where o.objectName = ?1 and o.user = ?2 and o.isFolder = ?3")
    boolean existsObjectByObjectNameAndUserAndIsFolder(String key, User user, Boolean isFolder);

}
