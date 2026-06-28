## Begründungen

datenstruktur fürs gehege: ich hab ein set genommen, weil kein tier doppelt
im gehege sein darf, und set macht das automatisch. records haben schon ein
passendes equals/hashcode, da musste ich nix extra machen. konkret ein
linkedhashset statt normales hashset, weil dann die reihenfolge erhalten
bleibt wie die tiere eingefügt wurden, bei normalem hashset wär die
reihenfolge einfach random.

parameter/rückgabetypen bei der zoo klasse: findEnclosureByName gibt
Enclosure<? extends Animal> zurück, weil im zoo ja verschiedene gehege-typen
gemischt drin sind (aquarium, terrarium usw), da kann man nicht einfach
einen festen typ nehmen. bei getAnimalsByPredicate hab ich Predicate<Animal>
genommen, damit man von außen beliebig filtern kann ohne dass ich für jeden
fall eine eigene methode schreiben muss.

## Aufgabe 3: Reflektion

### Generics

- generics helfen mir dass ich nicht aus versehen ein falsches tier in ein
  gehege packen kann. wenn ich versuche eine schlange ins aquarium zu tun
  kompiliert das gar nicht erst, der fehler kommt also schon beim schreiben
  vom code und nicht erst wenn man das programm startet.
- beispiel: Aquarium ist ein Enclosure<Fish>. add(new Snake("x")) geht da
  gar nicht, weil Snake kein Fish ist. wenn ich stattdessen einfach für
  alles Enclosure<Animal> genommen hätte würde der fehler erst beim
  ausführen auffallen, vielleicht sogar gar nicht.
- bei CatHouse<C extends Cat> ist das nochmal ein bisschen genauer. ein
  CatHouse<Tiger> lässt nur tiger rein, kein mix mit löwen, das legt man
  schon beim erstellen fest.

### Logging

- mit println kann man nicht einfach mal mehr oder weniger ausgeben lassen,
  entweder die zeile ist da oder man muss sie rausnehmen. mit einem logger
  und den levels kann ich das einfach umschalten ohne im code was zu ändern.
- INFO benutze ich wenn eine methode aufgerufen wird, also einfach um zu
  sehen was gerade passiert.
- WARNING wenn was nicht gefunden wird, z.b. ein gehege das es nicht gibt.
  ist ja kein richtiger fehler aber trotzdem komisch.
- SEVERE nur wenn was wirklich nicht stimmen sollte, bei mir z.b. wenn zwei
  gehege den gleichen namen haben.

### Streams

- bei getAllAnimals und getAllMammals war stream ziemlich praktisch, mit
  flatMap konnte ich durch alle gehege und gleichzeitig durch alle tiere
  gehen ohne zwei schleifen ineinander zu schreiben.
- bei countAnimalsByType mit groupingBy und counting wäre das mit normalen
  schleifen viel mehr code gewesen, da hätte ich mir selber eine map bauen
  und hochzählen müssen.
- unübersichtlich fand ich summary(), weil da mehrere streams hintereinander
  kommen, erst groupingBy und dann nochmal ein stream über die ergebnisse.
  das hab ich beim ersten mal nicht direkt verstanden und musste es mir
  nochmal in ruhe angucken.
