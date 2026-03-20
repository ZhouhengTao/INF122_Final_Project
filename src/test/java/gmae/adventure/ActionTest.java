package gmae.adventure;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class ActionTest {

    @Test
    void ofTypeCreatesActionWithCorrectType() {
        Action action = Action.of(ActionType.PASS);
        assertEquals(ActionType.PASS, action.getType());
    }

    @Test
    void ofTypeReturnsNullParamForMissingKey() {
        Action action = Action.of(ActionType.PASS);
        assertNull(action.getParam("anything"));
    }

    @Test
    void ofTypeKeyValueStoresParam() {
        Action action = Action.of(ActionType.MOVE, "target", "Realm1");
        assertEquals("Realm1", action.getParam("target"));
    }

    @Test
    void ofTypeKeyValueReturnsNullForOtherKeys() {
        Action action = Action.of(ActionType.MOVE, "target", "Realm1");
        assertNull(action.getParam("other"));
    }

    @Test
    void ofMapStoresAllParams() {
        Map<String, Object> params = Map.of("key1", "val1", "key2", 42);
        Action action = Action.of(ActionType.BUY, params);
        assertEquals("val1", action.getParam("key1"));
        assertEquals(42, action.getParam("key2"));
    }

    @Test
    void constructorWithMapCreatesCorrectAction() {
        Action action = new Action(ActionType.SELL, Map.of("itemName", "Spice", "quantity", 2));
        assertEquals(ActionType.SELL, action.getType());
        assertEquals("Spice", action.getParam("itemName"));
        assertEquals(2, action.getParam("quantity"));
    }

    @Test
    void toStringContainsActionType() {
        Action action = Action.of(ActionType.DEFEND);
        assertTrue(action.toString().contains("DEFEND"));
    }

    @Test
    void actionTypeEnumContainsExpectedValues() {
        assertNotNull(ActionType.MOVE);
        assertNotNull(ActionType.DEFEND);
        assertNotNull(ActionType.USE_ITEM);
        assertNotNull(ActionType.TRADE);
        assertNotNull(ActionType.BUY);
        assertNotNull(ActionType.SELL);
        assertNotNull(ActionType.PASS);
    }
}
