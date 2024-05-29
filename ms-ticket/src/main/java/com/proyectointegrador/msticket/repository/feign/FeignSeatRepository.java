package com.proyectointegrador.msticket.repository.feign;
;
import com.proyectointegrador.msticket.configuration.feign.FeignInterceptor;
import com.proyectointegrador.msticket.domain.Seat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

@FeignClient( name= "ms-place", url="http://localhost:8084", configuration = FeignInterceptor.class)
public interface FeignSeatRepository {
    @RequestMapping(method = RequestMethod.GET, value = "/seat/private/ticket/{id}")
    List<Seat> findByTicketId(@PathVariable Long id);
}
