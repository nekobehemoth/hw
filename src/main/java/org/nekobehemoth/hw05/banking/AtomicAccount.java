package org.nekobehemoth.hw05.banking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;


@AllArgsConstructor
@Getter
@Setter
public class AtomicAccount {
    public int id;
    public final AtomicLong balance;
}
