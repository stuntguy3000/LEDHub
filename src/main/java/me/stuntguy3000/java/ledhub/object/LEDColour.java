package me.stuntguy3000.java.ledhub.object;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author stuntguy3000
 */
@Data
@AllArgsConstructor
public class LEDColour {
    private int r;
    private int g;
    private int b;

    public String getString(double multiplier) {
        return "r" + (int) (r * multiplier) +
                ";g" + (int) (g * multiplier) +
                ";b" + (int) (b * multiplier) + ";";
    }
}
