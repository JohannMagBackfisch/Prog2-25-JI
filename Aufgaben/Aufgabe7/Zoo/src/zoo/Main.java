package zoo;

import zoo.animal.*;
import zoo.enclosure.*;
import zoo.management.Zoo;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Logger zooLogger = Logger.getLogger(Zoo.class.getName());
        zooLogger.setLevel(Level.INFO);

        zooLogger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setEncoding("UTF-8");
        zooLogger.addHandler(handler);

        Zoo zoo = new Zoo();

        Aquarium aquarium = new Aquarium("Aquarium 1");
        aquarium.add(new Trout("Forelle Fritz"));
        aquarium.add(new Salmon("Lachs Lotte"));

        Terrarium terrarium = new Terrarium("Terrarium 1");
        terrarium.add(new Snake("Schlange Susi"));
        terrarium.add(new Lizard("Echse Egon"));

        MammalHouse mammalHouse = new MammalHouse("Säugetierhaus");
        mammalHouse.add(new Elephant("Elefant Ernie"));
        mammalHouse.add(new Giraffe("Giraffe Gerda"));

        CatHouse<Tiger> tigerHouse = new CatHouse<>("Tigergehege");
        tigerHouse.add(new Tiger("Tiger Tom"));
        tigerHouse.add(new Tiger("Tiger Tina"));

        Enclosure<Bird> aviary = new Enclosure<>("Vogelhaus");
        aviary.add(new Eagle("Adler Anton"));
        aviary.add(new Parrot("Papagei Paul"));

        zoo.addEnclosure(aquarium);
        zoo.addEnclosure(terrarium);
        zoo.addEnclosure(mammalHouse);
        zoo.addEnclosure(tigerHouse);
        zoo.addEnclosure(aviary);

        System.out.println();
        System.out.println(zoo.summary());
        System.out.println("Alle Mammals: " + zoo.getAllMammals());
        System.out.println("Überfüllte Gehege (>1 Tier): " + zoo.getOvercrowdedEnclosures(1));
        System.out.println("Anzahl pro Typ: " + zoo.countAnimalsByType());

        System.out.println();
        System.out.println("--- Jetzt mit Log-Level FINE ---");
        System.out.println();
        zooLogger.setLevel(Level.FINE);
        handler.setLevel(Level.FINE);

        zoo.findEnclosureByName("Aquarium 1");
        zoo.findEnclosureByName("Gehege das es nicht gibt");

        zoo.addEnclosure(new Aquarium("Aquarium 1"));
    }
}
