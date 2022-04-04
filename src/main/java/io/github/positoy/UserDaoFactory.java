package io.github.positoy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDaoFactory {
    @Bean
    public ConnectionMaker connectionMaker() {
        return new MyConnectionMaker(); // IoC (Inversion of Control) ; 프레임워크! 사용자가 라이브러리를 부른 것이 아니고, 프레임워크에 구성요소를 끼워넣음
    }

    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }
}