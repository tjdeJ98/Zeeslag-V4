package com.example.isy2zeeslagv4.other;

import com.example.isy2zeeslagv4.controller.MainMenuController;
import com.example.isy2zeeslagv4.model.game.games.BattleshipGame;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelExporter {
    private static final Object lock = new Object();
    public static void addExportRequest(String playerName, int[] matchData, String filePath) {
        BattleshipGame.writeQueue.add(new ExcelWriteRequest(playerName, matchData, filePath));
    }
    public static void exportPlayerData(String playerName, int[] matchData, String filePath) {
        synchronized (lock) {
            System.out.println(BattleshipGame.writeQueue.size());
            Workbook workbook;
            Sheet sheet;

            try (FileInputStream fileIn = new FileInputStream(filePath)) {
                workbook = new XSSFWorkbook(fileIn);
                sheet = workbook.getSheetAt(0);
            } catch (FileNotFoundException e) {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Player Data");
                createHeaderRow(sheet);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            int lastRowNum = sheet.getLastRowNum();
            int gameId = (lastRowNum / 2) + 1;
            int winLoss = (lastRowNum % 2 == 0) ? 1 : 0;

            Row dataRow = sheet.createRow(lastRowNum + 1);
            dataRow.createCell(0).setCellValue(playerName);
            dataRow.createCell(1).setCellValue(matchData[0]);
            dataRow.createCell(2).setCellValue(matchData[1]);
            dataRow.createCell(3).setCellValue(matchData[2]);
            dataRow.createCell(4).setCellValue(gameId);
            dataRow.createCell(5).setCellValue(winLoss);

            // Resize columns
            for (int i = 0; i < 6; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

        private static void createHeaderRow (Sheet sheet) {
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Player Name");
            headerRow.createCell(1).setCellValue("Hits");
            headerRow.createCell(2).setCellValue("Misses");
            headerRow.createCell(3).setCellValue("Unshot Spaces");
            headerRow.createCell(4).setCellValue("Game ID");
            headerRow.createCell(5).setCellValue("Win/Loss");
        }
}