package com.dbcase.reposity;

import com.dbcase.entity.AcUserEntity;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Repostirory 该注解不加也可以
 *
 * 原生sql语句
 * jpa的语句 JPQL语句
 */
@Repository
public interface AcUserRepository extends JpaRepository<AcUserEntity,Integer> {

    //native sql
    @Query(value = "SELECT * FROM ac_user WHERE name = :name AND age = :age",nativeQuery = true)
    List<AcUserEntity> query(@Param("name")String name,@Param("age")int age);

    //查询info字段（jsonb类型）中的address字段为传入的值的对象
    @Query(value = "SELECT * FROM ac_user WHERE (info ->>'address')=:address",nativeQuery = true)
    List<AcUserEntity> queryByAddress(@Param("address") String address);

}
