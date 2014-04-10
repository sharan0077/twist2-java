package com.thoughtworks.twist2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StepValueExtractorTest {

    @Test
    public void shouldExtractParametersInQuotes() {
        String stepText = "A step with \"parm0\" and \"param1\"";
        String extractedText = new StepValueExtractor().getFor(stepText);
        assertEquals("A step with {} and {}", extractedText);
    }

    @Test
    public void shouldExtractParameterInBracket() {
        String stepText = "A step with <parm0> and <param1>";
        String extractedText = new StepValueExtractor().getFor(stepText);
        assertEquals("A step with {} and {}", extractedText);
    }

    @Test
    public void shouldExtractParamterWhenStepAndParamterHasEscapedQuotes() {
        String stepText = "A step with \\\" \"param0\" and \"param with \\\" quote\"";
        String extractedText = new StepValueExtractor().getFor(stepText);
        assertEquals("A step with \" {} and {}", extractedText);
    }

    @Test
    public void shouldExtractParamterWhenAngularBracketParamHasEscapedQuotes() {
        String stepText = "A step with \\\" <param0> and <param with \\\" quote>";
        String extractedText = new StepValueExtractor().getFor(stepText);
        assertEquals("A step with \" {} and {}", extractedText);
    }

}
