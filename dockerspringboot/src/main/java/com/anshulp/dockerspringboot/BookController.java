package com.anshulp.dockerspringboot;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/books")
public class BookController {

    @PostMapping
    public String createBook(@RequestBody BookRequestDto bookRequestDto) {
        return "Book created successfully!";
    }

}
