package org.example.Fetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.example.Models.Author;
import org.example.Models.Book;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AuthorsDataFetcher implements DataFetcher<List<Author>>
{
    private final List<Book> books;

    public AuthorsDataFetcher(List<Book> books)
    {
        this.books = books;
    }

    @Override
    public List<Author> get(DataFetchingEnvironment environment)
    {
        String countryName = environment.getArgument("countryName");

        if (countryName.isEmpty())
            return List.of();

        return books.stream()
                .map(Book::getAuthor)
                .filter(author -> author.getCountry().getName().toLowerCase().contains(countryName.toLowerCase()))
                .distinct()
                .toList();
    }
}
