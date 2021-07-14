package io.kimmking.cache.repository;

import io.kimmking.cache.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//hibernate cache test


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
