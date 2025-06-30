package org.nekobehemoth.hw02.lombokpractice;


import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.time.LocalTime;

@Getter
@Setter
public class Store {
    private Long id;
    private String name;
    private String address;
    private LocalTime openTime;

    @SneakyThrows(BusinessException.class)
    public void openStore(LocalTime currentTime) throws BusinessException{
        if (currentTime.isBefore(openTime)) throw new BusinessException("You can't open store earlier that open time");
    }
}


class BusinessException extends Exception  {
    public BusinessException(String errorMessage) {
        super(errorMessage);
    }
}