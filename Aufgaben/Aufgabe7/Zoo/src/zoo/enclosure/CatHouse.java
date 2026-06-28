package zoo.enclosure;

import zoo.animal.Cat;

public class CatHouse<C extends Cat> extends Enclosure<C> {
    public CatHouse(String name) {
        super(name);
    }
}
