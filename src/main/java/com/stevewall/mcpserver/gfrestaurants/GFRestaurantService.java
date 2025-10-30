package com.stevewall.mcpserver.gfrestaurants;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.stevewall.mcpserver.gfrestaurants.domain.GFRestaurant;
import com.stevewall.mcpserver.gfrestaurants.domain.UserReview;

@Service
public class GFRestaurantService
{
    private static final Logger log = LoggerFactory.getLogger(GFRestaurantService.class);
    private final List<GFRestaurant> glutenFreeRestaurants = new ArrayList<>();

    @Tool(name = "get_gf_restaurant", description = "Get a single gluten free restaurant by title")
    public GFRestaurant getRestaurant(String name) {
        return glutenFreeRestaurants.stream()
                                    .filter(restaurant -> restaurant.name().equals(name))
                                    .findFirst()
                                    .orElse(null);
    }

    @Tool(name = "search_gf_restaurants",
            description = """
        Search for gluten free restaurant by city, state, cuisine or rating.
        The state parameter should be a 2 character state code.
        The cuisine parameter is the food category such as Italian, Asian, Breakfast, Bakery, American, French, etc.  This is an optional field.
        The isDedicated parameter is a boolean that would be set to true if only dedicated gluten free facilities should be considered.  This is an optional field.
        The numStars parameter is an integer and should the value 1, 2, 3, 4 or 5.
        """)
    public List<GFRestaurant> searchRestaurant(String city, String state, String cuisine, Boolean isDedicated,Integer numStars)
    {
        // TODO:  Call an API here
        return glutenFreeRestaurants.stream()
                                    .filter(restaurant -> {
                                        boolean continueMatching = true;
                                        if (!(Strings.isNotBlank(city) && restaurant.city().equals(city)))
                                            continueMatching = false;
                                        if (!(continueMatching && Strings.isNotBlank(state) && restaurant.state().equals(state)))
                                            continueMatching = false;
                                        if (!(continueMatching && Strings.isNotBlank(cuisine) && restaurant.category().equals(cuisine)))
                                            continueMatching = false;
                                        // TODO: Fix logic for isDedicated
//                                        if (!(continueMatching && isDedicated != null && isDedicated.equals(restaurant.isDedicatedFacility())))
//                                            continueMatching = false;
                                        // TODO: Check rating
//                                        if (!(continueMatching && Strings.isNotBlank(city) && restaurant.city().equals(city)))
//                                            continueMatching = false;

                                        return continueMatching;
                                    })
                .toList();
    }

    @PostConstruct
    public void init() {
        glutenFreeRestaurants.addAll(List.of(
                new GFRestaurant("MPLS Italian", "Italian", "Minneapolis", "MN", true,
                                 List.of(new UserReview(4, "Great Italian food"),
                                         new UserReview(3, "Service was slow, but the food was good"))
                ),
                new GFRestaurant("ROCH Italian", "Italian", "Rochester", "MN", false,
                                 List.of(new UserReview(2, "My meal was contaminated"),
                                         new UserReview(1, "Took a long time to get seated at our table"))
                ),
                new GFRestaurant("MPLS French", "French", "Minneapolis", "MN", false,
                                 List.of(new UserReview(5, "Authentic French food"),
                                         new UserReview(4, "Service was slow"))
                ),
                new GFRestaurant("ROCH French", "French", "Rochester", "MN", false,
                                 List.of(new UserReview(3, "Food was okay"),
                                         new UserReview(2, "I didn't like my meal"))
                ),
                new GFRestaurant("MPLS Asian", "Asian", "Minneapolis", "MN", false,
                                 List.of(new UserReview(1, "Very few gluten free items on the menu.  Just had a bowl of white rice."),
                                         new UserReview(5, "Fantastic food!"))
                ),
                new GFRestaurant("ROCH Asian", "Asian", "Rochester", "MN", false,
                                 List.of(new UserReview(4, "The gluten free beef and broccoli is good."),
                                         new UserReview(3, "There are only a couple of gluten free options"))
                ),
                new GFRestaurant("MPLS Pizza", "Italian", "Minneapolis", "MN", false,
                                 List.of(new UserReview(2, "No gluten free pasta.  I could only have a salad."),
                                         new UserReview(1, "Barely anything is gluten free."))
                ),
                new GFRestaurant("ROCH Pizza", "Italian", "Rochester", "MN", true,
                                 List.of(new UserReview(5, "Some of the best gluten free Italian food"),
                                         new UserReview(4, "Our server was extremely friendly and knew the menu."))
                )
        ));
    }
}
