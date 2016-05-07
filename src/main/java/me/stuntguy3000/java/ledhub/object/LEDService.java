package me.stuntguy3000.java.ledhub.object;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author stuntguy3000
 */
@Data
@AllArgsConstructor
public class LEDService {
    private String serviceName;
    private HashMap<String, LEDServiceAction> serviceActions;
}
