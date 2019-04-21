package assembler.tables;

import java.util.HashSet;
import java.util.Set;

public class DirectiveTable {

    static {
        load();
    }

    private static final Set<String> assemblerDirectives = new HashSet<>();

    public static boolean contains(String directive) {
        return assemblerDirectives.contains(directive);
    }

    private static void load() {
        assemblerDirectives.add("BYTE");
        assemblerDirectives.add("RESB");
        assemblerDirectives.add("WORD");
        assemblerDirectives.add("RESW");
        assemblerDirectives.add("START");
        assemblerDirectives.add("BASE");
        assemblerDirectives.add("NOBASE");
        assemblerDirectives.add("END");
        assemblerDirectives.add("LTORG");
        assemblerDirectives.add("EXTREF");
        assemblerDirectives.add("EXTDEF");
        assemblerDirectives.add("ORG");
        assemblerDirectives.add("EQU");
        assemblerDirectives.add("CSECT");
    }
}
