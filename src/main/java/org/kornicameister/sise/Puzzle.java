package org.kornicameister.sise;

import org.kornicameister.sise.core.io.CLIWrapper;

/**
 * Puzzle15's Puzzle is the main class - launcher -
 * of whole application...contains only main(String []args) method
 * that is further accessible via command line tool
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class Puzzle {

    public static void main(String[] args) throws Exception {
        CLIWrapper.getCMD().newGraph(args);
    }
}
