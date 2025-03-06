package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQL;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeRuntimeWiring;
import org.example.Fetchers.AddBookDataFetcher;
import org.example.Fetchers.AuthorsDataFetcher;
import org.example.Fetchers.BookDataFetcher;
import org.example.Models.Book;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GraphQLProvider
{
    private GraphQL graphQL;

    public GraphQLProvider() throws Exception
    {
        var objectMapper = new ObjectMapper();
        var json = new File("src/main/resources/books.json");
        var books = new ArrayList<>(List.of(objectMapper.readValue(json, Book[].class)));

        var schemaFile = new File("src/main/resources/schema.graphqls");
        var schema = new String(Files.readAllBytes(Paths.get(schemaFile.toURI())));

        var typeRegistry = new SchemaParser().parse(schema);
        var wiring = RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("getBooksByAuthor", new BookDataFetcher(books))
                        .dataFetcher("getAuthorsByCountry", new AuthorsDataFetcher(books)))
                .type(TypeRuntimeWiring.newTypeWiring("Mutation")
                        .dataFetcher("addBook", new AddBookDataFetcher(books, json)))
                .build();

        var graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}
