package com.example.isy2zeeslagv4.model.game.commands.board.battleship;

import com.example.isy2zeeslagv4.model.game.commands.board.Board;
import com.example.isy2zeeslagv4.other.Cell;
import com.example.isy2zeeslagv4.other.Ship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Battleshipboard implements Board {
    private final int size;
    private final HashSet<Integer> listOfAllCoordinatesOnBoard;
    private Cell[][] board;

    public Battleshipboard(int size)
    {
        this.size = size;
        this.board = new Cell[size][size];
        this.listOfAllCoordinatesOnBoard = new HashSet<>();
        initialize();
    }

    @Override
    public void initialize() {
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
        int count = 0;

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                board[x][y] = new Cell(letters[x] + y);
                listOfAllCoordinatesOnBoard.add(count);
                count++;
            }
        }
    }

    @Override
    public Cell[][] getBoard() {
        return board;
    }

    @Override
    public void printBoard() {
        for (int row=0; row<size; row++) {
            for (int col=0; col<size; col++) {
                if (board[row][col].getShipid() != -1) {
                    System.out.printf("[" + board[row][col].getShipid() + "]");
                } else {
                    System.out.printf("[" + board[row][col].getCoordinate() + "]");
                }
            }
            System.out.println();
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    private boolean validateShot(int coordinate)
    {
        return checkIfCellAlreadyShot(coordinate);
    }

    public void placeShip(List<Integer> coordinates, Ship ship)
    {
        int count = 0;
        for (Cell[] row : board) {
            for (Cell cell : row) {
                if (coordinates.contains(count))
                    cell.setShipId(ship.getId());
                count++;
            }
        }
        ship.setCoordinates(coordinates);
    }

    public int convertCoordinateToIndex(String coordinate) {
        if (coordinate == null || coordinate.length() < 2) {
            throw new IllegalArgumentException("Invalid coordinate format");
        }

        // Convert the row character (e.g., 'A') to a row index (0-based)
        int rowIndex = coordinate.charAt(0) - 'A';

        // Convert the column part of the string to an integer
        int columnIndex;
        try {
            columnIndex = Integer.parseInt(coordinate.substring(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid coordinate format");
        }

        // Check for valid row and column
        if (rowIndex < 0 || rowIndex >= 8 || columnIndex < 0 || columnIndex >= 8) {
            throw new IllegalArgumentException("Coordinate out of bounds");
        }

        // Calculate the index for an 8x8 board
        return rowIndex * 8 + columnIndex;
    }

    public boolean shipPlacementPossible(int start, int end)
    {
        List<Integer> coordinates = getAllCoordinatesShipWillCover(start, end);
        if (coordinates == null)
            return false;

        boolean exists = checkIfCoordinateExists(start) && checkIfCoordinateExists(end);
        if (!(checkIfSpotsNotTaken(coordinates) && exists)) {
            return false;
        }
        return true;
    }

    private boolean checkIfSpotsNotTaken(List<Integer> coordinates)
    {
        int count = 0;
        for (Cell[] row : board) {
            for (Cell cell : row) {
                if (coordinates.contains(count) && cell.getShipid() != -1)
                    return false;
                count++;
            }
        }
        return true;
    }

    public boolean checkIfSpotNotTaken(int coordinate)
    {
        int count = 0;
        for (Cell[] row : board) {
            for (Cell cell : row) {
                if (coordinate == count && cell.getShipid() != -1)
                    return false;
                count++;
            }
        }
        return true;
    }

    public boolean setupValidation(int start, int end, Ship ship)
    {
        boolean foo = !isDuplicateStartAndEnd(start, end);
        boolean bar = isValidShipPlacementLength(start, end, ship.getSize());
        boolean tak = shipPlacementPossible(start, end);
        return foo && bar && tak;
    }

    public boolean isValidShipPlacementLength(int start, int end, int shipLength) {
        int startRow = start / size;
        int startCol = start % size;
        int endRow = end / size;
        int endCol = end % size;

        if (startRow == endRow) {
            return Math.abs(Math.max(startCol, endCol) - Math.min(startCol, endCol)) == shipLength - 1;
        } else if (startCol == endCol) {
            return Math.abs(Math.max(startRow, endRow) - Math.min(startRow, endRow)) == shipLength - 1;
        } else {
            System.out.println("Ship must be placed horizontally or vertically.");
            return false;
        }
    }

    public boolean isDuplicateStartAndEnd(int start, int end)
    {
        if (start == end) {
            System.out.println("Start and end coordinates cannot be the same.");
            return true;
        }
        return false;
    }

    public int[] countHitsAndMisses() {
        int[] hitMisData = new int[3];
        // Your existing logic to handle a shot
        for (int row=0; row<size; row++) {
            for (int col=0; col<size; col++) {
                if (board[row][col].isHit()) {
                    if (board[row][col].getShipid() != -1) {
                        hitMisData[0]++;
                    } else {
                        hitMisData[1]++;
                    }
                } else {
                    hitMisData[2]++;
                }
            }
        }
        return hitMisData;
    }

    public void printOnlyHitsBoard()
    {
        StringBuilder builder = new StringBuilder();
        for (int row=0; row<size; row++) {
            for (int col=0; col<size; col++) {
                builder.append(getPrintSymbol(board[row][col]));
            }
            builder.append("\n");
        }
        System.out.print(builder.toString());
    }

    private String getPrintSymbol(Cell cell)
    {
        if (cell.isHit()) {
            return cell.getShipid() != -1 ? "[X]" : "[O]";
        } else {
            return "[ ]";
        }
    }

    public boolean checkIfCellAlreadyShot(int coordinate)
    {
        return getBattleshipCellByCoordinate(coordinate).isHit();
    }

    public boolean checkIfCoordinateExists(int coordinate)
    {
        return listOfAllCoordinatesOnBoard.contains(coordinate);
    }

    public boolean cellContainsShip(int coordinate)
    {
        return getBattleshipCellByCoordinate(coordinate).getShipid() != -1;
    }

    public int shotOnBoard(int coordinate)
    {
        Cell cell = getBattleshipCellByCoordinate(coordinate);
        cell.setHit();

        return cell.getShipid();
    }


    private Cell getBattleshipCellByCoordinate(int coordinate)
    {
        int count = 0;
        for (Cell[] row : board) {
            for (Cell cell : row) {
                if (count == coordinate)
                    return cell;
                count++;
            }
        }
        return null;
    }

    public List<Integer> getAllCoordinatesShipWillCover(int start, int end)
    {
        List<Integer> coordinates = new ArrayList<>();


        int startRow = start / size;
        int startCol = start % size;
        int endRow = end / size;
        int endCol = end % size;

        if (startRow == endRow) {
            for (int col = Math.min(startCol, endCol); col <= Math.max(startCol, endCol); col++) {
                coordinates.add(startRow*size + col);
            }
        } else if (startCol == endCol) {
            for (int row = Math.min(startRow, endRow); row <= Math.max(startRow, endRow); row++) {
                coordinates.add(row*size + startCol);
            }
        } else {
            return null;
        }

        return coordinates;
    }
}
