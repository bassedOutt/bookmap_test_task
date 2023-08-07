package com.murmylo.volodymyr.service.impl;

import com.murmylo.volodymyr.service.OutputService;

import java.io.BufferedWriter;
import java.io.IOException;

public class OutputServiceImpl implements OutputService {
    private final BufferedWriter bufferedWriter;

    public OutputServiceImpl(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    @Override
    public void write(String str) {
        try {
            bufferedWriter.write(str + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Can't save to file");
        }
    }
}
