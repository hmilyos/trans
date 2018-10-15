package com.imooc.example.axon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mavlarn on 2018/6/17.
 */
public class Test1 {

    @Test
    public void test1() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        Map data = new HashMap();
        data.put("date", format.format(new Date()));
        data.put("name", "mav");

        System.out.println(om.writeValueAsString(data));


    }
}
