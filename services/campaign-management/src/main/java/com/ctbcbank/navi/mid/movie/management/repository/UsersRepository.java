package com.ctbcbank.navi.mid.movie.management.repository;

import com.ctbcbank.navi.mid.movie.management.entity.UserEntity;
import com.ibm.cbmp.fabric.foundation.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface UsersRepository extends BaseRepository<UserEntity, BigInteger> {
    @Query(
            value = "SELECT STATUS FROM TB_USERS_BRUCE WHERE ID = :id",
            nativeQuery = true
    )
    BigInteger checkStatus(BigInteger id);

    UserEntity findByUserName(String userName);

    @Query(
            value = "SELECT " +
                    "CASE WHEN COUNT(*) > 0 " +
                    "THEN 1 " +
                    "ELSE 0 " +
                    "END " +
                    "FROM TB_USERS_BRUCE " +
                    "WHERE EMAIL = :email AND ID != :id",
            nativeQuery = true
    )
    Integer existsByEmailExceptId(String email, BigInteger id);
    Boolean existsByEmail(String email);

    @Query(
            value = "UPDATE TB_USERS_BRUCE SET STATUS = 1 WHERE ID = :id",
            nativeQuery = true
    )
    @Modifying
    void deleteById(BigInteger id);
}
