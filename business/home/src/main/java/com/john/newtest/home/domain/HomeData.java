package com.john.newtest.home.domain;

import com.john.home_api.HomeDataService;
import com.john.jrouter.annotation.Route;

/**
 * Created by JohnZh on 2020/7/11
 *
 * <p></p>
 */
@Route("/home/homeData")
public class HomeData implements HomeDataService {

    @Override
    public String getText() {
        return "Home Test";
    }
}
