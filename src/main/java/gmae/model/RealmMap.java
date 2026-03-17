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

    private final Map<String, RealmView> realmsById = new LinkedHashMap<>();
    private final Map<RealmView, Set<RealmView>> adjacency = new LinkedHashMap<>();

    public void addRealm(RealmView realm) {
        if (realm == null) {
            throw new IllegalArgumentException("Realm must not be null");
        }
        realmsById.put(realm.getId(), realm);
        adjacency.computeIfAbsent(realm, ignored -> new LinkedHashSet<>());
    }

    public RealmView getRealm(String id) {
        return realmsById.get(id);
    }

    public Collection<RealmView> getRealms() {
        return Collections.unmodifiableCollection(realmsById.values());
    }

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

    public void connect(String firstId, String secondId) {
        RealmView first = getRealm(firstId);
        RealmView second = getRealm(secondId);
        if (first == null || second == null) {
            throw new IllegalArgumentException("Both realms must exist before connecting them");
        }
        connect(first, second);
    }

    public void connect(RealmView first, RealmView second) {
        Objects.requireNonNull(first, "First realm must not be null");
        Objects.requireNonNull(second, "Second realm must not be null");
        addRealm(first);
        addRealm(second);
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

    public static RealmMap createGrid(int rows, int cols) {
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
    }
}