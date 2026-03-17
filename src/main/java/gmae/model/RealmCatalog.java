package gmae.model;

import java.util.List;

public final class RealmCatalog {

    private RealmCatalog() {
    }

    public record RealmTemplate(String name, String description, String coordinates) {
    }

    private static final List<RealmTemplate> FIXED_POOL = List.of(
            new RealmTemplate("Shadow Hollow", "A quiet land filled with old ruins and hidden relic paths.", "zone=shadow-hollow"),
            new RealmTemplate("Crystal Vale", "A bright valley where ancient energy still lingers in the air.", "zone=crystal-vale"),
            new RealmTemplate("Iron Bastion", "A fortified realm known for narrow passes and defensive ground.", "zone=iron-bastion"),
            new RealmTemplate("Moonfen", "A mist-covered marsh where travelers often lose their way.", "zone=moonfen"),
            new RealmTemplate("Sunspire Reach", "A high and wind-beaten region famous for distant visibility.", "zone=sunspire-reach"),
            new RealmTemplate("Frostmere", "A frozen frontier where movement is slow and danger is constant.", "zone=frostmere"),
            new RealmTemplate("Emerald Wilds", "A dense overgrown realm rich in mystery and hidden trails.", "zone=emerald-wilds"),
            new RealmTemplate("Obsidian Gate", "A dark volcanic pass connecting several unstable regions.", "zone=obsidian-gate"),
            new RealmTemplate("Scarlet Harbor", "A relic trade point where rival hunters often cross paths.", "zone=scarlet-harbor"),
            new RealmTemplate("Echo Basin", "A deep stone basin where sound and movement carry strangely.", "zone=echo-basin"),
            new RealmTemplate("Silent Peak", "A steep isolated summit valued for its strong defensive position.", "zone=silent-peak"),
            new RealmTemplate("Ancient Crossing", "A worn crossroads believed to sit above forgotten tunnels.", "zone=ancient-crossing")
    );

    public static List<RealmTemplate> getFixedPool() {
        return FIXED_POOL;
    }
}