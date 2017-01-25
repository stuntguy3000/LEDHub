package me.stuntguy3000.java.ledhub.object;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;

/**
 * @author stuntguy3000
 */
@Data
@AllArgsConstructor
public class LEDColour {
    private int r;
    private int g;
    private int b;

    public LEDColour(Color decode) {
        r = decode.getRed();
        g = decode.getGreen();
        b = decode.getBlue();
    }

    public String getString(double multiplier) {
        return "r" + (int) (r * multiplier) +
                ";g" + (int) (g * multiplier) +
                ";b" + (int) (b * multiplier) + ";";
    }
}
