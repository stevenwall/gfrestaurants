package com.stevewall.mcpserver.gfrestaurants;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GfrestaurantsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GfrestaurantsApplication.class, args);
	}

    @Bean
    public ToolCallbackProvider swallTools(GFRestaurantService gfRestaurantService)
    {
        return MethodToolCallbackProvider.builder().toolObjects(gfRestaurantService).build();
    }
}
