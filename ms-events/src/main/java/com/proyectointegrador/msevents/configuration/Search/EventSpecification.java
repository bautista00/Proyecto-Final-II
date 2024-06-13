package com.proyectointegrador.msevents.configuration.Search;

import com.proyectointegrador.msevents.domain.Event;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class EventSpecification {

    public static Specification<Event> nameContains(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Event> categoryContains(String category) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("category")), "%" + category.toLowerCase() + "%");
    }

    public static Specification<Event> cityContains(String city) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("city")), "%" + city.toLowerCase() + "%");
    }

    public static Specification<Event> dateIs(Date date) {
        return (root, query, cb) -> cb.equal(root.get("date"), date);
    }
}

