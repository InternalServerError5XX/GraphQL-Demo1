package org.example.Fetchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.example.Models.Author;
import org.example.Models.Book;
import org.example.Models.Country;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddBookDataFetcher implements DataFetcher<Book> {
    private final List<Book> books;
    private final File file;
    private final ObjectMapper objectMapper;

    public AddBookDataFetcher(List<Book> books, File file) {
        this.books = books;
        this.file = file;
        this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public Book get(DataFetchingEnvironment environment) throws IOException
    {
        Map<String, Object> input = environment.getArgument("input");

        int newBookId = books.stream().mapToInt(Book::getId).max().orElse(0) + 1;
        String title = (String) input.get("title");
        Map<String, Object> authorInput = (Map<String, Object>) input.get("author");

        int authorId = (int) authorInput.get("id");
        String authorName = (String) authorInput.get("name");
        Map<String, Object> countryInput = (Map<String, Object>) authorInput.get("country");

        int countryId = (int) countryInput.get("id");
        String countryName = (String) countryInput.get("name");
        int countryCode = (int) countryInput.get("countryCode");

        Country country = new Country(countryId, countryName, countryCode);
        Author author = new Author(authorId, authorName, country);
        Book newBook = new Book(newBookId, title, author);

        books.add(newBook);
        saveBooksToFile();

        return newBook;
    }

    private void saveBooksToFile() throws IOException
    {
        objectMapper.writeValue(file, books);
    }
}
