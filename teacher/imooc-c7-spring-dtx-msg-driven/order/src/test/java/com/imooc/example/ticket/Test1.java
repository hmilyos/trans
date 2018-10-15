package com.imooc.example.ticket;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.fasterxml.uuid.impl.UUIDUtil;
import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by mavlarn on 2018/3/30.
 */
public class Test1 {

    @Test
    public void test1() {
        UUID id = UUID.randomUUID();
        printId(id);
    }

    @Test
    public void test2() {
        TimeBasedGenerator gen = Generators.timeBasedGenerator();
        List<UUID> ids = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            ids.add(gen.generate());
        }
        printId(Generators.nameBasedGenerator().generate("test"));
        printId(Generators.randomBasedGenerator().generate());

        for (int i = 0; i < 10; i++) {
            printId(ids.get(i));
        }

    }

    private void printId(UUID id) {
        System.out.println(id);
        System.out.println("bytes:" + new String(UUIDUtil.asByteArray(id)));
        System.out.println("variant:" + id.variant());
        System.out.println("version:" + id.version());
        try {
            System.out.println("clockSequence:" + id.clockSequence());
        } catch (Exception e) {}
        System.out.println("getLeastSignificantBits:" + id.getLeastSignificantBits());
        System.out.println("getMostSignificantBits:" + id.getMostSignificantBits());
        try {
            System.out.println("timestamp:" + id.timestamp());
        } catch (Exception e) {}
        try {
            System.currentTimeMillis();
            System.out.println("node:" + id.node());
        } catch (Exception e) {}
        System.out.println("hashCode:" + id.hashCode());
    }

    @Test
    public void test3() {
        ZonedDateTime t = ZonedDateTime.ofInstant(Instant.ofEpochMilli(137416356322040092L), ZoneId.systemDefault());
        System.out.println(t);
    }
}
