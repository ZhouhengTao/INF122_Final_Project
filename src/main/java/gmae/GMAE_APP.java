package gmae;

import gmae.menu.MenuController;
import gmae.profile.ProfileManager;

public class GMAE_APP {

    private MenuController menuController;
    private ProfileManager profileManager;

    public static void main(String[] args) {
        new GMAE_APP().start();
    }

    public void initialize() {
        profileManager = new ProfileManager();
        profileManager.loadProfiles();

        // TODO: CaravanTradeAdventure
        // TODO: RelicHuntAdventure

        menuController = new MenuController();
    }

    public void start() {
        initialize();
        // TODO: menuController
        exit();
    }

    public void showMenu() {
        // TODO: displayMenu
    }

    public void exit() {
        profileManager.saveProfiles();
        System.exit(0);
    }
}
