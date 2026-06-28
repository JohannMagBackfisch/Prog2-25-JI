package zoo.animal;

public sealed interface Mammal extends Animal permits Elephant, Giraffe, Cat {
}
