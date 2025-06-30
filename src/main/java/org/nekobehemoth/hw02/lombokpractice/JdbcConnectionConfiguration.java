package org.nekobehemoth.hw02.lombokpractice;

import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
public class JdbcConnectionConfiguration {
    private String jdbcDriver;
    private String hostname;
    private int port;
    private String serviceName;

}
