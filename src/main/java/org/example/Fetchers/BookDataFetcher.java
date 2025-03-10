package org.example.Fetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.example.Models.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookDataFetcher implements DataFetcher<List<Book>>
{
    private final List<Book> books;

    public BookDataFetcher(List<Book> books)
    {
        this.books = books;
    }

    @Override
    public List<Book> get(DataFetchingEnvironment environment)
    {
        String authorName = environment.getArgument("authorName");

        if (authorName.isEmpty())
            return books;

        return books.stream()
                .filter(book -> book.getAuthor().getName().toLowerCase().contains(authorName.toLowerCase()))
                .toList();
    }
}
