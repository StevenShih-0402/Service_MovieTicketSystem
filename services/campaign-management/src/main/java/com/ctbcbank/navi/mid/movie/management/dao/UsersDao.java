package com.ctbcbank.navi.mid.movie.management.dao;

import com.ctbcbank.navi.mid.movie.management.dto.QueryUserConditionDto;
import com.ctbcbank.navi.mid.movie.management.dto.UserDto;
import com.ctbcbank.navi.mid.movie.management.entity.UserEntity;
import com.ctbcbank.navi.mid.movie.management.repository.UsersRepository;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UsersDao {

    private final UsersRepository usersRepository;
    private final JdbcTemplate jdbcTemplate;

    public Boolean existsByEmail(String email){
        return usersRepository.existsByEmail(email);
    }

    public Boolean existsById(BigInteger id){
        return usersRepository.existsById(id);
    }

    public Boolean checkStatus(BigInteger id){
        return usersRepository.checkStatus(id).equals(BigInteger.valueOf(1));
    }

    public Boolean existsByEmailExceptId(String email, BigInteger id){
        return usersRepository.existsByEmailExceptId(email, id) == 1;
    }

    @Transactional
    public UserDto saveUsers(UserDto userDto) {

        String sql = """
                INSERT INTO TB_USERS_BRUCE
                (
                  CREATE_DTTM
                , UPDATE_DTTM
                , USER_NAME
                , EMAIL
                )
                VALUES
                (
                  SYSTIMESTAMP
                , SYSTIMESTAMP
                , ?
                , ?
                )
                """;

        jdbcTemplate.update(sql, ps -> {
            ps.setString(1, userDto.getUserName());
            ps.setString(2, userDto.getEmail());
        });

        UserEntity newUser = usersRepository.findByUserName(userDto.getUserName());

        UserDto reUserDto = new UserDto();

        reUserDto.setId(newUser.getId());
        reUserDto.setUserName(newUser.getUserName());
        reUserDto.setEmail(newUser.getEmail());

        return reUserDto;
    }

    @Transactional(readOnly = true)
    public List<UserDto> queryUsers(QueryUserConditionDto usersConditionDto){
        List<UserDto> reDataList = new ArrayList<>();

        List<UserEntity> usersEntities = usersRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(usersConditionDto.getId())) {
                predicates.add(builder.equal(root.get("id"), usersConditionDto.getId()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });

        CollectionUtils.emptyIfNull(usersEntities).forEach(entity -> {
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    @Transactional
    public UserDto updateUsers(UserDto userDto) {

        String sql = """
                UPDATE TB_USERS_BRUCE
                SET
                USER_NAME = ?,
                EMAIL = ?,
                UPDATE_DTTM = SYSTIMESTAMP
                WHERE ID = ?
                """;

        jdbcTemplate.update(sql, ps -> {
            ps.setString(1, userDto.getUserName());
            ps.setObject(2, userDto.getEmail());
            ps.setObject(3, userDto.getId());
        });

        UserDto reUserDto = new UserDto();

        reUserDto.setUserName(userDto.getUserName());
        reUserDto.setEmail(userDto.getEmail());

        return reUserDto;
    }

    @Transactional
    public void deleteUser(QueryUserConditionDto queryUserConditionDto){
        usersRepository.deleteById(queryUserConditionDto.getId());
    }
}