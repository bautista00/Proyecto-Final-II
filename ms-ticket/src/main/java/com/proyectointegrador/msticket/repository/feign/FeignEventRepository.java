package com.proyectointegrador.msticket.repository.feign;

import com.proyectointegrador.msticket.configuration.feign.FeignInterceptor;
import com.proyectointegrador.msticket.domain.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name= "ms-events", url="http://localhost:8083", configuration = FeignInterceptor.class)
public interface FeignEventRepository {
    @RequestMapping(method = RequestMethod.GET, value = "/event/public/getById/{id}")
    Event findEventById(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/events/idsByCriteria")
    List<Long> getEventIdsByReportSearch(@RequestParam Map<String, String> criteria);

}
