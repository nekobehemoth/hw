package org.nekobehemoth.hw03;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CustomLinkedList<String> cll = new CustomLinkedList<>();
        cll.add("first");
        cll.add("second");
        cll.add("third");

        cll.remove("first");
        System.out.println(cll.toString());

        LinkedList<String> ll = new LinkedList<>();
        ll.add("first");
        ll.add("second");
        ll.add("third");
        ll.remove("first");
        System.out.println(ll.toString());

        ll.offer("last");

    }
}
