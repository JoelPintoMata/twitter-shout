package com.letsShout.business;

import akka.http.javadsl.testkit.JUnitRouteTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class MessageRegistryTest extends JUnitRouteTest {

    @Test
    public void testShouter() {
        List<String> list = new LinkedList<>();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        Shouter shouter = new Shouter();
        list = shouter.shout(list);
        Assert.assertEquals(list.size(), 3);
        Assert.assertEquals(list.get(0), "TEST1!");
        Assert.assertEquals(list.get(1), "TEST2!");
        Assert.assertEquals(list.get(2), "TEST3!");
    }
}