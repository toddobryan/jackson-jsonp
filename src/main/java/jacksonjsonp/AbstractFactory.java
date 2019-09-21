package jacksonjsonp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

abstract class AbstractFactory {
    private Map<String, ?> config;

    AbstractFactory(Map<String, ?> config) {
        if (config == null) {
            this.config = Collections.EMPTY_MAP;
        } else {
            Set<String> supported = getSupportedProperties();
            Map<String, Object> supportedConfigs = new HashMap<>();
            for (String key : config.keySet()) {
                if (supported.contains(key)) {
                    supportedConfigs.put(key, config.get(key));
                }
            }
            this.config = Collections.unmodifiableMap(supportedConfigs);
        }
    }

    public abstract Set<String> getSupportedProperties();

    public Map<String, ?> getConfigInUse() {
        return config;
    }
}
