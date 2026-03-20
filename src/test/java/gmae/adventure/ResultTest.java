package gmae.adventure;

import gmae.model.Player;
import gmae.model.PlayerProfile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    private Player makePlayer(String name) {
        return new Player(new PlayerProfile(name));
    }

    @Test
    void winCreatesNonTieResult() {
        Player winner = makePlayer("Alice");
        Result result = Result.win(winner, "Alice won!");
        assertFalse(result.isTie());
    }

    @Test
    void winSetsWinner() {
        Player winner = makePlayer("Bob");
        Result result = Result.win(winner, "summary");
        assertSame(winner, result.getWinner());
    }

    @Test
    void winSetsSummary() {
        Player winner = makePlayer("Carol");
        Result result = Result.win(winner, "Carol won by points");
        assertEquals("Carol won by points", result.getSummary());
    }

    @Test
    void winThrowsOnNullWinner() {
        assertThrows(IllegalArgumentException.class, () -> Result.win(null, "summary"));
    }

    @Test
    void tieCreatesTieResult() {
        Result result = Result.tie("Equal score");
        assertTrue(result.isTie());
    }

    @Test
    void tieHasNullWinner() {
        Result result = Result.tie("Tied");
        assertNull(result.getWinner());
    }

    @Test
    void tieSetsSummary() {
        Result result = Result.tie("Both tied at 5");
        assertEquals("Both tied at 5", result.getSummary());
    }

    @Test
    void toStringContainsWinnerName() {
        Player winner = makePlayer("Dave");
        Result result = Result.win(winner, "summary");
        assertTrue(result.toString().contains("Dave"));
    }

    @Test
    void toStringContainsTieKeyword() {
        Result result = Result.tie("summary");
        assertTrue(result.toString().toLowerCase().contains("tie"));
    }
}
