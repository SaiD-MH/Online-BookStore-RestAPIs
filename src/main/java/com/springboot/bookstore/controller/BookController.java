package com.springboot.bookstore.controller;

import com.springboot.bookstore.payload.BookDto;
import com.springboot.bookstore.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {



    private BookService bookService ;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto){

        return new ResponseEntity<>(bookService.createBook(bookDto) , HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks(){

        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") long id){

        return ResponseEntity.ok(bookService.getBookId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto , @PathVariable("id") long id){

        return ResponseEntity.ok( bookService.updateBook(bookDto , id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") long id ){
        
        return ResponseEntity.ok(bookService.deleteBook(id));
    }
}
