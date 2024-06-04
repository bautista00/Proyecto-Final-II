package com.proyectointegrador.msevents.repository.repository;

import com.proyectointegrador.msevents.configuration.feign.FeignInterceptor;
import com.proyectointegrador.msevents.domain.Place;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Optional;

@FeignClient(name= "ms-place", url="http://localhost:8084", configuration = FeignInterceptor.class)
public interface FeignPlaceRepository {
    @RequestMapping(method = RequestMethod.GET, value = "/place/public/id/{id}")
    ResponseEntity<Optional<Place>> getPlaceById(@PathVariable Long id);
}
