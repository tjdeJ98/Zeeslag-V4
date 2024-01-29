package com.example.isy2zeeslagv4.config;

/**
 * Holds setting values for the application
 */
public enum Setting {

    IP ("ip", "145.33.225.170"),
    PORT ("port", "7777"),
    NAME ("name", ""),
    TIME_OUT ("time_out", "30");

    private String config;
    private String value;

    Setting(String config, String value)
    {
        this.config = config;
        this.value = value;
    }

    public static Setting getConfigByKey(String key) {
        for (Setting setting : values())
            if (setting.getConfig().equals(key))
                return setting;

        return null;
    }

    public String getConfig()
    {
        return config;
    }

    public String getValue()
    {
        return value;
    }


}
