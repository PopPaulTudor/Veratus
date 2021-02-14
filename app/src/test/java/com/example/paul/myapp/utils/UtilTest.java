package com.example.paul.myapp.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilTest {
    @Test
    public void starttoHour() throws Exception {

        long expectedValue=51860310;
        long actualValue=Util.StarttoHour(1492082660440L);
        Assert.assertEquals(expectedValue,actualValue);
    }

    @Test
    public void testSetMilitoHour() throws  Exception{

        String expectedValue="21:22";
        String actualValue=Util.setMilitoHour(1496341320000L);
        Assert.assertEquals(expectedValue,actualValue);
    }

}