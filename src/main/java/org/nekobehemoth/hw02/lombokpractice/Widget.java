package org.nekobehemoth.hw02.lombokpractice;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Widget {
    private final Long id;
    private final String name;
    private final String description;
}
