package com.stevewall.mcpserver.gfrestaurants.domain;

import java.util.List;

public record GFRestaurant(String name, String category, String city, String state, boolean isDedicatedFacility, List<UserReview> reviews)
{
}
