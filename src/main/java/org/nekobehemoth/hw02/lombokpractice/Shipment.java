package org.nekobehemoth.hw02.lombokpractice;


import lombok.Data;

import java.util.Date;

@Data
public class Shipment {
    private Long id;
    private Date shipDate;
    private String createUser;
    private final String item;
}
