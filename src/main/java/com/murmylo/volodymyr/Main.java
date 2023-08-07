package com.murmylo.volodymyr;

import com.murmylo.volodymyr.domain.OrderBook;
import com.murmylo.volodymyr.service.CommandExecutorService;
import com.murmylo.volodymyr.service.OutputService;
import com.murmylo.volodymyr.service.impl.CommandExecutorServiceImpl;
import com.murmylo.volodymyr.service.impl.OutputServiceImpl;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static final String INPUT_FILE_PATH = "input.txt";
    private static final String OUTPUT_FILE_PATH = "output.txt";

    @SneakyThrows
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(INPUT_FILE_PATH));
             BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(OUTPUT_FILE_PATH))) {
            OrderBook orderBook = new OrderBook();
            OutputService outputService = new OutputServiceImpl(bufferedWriter);
            CommandExecutorService commandExecutorService = new CommandExecutorServiceImpl(orderBook, outputService);
            String line = reader.readLine();
            while (line != null) {
                commandExecutorService.execute(line);
                line = reader.readLine();
            }
        }
    }

}