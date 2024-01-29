package com.example.isy2zeeslagv4.other;

public class ExcelWriteRequest {
    private String playerName;
    private int[] matchData;
    private String filePath;

    public ExcelWriteRequest(String playerName, int[] matchData, String filePath) {
        this.playerName = playerName;
        this.matchData = matchData;
        this.filePath = filePath;
    }

    // Getters
    public String getPlayerName() { return playerName; }
    public int[] getMatchData() { return matchData; }
    public String getFilePath() { return filePath; }
}
