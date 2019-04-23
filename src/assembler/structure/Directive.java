package assembler.structure;

import assembler.constants.Length;
import org.jetbrains.annotations.NotNull;

public class Directive {

    private String directive;
    private Length length;

    public Directive(String directive, Length length) {
        setDirective(directive);
        this.length = length;
    }

    public String getDirective() {
        return directive;
    }

    public void setDirective(@NotNull String directive) {
        this.directive = directive.toUpperCase();
    }

    public Length getLength() {
        return length;
    }

    public void setLength(Length length) {
        this.length = length;
    }
}
