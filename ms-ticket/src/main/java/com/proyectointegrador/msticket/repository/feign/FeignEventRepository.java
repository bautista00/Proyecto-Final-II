package com.proyectointegrador.msticket.repository.feign;

import com.proyectointegrador.msticket.configuration.feign.FeignInterceptor;
import com.proyectointegrador.msticket.domain.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name= "ms-events", url="http://localhost:8083", configuration = FeignInterceptor.class)
public interface FeignEventRepository {
    @RequestMapping(method = RequestMethod.GET, value = "/event/public/getById/{id}")
    Event findEventById(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.POST, value = "/event/private/idsByCriteria")
    List<Long> getEventIdsByReportSearch(@RequestBody Map<String, String> criteria);

}
