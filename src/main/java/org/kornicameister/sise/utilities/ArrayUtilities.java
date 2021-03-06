package org.kornicameister.sise.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ArrayUtilities {
    public static Integer[] flatten(Integer[][] array) {
        List<Integer> flatten = new ArrayList<>(array.length * array[0].length);
        int index = 0;
        for (Integer[] anArray : array) {
            for (int j = 0; j < array[0].length; j++) {
                flatten.add(null);
                flatten.set(index++, anArray[j]);
            }
        }
        return flatten.toArray(new Integer[flatten.size()]);
    }

    public static Integer[][] swapByIndex(Integer[][] target, Point from, Point to) {
        int fX = from.getX(),
                fY = from.getY(),
                tX = to.getX(),
                tY = to.getY();

        Integer temp = target[fX][fY];
        target[fX][fY] = target[tX][tY];
        target[tX][tY] = temp;

        return target;
    }
}
