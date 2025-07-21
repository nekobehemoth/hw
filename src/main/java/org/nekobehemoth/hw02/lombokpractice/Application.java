package org.nekobehemoth.hw02.lombokpractice;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
public class Application {
    private final String name;
    private final String version;
    private @NonNull String description;
    private Boolean ran;
}
