package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {
    private Map<String, String> countryCodeToName;
    private Map<String, Map<String, String>> translations;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     *
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        countryCodeToName = new HashMap<>();
        translations = new HashMap<>();

        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryData = jsonArray.getJSONObject(i);

                if (!countryData.has("code") || !countryData.has("name")) {
                    System.err.println("Error: Missing 'code' or 'name' field in JSON object at index " + i);
                    continue;
                }

                String countryCode = countryData.getString("code");
                String countryName = countryData.getString("name");

                countryCodeToName.put(countryCode, countryName);

                Map<String, String> languageTranslations = new HashMap<>();

                for (String key : countryData.keySet()) {
                    if (!key.equals("code") && !key.equals("name")) {
                        languageTranslations.put(key, countryData.getString(key));
                    }
                }

                translations.put(countryCode, languageTranslations);
            }
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException("Error loading or parsing JSON file: " + filename, ex);
        }
    }


    @Override
    public List<String> getCountryLanguages(String country) {
        String countryCode = fromCountry(country);
        if (countryCode != null && translations.containsKey(countryCode)) {
            return new ArrayList<>(translations.get(countryCode).keySet());
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getCountries() {
        return new ArrayList<>(countryCodeToName.values());
    }

    @Override
    public String translate(String country, String language) {
        String countryCode = fromCountry(country);
        if (countryCode != null && translations.containsKey(countryCode)) {
            return translations.get(countryCode).getOrDefault(language, null);
        }
        return null;
    }

    @Override
    public String fromCountryCode(String code) {
        return countryCodeToName.getOrDefault(code, null);  // Return the country name for the given code
    }

    @Override
    public String fromCountry(String countryName) {
        for (Map.Entry<String, String> entry : countryCodeToName.entrySet()) {
            if (entry.getValue().equals(countryName)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public String fromLanguageCode(String code) {
        switch (code) {
            case "de": return "German";
            case "en": return "English";
            case "zh": return "Chinese";
            default: return null;
        }
    }

    @Override
    public String fromLanguage(String language) {
        switch (language) {
            case "German": return "de";
            case "English": return "en";
            case "Chinese": return "zh";
            default: return null;
        }
    }
}
