package gmae;

import gmae.adventure.AdventureRegistry;
import gmae.adventure.MiniAdventure;
import gmae.adventures.CaravanTradeAdventure;
import gmae.adventures.RelicHuntAdventure;
import gmae.menu.GameView;
import gmae.menu.MenuController;
import gmae.model.Player;
import gmae.profile.ProfileManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.function.Supplier;

public class GMAE_APP extends Application {

    private Stage primaryStage;
    private ProfileManager profileManager;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        initialize();

        stage.setTitle("GMAE - GuildQuest Mini-Adventure Environment");
        stage.setResizable(false);
        showMainMenu();
        stage.show();
    }

    public void initialize() {
        profileManager = new ProfileManager();
        profileManager.loadProfiles();

        AdventureRegistry.register(new CaravanTradeAdventure());
        AdventureRegistry.register(new RelicHuntAdventure());
    }

    public void showMainMenu() {
        MenuController ctrl = new MenuController(this, profileManager);
        primaryStage.setScene(new Scene(ctrl.buildView(), 1280, 800));
    }

    public void showGame(Player p1, Player p2, MiniAdventure adventure) {
        Supplier<MiniAdventure> factory;
        if (adventure instanceof CaravanTradeAdventure) {
            factory = CaravanTradeAdventure::new;
        } else if (adventure instanceof RelicHuntAdventure) {
            factory = RelicHuntAdventure::new;
        } else {
            factory = () -> adventure;
        }

        GameView gameView = new GameView(p1, p2, factory, this::showMainMenu);
        primaryStage.setScene(new Scene(gameView.buildView(), 1280, 800));
    }

    @Override
    public void stop() {
        if (profileManager != null) {
            profileManager.saveProfiles();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}