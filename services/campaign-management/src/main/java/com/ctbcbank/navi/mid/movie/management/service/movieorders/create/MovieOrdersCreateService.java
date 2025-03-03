package com.ctbcbank.navi.mid.movie.management.service.movieorders.create;

import com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload.MovieOrderCreateRq;

public interface MovieOrdersCreateService {
    MovieOrderCreateRsBo create(MovieOrderCreateRqBo movieOrderCreateRqBo);
}
