package zoo.animal;

public sealed interface Animal permits Fish, Bird, Reptile, Mammal {
    String name();
}
