package de.kneitzel.rechner;

/**
 * Java Forum Thread:
 * https://www.java-forum.org/thema/taschenrechner-mit-mehr-als-2-zahlen.200279/
 *
 * Knoten eines einfachen Rechners für Grundrechenarten.
 */
public interface Node {
    /**
     * Wertet den Knoten des Rechnungsbaumes aus.
     * @return Den Wert des (Teil)Baumes
     */
    public double evaluate();
}
