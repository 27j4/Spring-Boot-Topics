package com.anshulp.dockerspringboot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public String createBook(BookRequestDto bookRequestDto) {
        Book book = new Book();
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        bookRepository.save(book);
        return "Book created successfully!";
    }

}
