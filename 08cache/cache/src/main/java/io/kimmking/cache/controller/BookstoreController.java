package io.kimmking.cache.controller;

import io.kimmking.cache.entity.Author;
import io.kimmking.cache.entity.User;
import io.kimmking.cache.service.BookstoreService;
import io.kimmking.cache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class BookstoreController {

    @Autowired
    BookstoreService bookstoreService;

    @RequestMapping("/author/find")
    Author find(Long id) {
        return bookstoreService.find(id);
    }
}