package org.nekobehemoth.hw02.lombokpractice;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.time.LocalTime;

public class Main {

    static SimpleDateFormat formatter = new SimpleDateFormat ("dd-MMM-yyyy");
    static String stringDate = "01-JUL-2025";


    @SneakyThrows
    public static void main(String[] args)  {
        System.out.println("---------------------START @DATA------------------------");
        //@Data annotation
        //Generates the setters
        //RequiredArgsConstructor
        Shipment ship = new Shipment("072345456");
        ship.setShipDate(formatter.parse(stringDate));
        ship.setId(1L);
        ship.setCreateUser("nekobehemoth");
        //Generate getters
        System.out.println("ID: " + ship.getId() + ";\n"
                            + "Ship date: " + ship.getShipDate() + ";\n"
                            + "Create user: " + ship.getCreateUser() + ";\n") ;
        //Generates toString and equals method
        System.out.println("Shipment 1: " + ship.toString());
        Shipment shipCopy = new Shipment("072345456");
        //Here should be thrown "java: unreported exception java.text.ParseException;
        // must be caught or declared to be thrown" error
        // But because I used @SneakyThrows annotation it handled by lombok
        shipCopy.setShipDate(formatter.parse(stringDate));
        shipCopy.setId(1L);
        shipCopy.setCreateUser("nekobehemoth");
        System.out.println("Shipment 2: " +  shipCopy.toString());
        System.out.println("Are shipment equals? " + ship.equals(shipCopy));
        System.out.println("---------------------END @DATA------------------------");
        //////////////////////////////////////////////////////////////////////////
        //RequiredArgsConstructor
        //Generates constructor with arguments for final or @NonNull fields
        System.out.println("---------------------START @RequiredArgsConstructor------------------------");
        Application app = new Application("Dictionary", "1.0.0", "English - Japanese dictionary");
        app.setRan(true);
        System.out.println("App info: " + app.toString());
        System.out.println("---------------------END @RequiredArgsConstructor------------------------");
        ///////////////////////////////////////////////////////////////////////////
        System.out.println("---------------------START @AllArgsConstructor------------------------");
        //@AllArgsConstructor
        //Generates constructor with all filed as arguments
        PurchaseOrder po = new PurchaseOrder(1L, 57519, 1101);
        System.out.println("PO object: " + po.toString());
        System.out.println("---------------------END @AllArgsConstructor------------------------");
        ///////////////////////////////////////////////////////////////////////////
        System.out.println("---------------------START @Builder------------------------");
        //@Builder
        //Allow to create object using Builder pattern, so you can build object step by step
        //convenient when you have many fields which should be setup
        Widget clockWidget = Widget.builder()
                .id(1L)
                .name("clock")
                .description("Modern clock for main screen")
                .build();
        System.out.println("Widget object: " + clockWidget.toString());
        System.out.println("---------------------END @Builder------------------------");

        System.out.println("-------------------START @Setter/@Getter------------------------");
        //@Setter/@Getter
        //Generates setters and getters
        Store store = new Store();
        store.setId(1101L);
        store.setName("7-eleven");
        store.setAddress("25 Sukhumvit street");
        store.setOpenTime(LocalTime.of(9,0,0));
        System.out.println("ID: " + store.getId() + ";\n" +
                "Name: " + store.getName() + ";\n" +
                "Address: " + store.getAddress()+ ";");
        System.out.println("-------------------END @Setter/@Getter------------------------");
        System.out.println("-------------------START @SneakyThrows------------------------");
        //@SneakyThrows
        //Need by surrounded by try catch block without @SneakyThrows
        store.openStore(LocalTime.of(11,0,0));
        System.out.println("Store open time: " + store.getOpenTime());
        System.out.println("-------------------END @SneakyThrows------------------------");
        System.out.println("-------------------START @Accessors------------------------");
        //@Accessors(chain = true): use fluent API pattern, it makes set methods returns this, which is why you
        //can call class methods in chain (example of this pattern is Java's Stream API)
        JdbcConnectionConfiguration conn = new JdbcConnectionConfiguration();
        conn.setJdbcDriver("oracle.jdbc.driver.OracleDriver")
                .setHostname("hostname")
                .setPort(1527)
                .setServiceName("service_name_1");
        System.out.println("JDBC conn object: " + conn.toString());
        System.out.println("-------------------END @Accessors------------------------");
    }



}
