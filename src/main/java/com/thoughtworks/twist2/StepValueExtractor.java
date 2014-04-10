package com.thoughtworks.twist2;

public class StepValueExtractor {

    public String getFor(String value) {
        StringBuilder extractedValue = new StringBuilder();
        char[] chars = value.toCharArray();
        Boolean inEscape = false;
        boolean inQuotes = false;
        boolean inBracket = false;
        for (char aChar : chars) {
            if (inEscape) {
                inEscape = false;
                if (!inQuotes && !inBracket)
                    extractedValue.append(aChar);
            } else if (aChar == '"') {
                if (!inQuotes) {
                    inQuotes = true;
                } else {
                    extractedValue.append("{}");
                    inQuotes = false;
                }
            } else if (aChar == '<' && !inBracket) {
                inBracket = true;
            } else if (aChar == '>' && inBracket) {
                extractedValue.append("{}");
                inBracket = false;
            } else if (aChar == '\\') {
                inEscape = true;
            } else if (!inQuotes && !inBracket) {
                extractedValue.append(aChar);
            }
        }
        return extractedValue.toString();
    }
}
