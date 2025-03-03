package com.ctbcbank.navi.mid.movie.management.entity;

import com.ibm.cbmp.fabric.foundation.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@DynamicUpdate
@Table(name = "TB_MOVIE_ORDERS_BRUCE")
public class MovieOrderEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "USER_ID", nullable = false)
    private BigInteger userId;

    @Column(name = "MOVIE_ID", nullable = false)
    private BigInteger movieId;

    @Column(name = "QUANTITY", nullable = false)
    private BigInteger quantity;

    @Column(name = "TOTAL_PRICE", nullable = false)
    private BigInteger totalPrice;
}
