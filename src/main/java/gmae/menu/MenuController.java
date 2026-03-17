package gmae.menu;

import gmae.GMAE_APP;
import gmae.profile.ProfileManager;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MenuController {

    private final GMAE_APP app;
    private final ProfileManager profileManager;

    public MenuController(GMAE_APP app, ProfileManager profileManager) {
        this.app = app;
        this.profileManager = profileManager;
    }

    public Parent buildView() {
        // TODO: build the real main menu UI
        return new StackPane(new Label("Main Menu"));
    }
}
