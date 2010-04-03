/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.whiter4bbit.expression;

/**
 *
 * @author whiter4bbit
 */
public class Token {

    private final String val;

    private final int type;

    public Token(String val, int type) {
        this.val = val;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getVal() {
        return val;
    }

    @Override
    public String toString() {
        return "Token: "+val+" type: "+type;
    }

}
