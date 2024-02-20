package com.springboot.bookstore.service;

import com.springboot.bookstore.payload.BookDto;
import com.springboot.bookstore.payload.BookResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BookService {

    BookDto createBook(BookDto bookDto);

    BookResponse getAllBooks(int pageNo, int pageSize, String sortBy, String sortDir);

    BookDto getBookId(long id);

    BookDto updateBook(BookDto bookDto, long id);


    String deleteBook(long id);


}
