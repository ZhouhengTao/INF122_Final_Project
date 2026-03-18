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
            new RealmTemplate("Ancient Crossing", "A worn crossroads believed to sit above forgotten tunnels.", "zone=ancient-crossing"),
            new RealmTemplate("Ashen Expanse", "A vast grey wasteland where few dare to linger for long.", "zone=ashen-expanse"),
            new RealmTemplate("Gilded Shores", "A prosperous coastal strip where trade goods flow freely.", "zone=gilded-shores"),
            new RealmTemplate("Thornwood Depths", "A tangled forest where paths shift and landmarks vanish.", "zone=thornwood-depths"),
            new RealmTemplate("Duskfall Ridge", "A ridge that catches the last light of day and holds it longest.", "zone=duskfall-ridge"),
            new RealmTemplate("Stormwatch Cliffs", "Jagged sea cliffs battered by constant gales from the north.", "zone=stormwatch-cliffs"),
            new RealmTemplate("Verdant Hollow", "A sheltered bowl of land known for its unusual calm.", "zone=verdant-hollow"),
            new RealmTemplate("Copperstone Pass", "A narrow mountain pass where caravans pay heavy tolls.", "zone=copperstone-pass"),
            new RealmTemplate("Mirewood", "An ancient forest draped in silvery mist and quiet danger.", "zone=mirewood"),
            new RealmTemplate("Sandfall Reach", "A desert flank where sand dunes shift nightly with the wind.", "zone=sandfall-reach"),
            new RealmTemplate("Ironveil Crossing", "A contested bridge realm that changes hands often.", "zone=ironveil-crossing"),
            new RealmTemplate("Blazing Flats", "A sun-scorched plain where heat rises in shimmering waves.", "zone=blazing-flats"),
            new RealmTemplate("Whispering Crags", "A cliff face riddled with caves that carry strange voices.", "zone=whispering-crags"),
            new RealmTemplate("Saltwind Coast", "A breezy shoreline where salt traders and wanderers meet.", "zone=saltwind-coast"),
            new RealmTemplate("Cinderfall", "A region blanketed in volcanic ash from an eruption long past.", "zone=cinderfall"),
            new RealmTemplate("Rustwood", "A dying forest of reddish trees stripped bare by years of drought.", "zone=rustwood"),
            new RealmTemplate("Gloomwater Mere", "A still black lake whose depths are unknown to any living soul.", "zone=gloomwater-mere"),
            new RealmTemplate("Pale Canyon", "A bleached stone canyon carved by a river that no longer flows.", "zone=pale-canyon"),
            new RealmTemplate("Amber Plateau", "A flat highland with a warm golden hue from mineral-rich soil.", "zone=amber-plateau"),
            new RealmTemplate("Redfang Pass", "A mountain gap marked by sharp red stone formations.", "zone=redfang-pass"),
            new RealmTemplate("Tideworn Delta", "A wide river delta reshaped by floods every season.", "zone=tideworn-delta"),
            new RealmTemplate("Brackenmoor", "A wide open moor covered in low scrub and boggy ground.", "zone=brackenmoor"),
            new RealmTemplate("Gravestone Flats", "A flat expanse dotted with eroded stone markers of unknown origin.", "zone=gravestone-flats"),
            new RealmTemplate("Hollowspire", "A tall hollow rock formation used as a watchtower by old factions.", "zone=hollowspire"),
            new RealmTemplate("Dewmist Glen", "A forested glen perpetually covered in morning mist.", "zone=dewmist-glen"),
            new RealmTemplate("Scorch Plains", "A charred expanse left behind by a war fought generations ago.", "zone=scorch-plains"),
            new RealmTemplate("Veilstone", "A region where a thin fog never fully lifts from the ground.", "zone=veilstone"),
            new RealmTemplate("Ironroot Thicket", "Dense undergrowth threaded with exposed ore veins.", "zone=ironroot-thicket"),
            new RealmTemplate("Windbreak Hollow", "A natural depression in the land that shields travelers from storms.", "zone=windbreak-hollow"),
            new RealmTemplate("Cinnabar Reach", "A volcanic region known for its striking red and orange rock faces.", "zone=cinnabar-reach"),
            new RealmTemplate("Greywater Ford", "A shallow river crossing used by traders from three directions.", "zone=greywater-ford"),
            new RealmTemplate("Sable Marsh", "A dark wetland where movement is slow and footing unreliable.", "zone=sable-marsh"),
            new RealmTemplate("Farsight Hill", "An open hilltop with a commanding view of surrounding territories.", "zone=farsight-hill"),
            new RealmTemplate("Thornspine Ridge", "A jagged spine of rock running across an otherwise flat plain.", "zone=thornspine-ridge"),
            new RealmTemplate("Coldwater Shallows", "A wide but shallow river stretch that freezes solid each winter.", "zone=coldwater-shallows"),
            new RealmTemplate("Dustfall Hollow", "A sunken valley where dust from the surrounding cliffs settles thickly.", "zone=dustfall-hollow"),
            new RealmTemplate("Marbled Keep", "The ruins of a fortified hall, its walls still streaked with old color.", "zone=marbled-keep"),
            new RealmTemplate("Stonehaven Reach", "A broad highland once home to a now-scattered settlement.", "zone=stonehaven-reach"),
            new RealmTemplate("Lostfound Crossing", "A crossroads where travelers often find items left by those before them.", "zone=lostfound-crossing"),
            new RealmTemplate("Thorngate Ruins", "Crumbling gateposts mark the entrance to a realm no longer named.", "zone=thorngate-ruins"),
            new RealmTemplate("Crestfall Shore", "A rocky coast where waves have carved strange shapes into the stone.", "zone=crestfall-shore"),
            new RealmTemplate("Murkveil Depths", "A sunken lowland always cloaked in an unsettling haze.", "zone=murkveil-depths"),
            new RealmTemplate("Embervast", "A wide open plain where embers from distant fires sometimes drift.", "zone=embervast"),
            new RealmTemplate("Ashfield Crossing", "A crossroads surrounded by fields of pale grey soil.", "zone=ashfield-crossing"),
            new RealmTemplate("Ironcliff Span", "A narrow natural bridge over a deep gorge, worn smooth by years of use.", "zone=ironcliff-span")
    );

    public static List<RealmTemplate> getFixedPool() {
        return FIXED_POOL;
    }
}
