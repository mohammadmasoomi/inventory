package com.github.mohammadmasoomi.inventory.core.repository.base;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serial;

public class BaseSpecification<T> implements Specification<T> {

    @Serial
    private static final long serialVersionUID = 3354278435526830700L;

    private final SearchCriteria criteria;

    public BaseSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        switch (criteria.getOperation()) {
            case GREAT_THAN -> builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case GREAT_THAN_EQUAL -> builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN -> builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN_EQUAL -> builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
            case EQUAL -> {
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    builder.like(
                            root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
                } else {
                    builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
            }
        }
        throw new IllegalArgumentException("Not supported operation or operation is null");
    }
}
