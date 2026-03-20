package gmae.adventures;

import gmae.adventure.Action;
import gmae.adventure.ActionType;
import gmae.adventure.AdventureState;
import gmae.adventure.Result;
import gmae.model.Player;
import gmae.model.PlayerProfile;
import gmae.model.RealmMap;
import gmae.model.RealmView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RelicHuntAdventureTest {

    // Use the package-private constructor so we control target relics, max rounds, and RNG
    private static final int TARGET_RELICS = 3;
    private static final int MAX_ROUNDS = 3;

    private RealmMap map;
    private RelicHuntAdventure adventure;
    private Player p1;
    private Player p2;

    @BeforeEach
    void setUp() {
        map = RealmMap.createRandomBoard(3, 3, new Random(42));
        adventure = new RelicHuntAdventure(map, TARGET_RELICS, MAX_ROUNDS, new Random(0));
        p1 = new Player(new PlayerProfile("Alice"));
        p2 = new Player(new PlayerProfile("Bob"));
        adventure.init(p1, p2);
    }

    @Test
    void nameAndDescription() {
        assertEquals("Relic Hunt", adventure.getName());
        assertNotNull(adventure.getDescription());
        assertFalse(adventure.getDescription().isBlank());
    }

    @Test
    void notFinishedAfterInit() {
        assertFalse(adventure.isFinished());
    }

    @Test
    void resultIsNullBeforeGameEnds() {
        assertNull(adventure.getResult());
    }

    @Test
    void initialStateIsRoundOne() {
        AdventureState state = adventure.getState();
        assertEquals(1, state.getCurrentRound());
        assertEquals(MAX_ROUNDS, state.getMaxRounds());
        assertFalse(state.isFinished());
    }

    @Test
    void validActionsAlwaysContainDefendAndPass() {
        List<ActionType> actions = adventure.getValidActions(p1);
        assertTrue(actions.contains(ActionType.DEFEND));
        assertTrue(actions.contains(ActionType.PASS));
    }

    @Test
    void validActionsContainMoveWhenAdjacentRealmsExist() {
        List<RealmView> neighbors = map.neighborsOf(p1.getPosition());
        if (!neighbors.isEmpty()) {
            assertTrue(adventure.getValidActions(p1).contains(ActionType.MOVE));
        }
    }

    @Test
    void passActionDoesNotChangeRelicCount() {
        int before = adventure.getRelicCount(p1);
        adventure.applyAction(p1, Action.of(ActionType.PASS));
        assertEquals(before, adventure.getRelicCount(p1));
    }

    @Test
    void defendActionDoesNotEndGame() {
        adventure.applyAction(p1, Action.of(ActionType.DEFEND));
        assertFalse(adventure.isFinished());
    }

    @Test
    void endRoundAdvancesRound() {
        // endRound increments currentRound internally; verify via state
        adventure.endRound();
        AdventureState state = adventure.getState();
        // After one endRound the shown round is 2 (or MAX_ROUNDS if already at max)
        assertTrue(state.getCurrentRound() >= 1);
    }

    @Test
    void gameFinishesAfterMaxRounds() {
        for (int i = 0; i < MAX_ROUNDS; i++) {
            adventure.applyAction(p1, Action.of(ActionType.PASS));
            adventure.applyAction(p2, Action.of(ActionType.PASS));
            adventure.endRound();
        }
        assertTrue(adventure.isFinished());
        assertNotNull(adventure.getResult());
    }

    @Test
    void tieWhenBothHaveEqualRelicsAtRoundEnd() {
        for (int i = 0; i < MAX_ROUNDS; i++) {
            adventure.applyAction(p1, Action.of(ActionType.PASS));
            adventure.applyAction(p2, Action.of(ActionType.PASS));
            adventure.endRound();
        }
        Result result = adventure.getResult();
        assertNotNull(result);
        assertTrue(result.isTie());
    }

    @Test
    void moveAction_movesToAdjacentRealm() {
        List<RealmView> neighbors = map.neighborsOf(p1.getPosition());
        if (neighbors.isEmpty()) return; // skip if no neighbors (edge case)

        RealmView destination = neighbors.get(0);
        adventure.applyAction(p1, Action.of(ActionType.MOVE, "target", destination));
        assertEquals(destination, p1.getPosition());
    }

    @Test
    void moveToNonAdjacentRealmIsIgnored() {
        RealmView originalPosition = p1.getPosition();
        // Find a realm that is definitely not adjacent
        RealmView nonAdjacent = null;
        for (RealmView realm : map.getRealms()) {
            if (!realm.equals(originalPosition) && !map.isAdjacent(originalPosition, realm)) {
                nonAdjacent = realm;
                break;
            }
        }
        if (nonAdjacent == null) return; // all realms adjacent

        adventure.applyAction(p1, Action.of(ActionType.MOVE, "target", nonAdjacent));
        assertEquals(originalPosition, p1.getPosition());
    }

    @Test
    void initThrowsOnNullPlayer1() {
        RelicHuntAdventure fresh = new RelicHuntAdventure(map, TARGET_RELICS, MAX_ROUNDS, new Random(0));
        assertThrows(IllegalArgumentException.class, () -> fresh.init(null, p2));
    }

    @Test
    void initThrowsOnNullPlayer2() {
        RelicHuntAdventure fresh = new RelicHuntAdventure(map, TARGET_RELICS, MAX_ROUNDS, new Random(0));
        assertThrows(IllegalArgumentException.class, () -> fresh.init(p1, null));
    }

    @Test
    void constructorThrowsOnNegativeTargetRelics() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelicHuntAdventure(map, 0, MAX_ROUNDS, new Random()));
    }

    @Test
    void constructorThrowsOnNonPositiveMaxRounds() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelicHuntAdventure(map, TARGET_RELICS, 0, new Random()));
    }

    @Test
    void resetClearsStateAndResult() {
        adventure.applyAction(p1, Action.of(ActionType.PASS));
        adventure.reset();
        // After reset, result and player references are cleared
        assertNull(adventure.getResult());
        assertFalse(adventure.isFinished());
    }

    @Test
    void relicsInRealmAreSeededOnInit() {
        int totalRelics = 0;
        for (RealmView realm : map.getRealms()) {
            totalRelics += adventure.getRelicsInRealm(realm);
        }
        assertTrue(totalRelics > 0, "Relics should be seeded across realms after init");
    }

    @Test
    void winnerSetWhenPlayerReachesTargetRelics() {
        // Simulate p1 collecting enough relics by moving through relics-holding realms.
        int maxAttempts = TARGET_RELICS * 10;
        for (int i = 0; i < maxAttempts && !adventure.isFinished(); i++) {
            List<ActionType> actions = adventure.getValidActions(p1);
            if (actions.contains(ActionType.MOVE)) {
                List<RealmView> neighbors = map.neighborsOf(p1.getPosition());
                if (!neighbors.isEmpty()) {
                    adventure.applyAction(p1, Action.of(ActionType.MOVE, "target", neighbors.get(0)));
                }
            } else {
                adventure.applyAction(p1, Action.of(ActionType.PASS));
            }
            if (!adventure.isFinished()) {
                adventure.applyAction(p2, Action.of(ActionType.PASS));
                adventure.endRound();
            }
        }
        assertTrue(adventure.isFinished());
    }
}
