package me.stuntguy3000.java.ledhub.object;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author stuntguy3000
 */
@Data
@AllArgsConstructor
public class LEDColor {
    private int r;
    private int g;
    private int b;

    @Override
    public String toString() {
        return "r" + r + ";g" + g + ";b" + b + ";";
    }
}
