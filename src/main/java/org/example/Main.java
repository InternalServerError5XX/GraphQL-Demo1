package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import graphql.ExecutionInput;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        var graphQLProvider = new GraphQLProvider();
        var scanner = new Scanner(System.in);

        while (true)
        {
            // { getBooksByAuthor(authorName: "1") { id title author { id name country { id, name, countryCode } } } }
            // { getAuthorsByCountry(countryName: "1") { id name country { id, name, countryCode } } }
            // mutation { addBook(input: { title: "New Book", author: { id: 4, name: "New Author", country: { id: 3, name: "New Country", countryCode: 123 } } }) { id title author { id name country { id name countryCode } } } }

            System.out.println("Enter GraphQL query:");
            var query = scanner.nextLine();
            var result = graphQLProvider.getGraphQL().execute(ExecutionInput.newExecutionInput().query(query).build());

            var objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            var jsonResult = objectMapper.writeValueAsString(result.getData());
            System.out.println(jsonResult);
        }
    }
}
