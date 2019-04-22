package assembler.tables;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class DirectiveTable {

    private static final Set<String> assemblerDirectives = new HashSet<>();

    /**
     * Cache directive table before starting program
     */
    static {
        load();
    }

    /**
     * Load all directives to directive table
     */
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

    /**
     * @param directive is a String passed which is supposed to be found
     *                  in the set table
     * @return boolean if found in the directive set
     */
    public static boolean contains(@NotNull String directive) {
        return assemblerDirectives.contains(directive.toUpperCase());
    }
}
