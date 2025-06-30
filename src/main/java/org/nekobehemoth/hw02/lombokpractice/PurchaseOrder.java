package org.nekobehemoth.hw02.lombokpractice;

import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class PurchaseOrder {
    private Long id;
    private int supplier_id;
    private int location_id;

}
