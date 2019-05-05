package assembler.structure;

import assembler.constants.Length;
import org.jetbrains.annotations.NotNull;

public final class Directive {

    private String directive;
    private Length length;
    private boolean hasOperand;

    public Directive(@NotNull String directive, @NotNull Length length, boolean hasOperand) {
        setDirective(directive);
        this.length = length;
        this.hasOperand = hasOperand;
    }

    @NotNull
    public String getDirective() {
        return directive;
    }

    public void setDirective(@NotNull String directive) {
        this.directive = directive.toUpperCase();
    }

    @NotNull
    public Length getLength() {
        return length;
    }

    public boolean hasOperand() {
        return hasOperand;
    }
}
