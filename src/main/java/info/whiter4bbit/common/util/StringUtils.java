/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.whiter4bbit.common.util;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * @author whiter4bbit
 */
public class StringUtils {

    public static String getSetterName(Field field) {
        String prefix = "set";
        return prefix + capitalizeFirstLetter(field.getName());
    }

    public static String capitalizeFirstLetter(String word) {
        if (word.length() > 0) {
            word = word.substring(0, 1).toUpperCase() + word.substring(1);
        }
        return word;
    }

    public static String join(Collection<?> words, String symb) {
        String[] words_ = new String[words.size()];
        int i=0;
        for(Object w : words){
            words_[i++] = String.valueOf(w);
        }
        return join(words_, symb);
    }

    public static String join(String[] words, String symb) {
        String joined = "";
        for (int i = 0; i < words.length; i++) {
            joined += words[i];
            if (i < words.length - 1) {
                joined += symb;
            }
        }
        return joined;
    }

    public static void main(String[] a) {
        System.out.println(capitalizeFirstLetter("mama"));
    }

}
