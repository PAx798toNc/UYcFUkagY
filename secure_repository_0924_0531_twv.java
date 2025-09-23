// 代码生成时间: 2025-09-24 05:31:52
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SecureRepository extends JpaRepository<SecureEntity, Long> {
    
    // 使用命名参数防止SQL注入
    @Query("SELECT e FROM SecureEntity e WHERE e.field = :fieldValue")
    List<SecureEntity> findByFieldWithValue(@Param("fieldValue") String fieldValue);

    // 演示一个更新操作的例子，使用@Modifying和@Transactional注解进行数据库更新
    @Transactional
    @Modifying
    @Query("UPDATE SecureEntity e SET e.field = :fieldValue WHERE e.id = :id")
    int updateFieldById(@Param("fieldValue\) String fieldValue, @Param("id") Long id);
    
    // 可以添加更多的方法，遵循上述模式
}