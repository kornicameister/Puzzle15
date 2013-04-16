package org.kornicameister.sise.core.graph;

/**
 * GraphEdge represent link between two nodes
 * and defines strategy that is used by
 * searching algorithms to recognize
 * whether or not the node is accessible
 * or not.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface GraphEdge {
    void setAccessibleStrategy(NodeAccessibleStrategy strategy);

    GraphNode getSuccessor();

    Character getDirection();

    boolean isAccessible();
}
