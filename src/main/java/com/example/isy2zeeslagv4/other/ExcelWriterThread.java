package com.example.isy2zeeslagv4.other;

import java.util.concurrent.BlockingQueue;

public class ExcelWriterThread implements Runnable {
    private final BlockingQueue<ExcelWriteRequest> queue;

    public ExcelWriterThread(BlockingQueue<ExcelWriteRequest> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ExcelWriteRequest request = queue.take();
                ExcelExporter.exportPlayerData(request.getPlayerName(), request.getMatchData(), request.getFilePath());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
