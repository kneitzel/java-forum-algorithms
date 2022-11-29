package de.kneitzel.rechner;

import java.util.Scanner;

/**
 * Java Forum Thread:
 * https://www.java-forum.org/thema/taschenrechner-mit-mehr-als-2-zahlen.200279/
 *
 * Der eigentliche Rechner
 */
public class Rechner {

    /**
     * Das Hauptprogramm
     *
     * @param args Die Kommandozeilenparameter (Werden nicht ausgewertet!)
     */
    public static void main(String[] args) {
        System.out.println("Bitte einen Ausdruck mit Grundrechenarten eingeben.");
        String expression = new Scanner(System.in).nextLine();
        Node node = createNode(expression);
        System.out.println(expression + " = " + node.evaluate());
    }

    /**
     * Erzeugung eines Baumes aus einem Ausdruck.
     * @param expression Ausdruck der auszuwerten ist.
     * @return Ein Node mit dem (Teil)Baum.
     */
    public static Node createNode(String expression) {
        // Strichrechnung muss zuerst aufgenommen werden:
        int index = findFirstIndex(expression, '+', '-');

        // Wenn keine Strichrechnung gefunden, dann nach Punktrechnung schauen.
        if (index == -1) {
            index = findFirstIndex(expression, '*', '/');
        }

        if (index != -1) {
            // Operator gefunden.
            return new OperatorNode(
                    expression.charAt(index),
                    createNode(expression.substring(0, index).trim()),
                    createNode(expression.substring(index+1).trim()));
        } else {
            // Es muss eine Zahl sein.
            return new NumberNode(Double.parseDouble(expression));
        }
    }

    /**
     * Finde das erste Vorkommen eines von mehreren Zeichen
     */
    public static int findFirstIndex(String expression, char... chars) {
        int firstIndex = -1;
        for (char ch: chars) {
            int index = expression.indexOf(ch);
            if (index != -1 && (index < firstIndex || firstIndex == -1) ) {
                firstIndex = index;
            }
        }

        return firstIndex;
    }
}
