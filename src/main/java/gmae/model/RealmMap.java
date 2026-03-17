package gmae.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class RealmMap {

    // Addition: map contain a limited size
    private static final int MIN_ROWS = 3;
    private static final int MAX_ROWS = 6;
    private static final int MIN_COLS = 3;
    private static final int MAX_COLS = 6;


    // Addition: Contain the actual 2D board
    private final RealmView[][] board;
    private final int rows;
    private final int cols;
    private final Map<RealmView, Cell> positions = new LinkedHashMap<>();



    private final Map<String, RealmView> realmsById = new LinkedHashMap<>();
    private final Map<RealmView, Set<RealmView>> adjacency = new LinkedHashMap<>();

    // Addition: private constructor to enforce use of CreateRandomBoard and CreateGrid factory methods
    private RealmMap(int rows, int cols) {
        if (rows < MIN_ROWS || rows > MAX_ROWS) {
            throw new IllegalArgumentException("Rows must be between " + MIN_ROWS + " and " + MAX_ROWS);
        }
        if (cols < MIN_COLS || cols > MAX_COLS) {
            throw new IllegalArgumentException("Columns must be between " + MIN_COLS + " and " + MAX_COLS);
        }
        this.rows = rows;
        this.cols = cols;
        this.board = new RealmView[rows][cols];
    }

    // Addition: place the realm at the specified position in the board, and add it to the map's internal structures. This is used by the factory methods to build the map. It will throw an exception if the position is invalid, already occupied, or if a realm with the same id already exists in the map.
    public void placeRealm(RealmView realm, int row, int col) {
        if (realm == null) {
            throw new IllegalArgumentException("Realm must not be null");
        }
        if (!isInsideBoard(row, col)) {
            throw new IllegalArgumentException("Cell is outside the board");
        }
        if (board[row][col] != null) {
            throw new IllegalArgumentException("Cell is already occupied");
        }
        if (realmsById.containsKey(realm.getId())) {
            throw new IllegalArgumentException("Realm id already exists in the map: " + realm.getId());
        }

        board[row][col] = realm;
        realmsById.put(realm.getId(), realm);
        adjacency.computeIfAbsent(realm, ignored -> new LinkedHashSet<>());
        positions.put(realm, new Cell(row, col));
    }

    /* Removed addRealm method since we need to know the position of the realm when we add it, so placeRealm should be used instead. We can still add realms by calling placeRealm with the desired position.
    public void addRealm(RealmView realm) {
        if (realm == null) {
            throw new IllegalArgumentException("Realm must not be null");
        }
        realmsById.put(realm.getId(), realm);
        adjacency.computeIfAbsent(realm, ignored -> new LinkedHashSet<>());
    }
    */

    public List<RealmView> neighborsOf(RealmView realm) {
        if (realm == null) {
            return List.of();
        }
        Set<RealmView> neighbors = adjacency.get(realm);
        if (neighbors == null) {
            return List.of();
        }
        return List.copyOf(neighbors);
    }

    public boolean isAdjacent(RealmView first, RealmView second) {
        if (first == null || second == null) {
            return false;
        }
        return adjacency.getOrDefault(first, Set.of()).contains(second);
    }

    /* Removed connect by id method since it is more efficient to connect by reference and we already have the realms in memory when we build the map, so we can just call connect with the realm references. If we need to connect by id, we can still get the realm references using getRealm(id) and then call connect with those references.
    public void connect(String firstId, String secondId) {
        RealmView first = getRealm(firstId);
        RealmView second = getRealm(secondId);
        if (first == null || second == null) {
            throw new IllegalArgumentException("Both realms must exist before connecting them");
        }
        connect(first, second);
    }
    */

    //update: connect only by reference instead of id
    public void connect(RealmView first, RealmView second) {
        Objects.requireNonNull(first, "First realm must not be null");
        Objects.requireNonNull(second, "Second realm must not be null");
        if (first.equals(second)) {
            return;
        }
        if (!positions.containsKey(first) || !positions.containsKey(second)) {
            throw new IllegalArgumentException("Both realms must already be placed on the board");
        }
        adjacency.get(first).add(second);
        adjacency.get(second).add(first);
    }

    public RealmView randomRealm(Random rng) {
        if (rng == null) {
            throw new IllegalArgumentException("Random must not be null");
        }
        if (realmsById.isEmpty()) {
            return null;
        }
        List<RealmView> realms = new ArrayList<>(realmsById.values());
        return realms.get(rng.nextInt(realms.size()));
    }

    // Addition: create a random board with the specified dimensions, using the fixed realm pool and the provided random generator. 
    // The method will randomly select a number of realms from the fixed pool (between 60% and 85% of the total cells), 
    // and then place them on the board in a way that ensures they are all connected. The remaining cells will be left empty (null). 
    public static RealmMap createRandomBoard(int rows, int cols, Random rng) {
        if (rng == null) {
            throw new IllegalArgumentException("Random must not be null");
        }

        RealmMap map = new RealmMap(rows, cols);

        int totalCells = rows * cols;
        int minRealmCount = Math.max(2, (int) Math.ceil(totalCells * 0.60));
        int maxRealmCount = Math.max(minRealmCount, (int) Math.floor(totalCells * 0.85));

        List<RealmCatalog.RealmTemplate> catalog = new ArrayList<>(RealmCatalog.getFixedPool());
        if (minRealmCount > catalog.size()) {
            throw new IllegalArgumentException("Fixed realm pool is too small for this board size");
        }

        int realmCount = minRealmCount + rng.nextInt(maxRealmCount - minRealmCount + 1);

        Collections.shuffle(catalog, rng);
        List<RealmCatalog.RealmTemplate> selectedTemplates = new ArrayList<>(catalog.subList(0, realmCount));

        List<Cell> placedCells = new ArrayList<>();

        // Start by placing the first realm in a random cell
        // This is to ensure all the realm are connected since we will only place new realms in the neighbors of already placed realms.
        Cell start = new Cell(rng.nextInt(rows), rng.nextInt(cols));
        placedCells.add(start);

        while (placedCells.size() < realmCount) {
            Cell base = placedCells.get(rng.nextInt(placedCells.size()));
            List<Cell> frontier = validEmptyNeighbors(base, rows, cols, placedCells);
            if (frontier.isEmpty()) {
                continue;
            }
            Cell next = frontier.get(rng.nextInt(frontier.size()));
            if (!placedCells.contains(next)) {
                placedCells.add(next);
            }
        }

        Collections.shuffle(selectedTemplates, rng);

        for (int i = 0; i < placedCells.size(); i++) {
            Cell cell = placedCells.get(i);
            RealmCatalog.RealmTemplate template = selectedTemplates.get(i);

            Realm rawRealm = new Realm(
                    template.name(),
                    template.description(),
                    "row=" + cell.row + ",col=" + cell.col + "," + template.coordinates(),
                    null
            );
            RealmView realm = new RealmAdapter(rawRealm);
            map.placeRealm(realm, cell.row, cell.col);
        }
        map.connectOrthogonalNeighbors();
        return map;
    }

    // Helper method to returns orthogonal neighboring cells that are inside the board and not already selected in placedCells.
    private static List<Cell> validEmptyNeighbors(Cell cell, int rows, int cols, List<Cell> placedCells) {
        List<Cell> result = new ArrayList<>();
        if (cell.row > 0) {
            addIfMissing(result, placedCells, new Cell(cell.row - 1, cell.col));
        }
        if (cell.row + 1 < rows) {
            addIfMissing(result, placedCells, new Cell(cell.row + 1, cell.col));
        }
        if (cell.col > 0) {
            addIfMissing(result, placedCells, new Cell(cell.row, cell.col - 1));
        }
        if (cell.col + 1 < cols) {
            addIfMissing(result, placedCells, new Cell(cell.row, cell.col + 1));
        }

        return result;
    }

    // Helper method to add a candidate cell to the result list if it is not already in the placedCells list. 
    private static void addIfMissing(List<Cell> result, List<Cell> placedCells, Cell candidate) {
        if (!placedCells.contains(candidate)) {
            result.add(candidate);
        }
    }

    // Helper method that connect all orthogonal neighbors on the board.
    private void connectOrthogonalNeighbors() {
        for (Map.Entry<RealmView, Cell> entry : positions.entrySet()) {
            RealmView realm = entry.getKey();
            Cell cell = entry.getValue();

            for (Cell neighborCell : orthogonalNeighbors(cell.row, cell.col)) {
                RealmView neighbor = board[neighborCell.row][neighborCell.col];
                if (neighbor != null) {
                    connect(realm, neighbor);
                }
            }
        }
    }

    // Helper method to get the orthogonal neighbors of a cell (up, down, left, right) that are within the bounds of the board. 
    private List<Cell> orthogonalNeighbors(int row, int col) {
        List<Cell> neighbors = new ArrayList<>();
        if (row > 0) {
            neighbors.add(new Cell(row - 1, col));
        }
        if (row + 1 < rows) {
            neighbors.add(new Cell(row + 1, col));
        }
        if (col > 0) {
            neighbors.add(new Cell(row, col - 1));
        }
        if (col + 1 < cols) {
            neighbors.add(new Cell(row, col + 1));
        }
        return neighbors;
    }

    // Update: 
    public static RealmMap createGrid(int rows, int cols) {
        return createRandomBoard(rows, cols, new Random());
    /* Previous implementation that created a grid by first creating the realms and then connecting them.
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Grid dimensions must be positive");
        }

        RealmMap map = new RealmMap();
        RealmView[][] grid = new RealmView[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Realm rawRealm = new Realm(
                        "Realm (" + row + "," + col + ")",
                        "Grid cell at row " + row + ", column " + col,
                        "row=" + row + ",col=" + col,
                        null
                );
                RealmView realm = new RealmAdapter(rawRealm);
                grid[row][col] = realm;
                map.addRealm(realm);
            }
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row + 1 < rows) {
                    map.connect(grid[row][col], grid[row + 1][col]);
                }
                if (col + 1 < cols) {
                    map.connect(grid[row][col], grid[row][col + 1]);
                }
            }
        }

        return map;
    */
    }


    public boolean isEmptyCell(int row, int col) {
        return getRealmAt(row, col) == null;
    }

    public boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private static final class Cell {
        private final int row;
        private final int col;

        private Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Cell other)) return false;
            return row == other.row && col == other.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    // ========================
    // Getters and Setters
    // ========================

    public RealmView getRealm(String id) {
        return realmsById.get(id);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public RealmView getRealmAt(int row, int col) {
        if (!isInsideBoard(row, col)) {
            throw new IllegalArgumentException("Cell is outside the board");
        }
        return board[row][col];
    }

    public int getRowOf(RealmView realm) {
        Cell cell = positions.get(realm);
        return cell != null ? cell.row : -1;
    }

    public int getColOf(RealmView realm) {
        Cell cell = positions.get(realm);
        return cell != null ? cell.col : -1;
    }

    public Collection<RealmView> getRealms() {
        return Collections.unmodifiableCollection(realmsById.values());
    }
}