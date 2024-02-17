package com.springboot.bookstore.service.Impl;

import com.springboot.bookstore.entity.Book;
import com.springboot.bookstore.exception.ResourceNotFoundException;
import com.springboot.bookstore.payload.BookDto;
import com.springboot.bookstore.payload.BookResponse;
import com.springboot.bookstore.repository.BookRepository;
import com.springboot.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {


    private BookRepository bookRepository;
    private ModelMapper modelMapper;


    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {

        // Map to Entity
        Book book = mapToEntity(bookDto);
        return mapToDto(bookRepository.save(book));
    }


    @Override
    public BookResponse getAllBooks(int pageNo, int pageSize, String sortBy, String sortDir) {


        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo , pageSize , sort);

        Page<Book> books = bookRepository.findAll(pageable);

        List<Book> bookList = books.getContent();

        BookResponse bookResponse = new BookResponse(

                bookList ,
                books.getNumber() ,
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages() ,
                books.isLast()
        );


        return bookResponse;

    }

    @Override
    public BookDto getBookId(long id) {

        Book book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Books", "id", id)
        );

        return mapToDto(book);
    }

    @Override
    public BookDto updateBook(BookDto bookDto, long id) {

        Book book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Books", "id", id)
        );

        // Update Saved Book

        book.setTitle(bookDto.getTitle());
        book.setAuthors(bookDto.getAuthors());
        book.setCategory(bookDto.getCategory());
        book.setQuantity(bookDto.getQuantity());
        book.setPrice(bookDto.getPrice());


        return mapToDto(bookRepository.save(book));

    }


    @Override
    public String deleteBook(long id) {

        Book book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Books", "id", id)
        );

        bookRepository.delete(book);
        return "Book Deleted Successfully";
    }

    private Book mapToEntity(BookDto bookDto) {


        Book book = modelMapper.map(bookDto, Book.class);
        return book;
    }

    private BookDto mapToDto(Book book) {

        BookDto bookDto = modelMapper.map(book, BookDto.class);
        return bookDto;
    }


}
