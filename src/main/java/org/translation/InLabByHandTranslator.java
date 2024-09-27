package org.translation;

import java.util.ArrayList;
import java.util.List;


// Extra Task: if your group has extra time, you can add support for another country code in this class.

/**
 * An implementation of the Translator interface which translates
 * the country code "can" to several languages.
 */
public class InLabByHandTranslator implements Translator {
    public static final String CANADA = "can";
    /**
     * Returns the language abbreviations for all languages whose translations are
     * available for the given country.
     *
     * @param country the country
     * @return list of language abbreviations which are available for this country
     */
    @Override
    public List<String> getCountryLanguages(String country) {
        if (CANADA.equals(country)) {
            return new ArrayList<>(List.of("de", "en", "zh"));
        }
        return new ArrayList<>();
    }


    /**
     * Returns the country abbreviations for all countries whose translations are
     * available from this Translator.
     *
     * @return list of country abbreviations for which we have translations available
     */
    @Override
    public List<String> getCountries() {
        return new ArrayList<>(List.of(CANADA));
    }

    /**
     * Returns the name of the country based on the specified country abbreviation and language abbreviation.
     *
     * @param country  the country
     * @param language the language
     * @return the name of the country in the given language or null if no translation is available
     */
    @Override
    public String translate(String country, String language) {
        if (!CANADA.equals(country)) {
            return null;
        }
        switch (language) {
            case "de": return "Kanada";
            case "en": return "Canada";
            case "zh": return "加拿大";
            default: return null;
        }
    }
    @Override
    public String fromCountryCode(String code) {
        if ("can".equals(code)) {
            return "Canada";
        }
        return null;
    }
    @Override
    public String fromCountry(String country) {
        if ("Canada".equals(country)) {
            return "can";
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
