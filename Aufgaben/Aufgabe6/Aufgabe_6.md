Link zu meiner filterDSL fork:
https://github.com/JohannMagBackfisch/JO_prog2_ybel_filterdsl.git

Aufgabe 4 hier als text:

Aufgabe 4: Vergleich Visitor-Pattern vs. Pattern Matching

AstBuilderVisitor:

Der Visitor braucht intern Stacks um sich Zwischenergebnisse zu merken. Das ist relativ umständlich, weil man immer aufpassen muss dass man genau so viele Elemente vom Stack nimmt wie man draufgelegt hat. Wenn man z.B. bei visitOrExpr nicht genau weiß wie viele Kinder besucht wurden, kann der Stack schnell durcheinander kommen. Außerdem ist der Code schwerer zu lesen weil man immer im Kopf behalten muss welcher Stack gerade welchen Zustand hat.
Ein Vorteil ist dass der Visitor direkt vom ANTLR-Framework unterstützt wird, also FilterBaseVisitor schon vorgegeben ist und man nur die Methoden überschreiben muss.

AstBuilderPattern:

Der Pattern-Builder ist deutlich einfacher zu verstehen. Jede Methode bekommt einen Kontext und gibt direkt ein Expr zurück, kein globaler Zustand, keine Stacks. Man kann die Methoden einzeln lesen ohne den Rest verstehen zu müssen.
Der Code ist auch kürzer. Zum Beispiel buildLiteralList ist einfach:
javareturn ctx.literal().stream().map(this::buildLiteral).toList();
Das wäre beim Visitor viel umständlicher mit Stack und Reihenfolge umkehren etc.

Fazit:

Beide Varianten liefern dasselbe Ergebnis, aber der Pattern-Builder ist einfacher zu schreiben und zu verstehen. Der Visitor ist eigentlich für Fälle geeignet wo man mehrere verschiedene Operationen auf dem selben Baum haben will (z.B. Printer, Evaluator, Builder alle als Visitor). Für den reinen Aufbau des ASTs ist Pattern Matching die bessere Wahl.