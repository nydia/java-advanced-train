package io.kimmking.cache.service;

import io.kimmking.cache.entity.Author;
import io.kimmking.cache.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

//hibernate cache test


@Service
public class BookstoreService {

    private final AuthorRepository authorRepository;

    public BookstoreService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void insertAuthor() {
        Author author = new Author();
        author.setId(UUID.randomUUID());
        author.setName("Joana Nimar");
        author.setGenre("History");
        author.setAge(34);

        authorRepository.save(author);
    }

    public Author find(){
        return authorRepository.findById(1L).get();
    }

}
