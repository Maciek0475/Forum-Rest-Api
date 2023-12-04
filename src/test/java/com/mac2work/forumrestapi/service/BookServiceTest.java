package com.mac2work.forumrestapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mac2work.forumrestapi.exception.ResourceNotFoundException;
import com.mac2work.forumrestapi.model.Book;
import com.mac2work.forumrestapi.repository.BookRepository;
import com.mac2work.forumrestapi.request.BookRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.BookResponse;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	private BookService bookService;
	
	private Book book;
	private Book book2;
	private BookRequest bookRequest;

	
	@BeforeEach
	void setUp() throws Exception {
		book = Book.builder()
				.id(1)
				.name("The Hobbit")
				.publicationYear(1937)
				.description("The bedtime story for his children famously begun on the blank page of an exam script that tells the tale of Bilbo Baggins and the dwarves in their quest to take back the Lonely Mountain from Smaug the dragon")
				.build();
		book2 = Book.builder()
				.id(2)
				.name("The Fellowship of the Ring")
				.publicationYear(1954)
				.description("One of the world’s most famous books that continues the tale of the ring Bilbo found in The Hobbit and what comes next for it, him, and his nephew Frodo.")
				.build();
		bookRequest = BookRequest.builder()
				.name("The Hobbit: There and Back Again")
				.publicationYear(1937)
				.description("The bedtime story for his children famously begun on the blank page of an exam script that tells the tale of Bilbo Baggins and the dwarves in their quest to take back the Lonely Mountain from Smaug the dragon")
				.build();
	}

	@Test
	final void bookService_getBooks_ReturnMoreThanOneBookResponse() {
		List<Book> books = List.of(book, book2);
		when(bookRepository.findAll()).thenReturn(books);
		
		List<BookResponse> bookResponses = bookService.getBooks();
		
		assertThat(bookResponses).isNotNull();
		assertThat(bookResponses.size()).isGreaterThan(1);
	}

	@Test
	final void bookService_getSpecificBook_ReturnBookResponseNotNull() {
		int id = book.getId();
		when(bookRepository.findById(id)).thenReturn(Optional.of(book));
		
		BookResponse bookResponse = bookService.getSpecificBook(id);
		
		assertThat(bookResponse).isNotNull();
	}
	
	@Test
	final void bookService_getSpecificBook_ThrowResourceNotFoundException() {
		int id = 3;
		Book emptyBook = null;
		when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(emptyBook));
		
		Exception exception = assertThrows(
				ResourceNotFoundException.class,
				() -> { bookService.getSpecificBook(id); });
		
		assertThat(exception).isNotNull();
	}

	@Test
	final void bookService_addBook_ReturnBookResponseNotNull() {
		doReturn(null).when(bookRepository).save(Mockito.any(Book.class));
		
		BookResponse bookResponse = bookService.addBook(bookRequest);
		
		assertThat(bookResponse).isNotNull();
	}

	@Test
	final void bookService_updateBook_ReturnBookResponseEqualToBookRequest() {
		int id = book.getId();				
		doReturn(null).when(bookRepository).save(Mockito.any(Book.class));
		when(bookRepository.findById(id)).thenReturn(Optional.of(book));
		
		BookResponse bookResponse = bookService.updateBook(id, bookRequest);
		
		assertThat(bookResponse).isNotNull();
		assertThat(bookResponse.getName()).isEqualTo(bookRequest.getName());
	}

	@Test
	final void bookService_deleteBook_ReturnApiResponseNotNull() {
		int id = book.getId();
		when(bookRepository.findById(id)).thenReturn(Optional.of(book));
		doNothing().when(bookRepository).delete(Mockito.any(Book.class));
		
		ApiResponse apiResponse = bookService.deleteBook(id);
		
		assertThat(apiResponse).isNotNull();
	}

}
