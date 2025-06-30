package org.nekobehemoth.hw02.lombokpractice;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.time.LocalTime;

public class Main {

    static SimpleDateFormat formatter = new SimpleDateFormat ("dd-MMM-yyyy");
    static String stringDate = "01-JUL-2025";


    @SneakyThrows
    public static void main(String[] args)  {
        //@Data annotation
        //Generates the setters
        Shipment ship = new Shipment();
        ship.setShipDate(formatter.parse(stringDate));
        ship.setId(1L);
        ship.setCreateUser("nekobehemoth");
        //Generate getters
        System.out.println("ID: " + ship.getId() + ";\n"
                            + "Ship date: " + ship.getShipDate() + ";\n"
                            + "Create user: " + ship.getCreateUser() + ";\n") ;
        //Generates toString and equals method
        System.out.println(ship.toString());
        Shipment shipCopy = new Shipment();
        //Here should be thrown "java: unreported exception java.text.ParseException;
        // must be caught or declared to be thrown" error
        // But because I used @SneakyThrows annotation it handled by lombok
        shipCopy.setShipDate(formatter.parse(stringDate));
        shipCopy.setId(1L);
        shipCopy.setCreateUser("nekobehemoth");

        System.out.println(ship.equals(shipCopy));
        //////////////////////////////////////////////////////////////////////////
        //RequiredArgsConstructor
        //Generates constructor with arguments for final or @NonNull fields
        Application app = new Application("Dictionary", "1.0.0", "English - Japanese dictionary");
        app.setRan(true);

        ///////////////////////////////////////////////////////////////////////////
        //@AllArgsConstructor
        //Generates constructor with all filed as arguments
        PurchaseOrder po = new PurchaseOrder(1L, 57519, 1101);

        ///////////////////////////////////////////////////////////////////////////

        //@Builder
        //Allow to create object using Builder pattern, so you can build object step by step
        //convenient when you have many fields which should be setup
        Widget clockWidget = Widget.builder()
                .id(1L)
                .name("clock")
                .description("Modern clock for main screen")
                .build();

        //@Setter/@Getter
        //Generates setters and getters
        Store store = new Store();
        store.setId(1101L);
        store.setName("7-eleven");
        store.setAddress("25 Sukhumvit street");
        store.setOpenTime(LocalTime.of(9,0,0));
        System.out.println("ID: " + store.getId() + ";\n" +
                "Name: " + store.getName() + ";\n" +
                "Address: " + store.getAddress()+ ";\n");

        //@SneakyThrows
        //Need by surrounded by try catch block without @SneakyThrows
        store.openStore(LocalTime.of(11,0,0));


        //@Accessors(chain = true): use fluent API pattern, all methods returns this, which is why you
        //can call class methods in chain (example of this pattern is Java's Stream API)
        JdbcConnectionConfiguration conn = new JdbcConnectionConfiguration();
        conn.setJdbcDriver("oracle.jdbc.driver.OracleDriver")
                .setHostname("hostname")
                .setPort(1527)
                .setServiceName("service_name_1");

    }



}
