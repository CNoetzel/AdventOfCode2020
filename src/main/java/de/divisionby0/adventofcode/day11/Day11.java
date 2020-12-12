package de.divisionby0.adventofcode.day11;

import de.divisionby0.adventofcode.day1.Day1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

public class Day11 {

    private static final String EMPTY_SEAT = "L";
    private static final String OCCUPIED_SEAT = "#";
    private static final String FLOOR = ".";


    public static void main(String[] args) throws IOException {
        String path = Objects.requireNonNull(Day1.class.getClassLoader().getResource("day11/input.txt")).getPath();
        String[][] seatMartix = getMatrixFromFile(path);

        // Part 1
        int numberOfOccupiedSeats1 = occupySeats(false, seatMartix);
        System.out.println("The number of occupied seats is: " + numberOfOccupiedSeats1);

        // Part 2
        int numberOfOccupiedSeats2 = occupySeats(true, seatMartix);
        System.out.println("The number of occupied seats is: " + numberOfOccupiedSeats2);
    }

    protected static int occupySeats(boolean advanced, String[][] seatMartix) {
        int numberOfOccupiedSeats = 0;
        boolean stable = false;
        while(!stable) {
            numberOfOccupiedSeats = 0;
            String[][] newMatrix = new String[seatMartix.length][seatMartix[0].length];
            for (int row = 0; row < seatMartix.length; row++) {
                for (int column = 0; column < seatMartix[row].length; column++) {
                    String currentPos = seatMartix[row][column];
                    // handle rules
                    if (currentPos.equals(EMPTY_SEAT)) {
                        if (advanced) {
                            String[] visibleSeats = getVisibleSeats(row, column, seatMartix);
                            newMatrix[row][column] = shouldOccupyAdvanced(visibleSeats) ? OCCUPIED_SEAT : currentPos;
                        } else {
                            newMatrix[row][column] = shouldOccupy(row, column, seatMartix) ? OCCUPIED_SEAT : currentPos;
                        }
                    } else if (currentPos.equals(OCCUPIED_SEAT)) {
                        if (advanced) {
                            String[] visibleSeats = getVisibleSeats(row, column, seatMartix);
                            newMatrix[row][column] = shouldLeaveAdvanced(visibleSeats) ? EMPTY_SEAT : currentPos;
                        } else {
                            newMatrix[row][column] = shouldLeave(row, column, seatMartix) ? EMPTY_SEAT : currentPos;
                        }
                    } else {
                        newMatrix[row][column] = currentPos;
                    }
                    // count position if occupied
                    if (newMatrix[row][column].equals(OCCUPIED_SEAT)) {
                        numberOfOccupiedSeats++;
                    }
                }
            }
            // check if stable
            stable = Arrays.deepEquals(seatMartix, newMatrix);
            seatMartix = newMatrix;
            //printMatrix(newMatrix);
            //System.out.println("-----------------------------------------------------------");
        }
        return numberOfOccupiedSeats;
    }

    private static boolean shouldLeave(int currentRow, int currentColumn, String[][] seatMatrix) {
        int minRow = Math.max(currentRow - 1, 0);
        int maxRow = Math.min(currentRow + 1, seatMatrix.length-1);
        int minColumn = Math.max(currentColumn - 1, 0);
        int maxColumn = Math.min(currentColumn + 1, seatMatrix[currentRow].length-1);

        int occupiedSeats = 0;
        for (int row = minRow; row <= maxRow; row++) {
            for (int column = minColumn; column <= maxColumn; column++) {
                boolean currentPos = row == currentRow && column == currentColumn;
                if (!currentPos) {
                    if (seatMatrix[row][column].equals(OCCUPIED_SEAT)) {
                        occupiedSeats++;
                    }
               }
            }
        }
        return occupiedSeats >= 4;
    }

    private static boolean shouldOccupy(int currentRow, int currentColumn, String[][] seatMatrix) {
        boolean shouldOccupy = true;
        int minRow = Math.max(currentRow - 1, 0);
        int maxRow = Math.min(currentRow + 1, seatMatrix.length - 1);
        int minColumn = Math.max(currentColumn - 1, 0);
        int maxColumn = Math.min(currentColumn + 1, seatMatrix[currentRow].length - 1);

        for (int row = minRow; row <= maxRow; row++) {
            for (int column = minColumn; column <= maxColumn; column++) {
                boolean currentPos = row == currentRow && column == currentColumn;
                if (!currentPos) {
                    if (seatMatrix[row][column].equals(OCCUPIED_SEAT)) {
                        shouldOccupy = false;
                    }
                }
            }
        }

        return shouldOccupy;
    }


    protected static int getDirectionIndicatorIfSeatInSight(int currentRow, int currentColumn, int rowToCheck, int columnToCheck) {
        // 0 1 2
        // 3 x 4
        // 5 6 7
        if(currentRow == rowToCheck && currentColumn == columnToCheck) {    // own seat
            return -1;
        } else if (currentColumn == columnToCheck) {                        // up or down
            return currentRow > rowToCheck ? 1 : 6;
        } else if (currentRow == rowToCheck) {                              // left or right
            return currentColumn > columnToCheck ? 3 : 4;
        } else {                                                            // diagonal
            int rowOffset = currentRow - rowToCheck;
            int columnOffset = currentColumn - columnToCheck;
            if (Math.abs(rowOffset) != Math.abs(columnOffset)) {
                return -1;
            }
            // if both values are the same and positive its down right, otherwise up left
            if (Integer.signum(rowOffset) == Integer.signum(columnOffset)) {
                return Integer.signum(rowOffset) > 0 ? 0 : 7;
            }
            // if values differ check which one is greater
            return Integer.signum(rowOffset) > Integer.signum(columnOffset) ? 2 : 5;
        }
    }

    protected static String[] getVisibleSeats(int currentRow, int currentColumn, String[][] seatMatrix) {
        int startRow = Math.max(currentRow - 1, 0);
        int maxRow = seatMatrix.length-1;
        int startColumn = Math.max(currentColumn - 1, 0);
        int maxColumn = seatMatrix[currentRow].length-1;

        String[] visibleSeats = new String[8];
        // check downwards right
        for (int row = startRow; row <= maxRow; row++) {
            for (int column = startColumn; column <= maxColumn; column++) {
                int directionIndicator = getDirectionIndicatorIfSeatInSight(currentRow, currentColumn, row, column);
                if(directionIndicator >= 0 && visibleSeats[directionIndicator] == null) {
                    if (!seatMatrix[row][column].equals(FLOOR)) {
                        visibleSeats[directionIndicator] = seatMatrix[row][column];
                    }
                }
            }
        }

        // check downwards left
        for (int row = startRow; row <= maxRow; row++) {
            for (int column = startColumn; column >= 0; column--) {
                int directionIndicator = getDirectionIndicatorIfSeatInSight(currentRow, currentColumn, row, column);
                if(directionIndicator >= 0 && visibleSeats[directionIndicator] == null) {
                    if (!seatMatrix[row][column].equals(FLOOR)) {
                        visibleSeats[directionIndicator] = seatMatrix[row][column];
                    }
                }
            }
        }

        // check upwards left
        for (int row = startRow; row >= 0; row--) {
            for (int column = startColumn; column >= 0; column--) {
                int directionIndicator = getDirectionIndicatorIfSeatInSight(currentRow, currentColumn, row, column);
                if(directionIndicator >= 0 && visibleSeats[directionIndicator] == null) {
                    if (!seatMatrix[row][column].equals(FLOOR)) {
                        visibleSeats[directionIndicator] = seatMatrix[row][column];
                    }
                }
            }
        }

        // check upwards right
        for (int row = startRow; row >= 0; row--) {
            for (int column = startColumn; column <= maxColumn; column++) {
                int directionIndicator = getDirectionIndicatorIfSeatInSight(currentRow, currentColumn, row, column);
                if(directionIndicator >= 0 && visibleSeats[directionIndicator] == null) {
                    if (!seatMatrix[row][column].equals(FLOOR)) {
                        visibleSeats[directionIndicator] = seatMatrix[row][column];
                    }
                }
            }
        }
        return visibleSeats;
    }

    private static boolean shouldLeaveAdvanced(String[] visibleSeats) {
        int occupiedSeats = 0;
        for (String seat: Arrays.asList(visibleSeats)) {
            if(seat != null && seat.equals(OCCUPIED_SEAT)) {
                occupiedSeats++;
            }
        }
        return occupiedSeats >= 5;
    }

    private static boolean shouldOccupyAdvanced(String[] visibleSeats) {
        boolean shouldOccupy = true;
        for (String seat: Arrays.asList(visibleSeats)) {
            if(seat != null && seat.equals(OCCUPIED_SEAT)) {
                shouldOccupy = false;
            }
        }
        return shouldOccupy;
    }

    protected static String[][] getMatrixFromFile(String path) throws IOException {
        return Files.readAllLines(new File(path).toPath(), StandardCharsets.UTF_8)
                .stream()
                .map(line -> line.split("(?!^)"))
                .toArray(String[][]::new);
    }

    private static void printMatrix(String[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
