package de.kneitzel.rechner;

/**
 * Java Forum Thread:
 * https://www.java-forum.org/thema/taschenrechner-mit-mehr-als-2-zahlen.200279/
 *
 * Node mit einem Operator.
 */
public class OperatorNode implements Node {

    /**
     * Operator für Berechnung.
     */
    private char operator;

    /**
     * Operanden in Teilbäumen.
     */
    private Node left, right;

    /**
     * Erzeugt einen neuen Operator Node
     * @param operator Operator
     * @param left linker Teilbaum
     * @param right rechter Teilbaum
     */
    public OperatorNode(char operator, Node left, Node right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    /**
     * Wertet den Knoten des Rechnungsbaumes aus.
     *
     * @return Den Wert des (Teil)Baumes
     */
    @Override
    public double evaluate() {
        return switch (operator) {
            case '+' -> left.evaluate() + right.evaluate();
            case '-' -> left.evaluate() - right.evaluate();
            case '*' -> left.evaluate() * right.evaluate();
            case '/' -> left.evaluate() / right.evaluate();
            default -> throw new IllegalStateException("Operaror " + operator + " ist ungültig!");
        };
    }


}
