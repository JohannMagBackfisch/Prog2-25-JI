package zoo.management;

import zoo.animal.Animal;
import zoo.animal.Bird;
import zoo.animal.Fish;
import zoo.animal.Mammal;
import zoo.animal.Reptile;
import zoo.enclosure.Enclosure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Zoo {

    private static final Logger LOGGER = Logger.getLogger(Zoo.class.getName());

    private final List<Enclosure<? extends Animal>> enclosures = new ArrayList<>();

    public void addEnclosure(Enclosure<? extends Animal> enclosure) {
        LOGGER.info("addEnclosure aufgerufen mit Gehege '" + enclosure.getName() + "'");

        boolean nameAlreadyExists = enclosures.stream()
                .anyMatch(e -> e.getName().equals(enclosure.getName()));
        if (nameAlreadyExists) {
            LOGGER.severe("Es gibt bereits ein Gehege mit dem Namen '" + enclosure.getName()
                    + "' - Gehege-Namen sollten eindeutig sein!");
        }

        enclosures.add(enclosure);
        LOGGER.fine("Zoo hat jetzt " + enclosures.size() + " Gehege");
    }

    public List<Enclosure<? extends Animal>> getEnclosures() {
        LOGGER.info("getEnclosures aufgerufen");
        List<Enclosure<? extends Animal>> result = new ArrayList<>(enclosures);
        LOGGER.fine("Liste mit " + result.size() + " Gehegen zurückgegeben");
        return result;
    }

    public Enclosure<? extends Animal> findEnclosureByName(String name) {
        LOGGER.info("findEnclosureByName aufgerufen mit name='" + name + "'");

        Optional<Enclosure<? extends Animal>> found = enclosures.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst();

        if (found.isEmpty()) {
            LOGGER.warning("Kein Gehege mit dem Namen '" + name + "' gefunden");
            return null;
        }

        LOGGER.fine("Gehege '" + name + "' gefunden");
        return found.get();
    }

    public List<Animal> getAllAnimals() {
        LOGGER.info("getAllAnimals aufgerufen");
        List<Animal> result = enclosures.stream()
                .flatMap(e -> e.getInhabitants().stream())
                .collect(Collectors.toList());
        LOGGER.fine("Insgesamt " + result.size() + " Tiere im Zoo gefunden");
        return result;
    }

    public List<Mammal> getAllMammals() {
        LOGGER.info("getAllMammals aufgerufen");
        List<Mammal> result = getAllAnimals().stream()
                .filter(a -> a instanceof Mammal)
                .map(a -> (Mammal) a)
                .collect(Collectors.toList());
        LOGGER.fine(result.size() + " Mammals im Zoo gefunden");
        return result;
    }

    public List<Animal> getAnimalsByPredicate(Predicate<Animal> predicate) {
        LOGGER.info("getAnimalsByPredicate aufgerufen");
        List<Animal> result = getAllAnimals().stream()
                .filter(predicate)
                .collect(Collectors.toList());
        LOGGER.fine(result.size() + " Tiere erfüllen das übergebene Prädikat");
        return result;
    }

    public Map<String, Long> countAnimalsByType() {
        LOGGER.info("countAnimalsByType aufgerufen");
        Map<String, Long> result = getAllAnimals().stream()
                .collect(Collectors.groupingBy(
                        a -> a.getClass().getSimpleName(),
                        Collectors.counting()));
        LOGGER.fine("Es gibt " + result.size() + " verschiedene Tier-Typen im Zoo");
        return result;
    }

    public List<Enclosure<? extends Animal>> getOvercrowdedEnclosures(int maxAnimals) {
        LOGGER.info("getOvercrowdedEnclosures aufgerufen mit maxAnimals=" + maxAnimals);

        List<Enclosure<? extends Animal>> result = enclosures.stream()
                .filter(e -> e.size() > maxAnimals)
                .collect(Collectors.toList());
        LOGGER.fine(result.size() + " überfüllte Gehege gefunden (Schwelle: " + maxAnimals + ")");
        return result;
    }

    public String summary() {
        LOGGER.info("summary aufgerufen");

        List<Animal> allAnimals = getAllAnimals();

        String typeBreakdown = allAnimals.stream()
                .collect(Collectors.groupingBy(this::categoryName, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> entry.getValue() + " " + entry.getKey())
                .collect(Collectors.joining(", "));

        String result = "Zoo mit " + enclosures.size() + " Gehegen und "
                + allAnimals.size() + " Tieren: " + typeBreakdown;

        LOGGER.fine("Summary erzeugt: " + result);
        return result;
    }

    private String categoryName(Animal animal) {
        return switch (animal) {
            case Fish f -> "Fish";
            case Bird b -> "Birds";
            case Reptile r -> "Reptiles";
            case Mammal m -> "Mammals";
        };
    }
}
