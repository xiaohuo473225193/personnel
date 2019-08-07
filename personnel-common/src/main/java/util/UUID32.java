package util;

import java.util.UUID;

public class UUID32 {
    public static String random(){
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
