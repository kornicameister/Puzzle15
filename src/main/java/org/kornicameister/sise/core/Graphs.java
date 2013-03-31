package org.kornicameister.sise.core;

/**
 * Indicates types of used algorithm..quite useful
 * for CLI and for the algorithm description
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public enum Graphs {
    BFS("b", "Breadth First Search"),
    DFD("d", "Depth First Search"),
    IDFS("i", "Iterative deepening Depth First Search");
    private final String acronym;
    private final String desc;

    Graphs(String acronym, String desc) {
        this.acronym = acronym;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getAcronym() {
        return acronym;
    }
}
