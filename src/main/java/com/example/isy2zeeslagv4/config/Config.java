package com.example.isy2zeeslagv4.config;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config {

    private static Config config;
    private HashMap<Setting, String> settings;

    public static Config getInstance()
    {
        if (config ==  null)
            config = new Config();

        return config;
    }

    private Config()
    {
        settings = new HashMap<>();
        initiateConfig();
    }

    private void initiateConfig()
    {
        System.out.println("Initiating config file");
        ArrayList<Setting> readySettings = new ArrayList<>();
        settings.clear();

        try
        {
            File configFile = new File("config.txt");
            if (!configFile.exists())
            {
                System.out.println("Creating a config file");
                configFile.createNewFile();
            } else {
                processConfigFile(configFile, readySettings);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        applyDefaultSettingsForMissing(readySettings);
    }

    private void processConfigFile(File configFile, List<Setting> readySettings) throws IOException
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] keyValue = line.split("=");
                if (keyValue.length < 2)
                {
                    System.out.println("Invalid config line: " + line);
                    continue;
                }
                Setting setting = Setting.getConfigByKey(keyValue[0]);
                setSetting(setting, keyValue[1], false);
                System.out.println("From config: " + setting + " = " + keyValue[1]);
                readySettings.add(setting);
            }
        }
    }

    private void applyDefaultSettingsForMissing(List<Setting> readySettings) {
        for (Setting setting : Setting.values())
        {
            if (!readySettings.contains(setting))
            {
                setSetting(setting, setting.getValue(), false);
                System.out.println("Default config " + setting + " = " + setting.getValue());
            }
        }
    }

    public String getSetting(Setting setting)
    {
        return settings.get(setting);
    }

    private void setSetting(Setting setting, String value, boolean save)
    {
        this.settings.put(setting, value);
        if (save) saveToConfigFile();
    }

    public void setSetting(Setting setting, String value)
    {
        setSetting(setting, value, true);
    }

    public void saveToConfigFile()
    {
        File file = new File("config.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            for (Setting setting : Setting.values())
            {
                if (!getSetting(setting).equals(setting.getValue())) {
                    fileWriter.append(setting.getConfig()).append("=").append(getSetting(setting)).append("\n");
                    System.out.println("Config saved: " + setting.getConfig() + "=" + getSetting(setting));
                }
            }

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
