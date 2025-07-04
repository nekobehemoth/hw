package org.nekobehemoth.hw02.testrunner;

import lombok.Getter;

@Getter
public enum SupportedAnnotations {
    BEFORE_EACH("BeforeEach"),
    AFTER_EACH("AfterEach"),
    TEST("Test"),
    PARAMETERIZED_TEST("ParameterizedTest"),
    CSV_FILE_SOURCE("CsvFileSource"),
    CSV_SOURCE("CsvSource"),
    METHOD_SOURCE("MethodSource");

    private final String annotation;

    private SupportedAnnotations(String annotation) {
        this.annotation = annotation;
    }

}
