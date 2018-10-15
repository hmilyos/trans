package com.imooc.example.ticket;

import org.junit.Test;

import java.util.UUID;

/**
 * Created by mavlarn on 2018/3/30.
 */
public class Test1 {

    @Test
    public void test1() {
        UUID id = UUID.randomUUID();
        System.out.println(id);
        System.out.println(id.variant());
        System.out.println(id.version());
        System.out.println(id.clockSequence());
        System.out.println(id.getLeastSignificantBits());
        System.out.println(id.getMostSignificantBits());
        System.out.println(id.timestamp());
        System.out.println(id.node());
        System.out.println(id.hashCode());

    }

}
