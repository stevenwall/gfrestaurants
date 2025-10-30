package com.stevewall.mcpserver.gfrestaurants;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.stevewall.mcpserver.gfrestaurants.domain.GFRestaurant;

public class GFRestaurantServiceTest
{
    GFRestaurantService service;

    @BeforeEach
    void before() {
        service = new GFRestaurantService();
        service.init();
    }

    @Test
    void testSearchRestaurant()
    {
        List<GFRestaurant> restaurantList = service.searchRestaurant("Minneapolis", "MN", "Italian", false, 3);
        Assertions.assertNotNull(restaurantList);
        Assertions.assertEquals(1, restaurantList.size());
    }
}
