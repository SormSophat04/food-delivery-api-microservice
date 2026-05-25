package com.food.eat.gatewayserver.mapper;

import com.food.eat.gatewayserver.dto.RouteApiRequest;
import com.food.eat.gatewayserver.dto.RouteApiResponse;
import com.food.eat.gatewayserver.entity.ApiRoute;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApiRouteMapper {

    ApiRoute toApiRoute(RouteApiRequest request);
    RouteApiResponse toRouteApiResponse(ApiRoute apiRoute);

}
