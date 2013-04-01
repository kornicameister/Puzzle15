package org.kornicameister.sise.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ArrayUtilities {
    public static <V> V[] flatten(V[][] array) {
        List<V> flatten = new ArrayList<>(array.length * array[0].length);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                flatten.set(i * j, array[i][j]);
            }
        }
        return (V[]) flatten.toArray();
    }
}
