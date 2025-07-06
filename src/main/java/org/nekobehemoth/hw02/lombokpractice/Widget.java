package org.nekobehemoth.hw02.lombokpractice;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Widget {
    private final Long id;
    private final String name;
    private final String description;
}
