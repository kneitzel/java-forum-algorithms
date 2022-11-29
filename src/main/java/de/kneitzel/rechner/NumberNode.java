package de.kneitzel.rechner;

/**
 * Java Forum Thread:
 * https://www.java-forum.org/thema/taschenrechner-mit-mehr-als-2-zahlen.200279/
 *
 * Node mit einer fetsen Zahl.
 */
public class NumberNode implements Node {
    /**
     * Wert des Nodes.
     */
    private double value;

    /**
     * Erzeugt einen NumberNode mit einem speziellen Wert.
     * @param value Wert des Nodes.
     */
    public NumberNode(double value) {
        this.value = value;
    }

    /**
     * Wertet den Knoten des Rechnungsbaumes aus.
     *
     * @return Den Wert des (Teil)Baumes
     */
    @Override
    public double evaluate() {
        return value;
    }
}
