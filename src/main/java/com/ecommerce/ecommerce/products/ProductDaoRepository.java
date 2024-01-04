package com.ecommerce.ecommerce.products;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDaoRepository {

    private final EntityManager entityManager;

    public ProductDaoRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<Product> findAllByQuery(ProductQueryString queries, Pageable pageable){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

        Root<Product> productRoot = criteriaQuery.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        System.out.println(queries);

        if(queries.getName() != null)
            predicates.add(criteriaBuilder.like(productRoot.get("name"), "%" + queries.getName() + "%"));

        if(queries.getPrice()  != null)
            predicates.add(criteriaBuilder.lessThanOrEqualTo(productRoot.get("price"), queries.getPrice()));

        if(queries.getProviderId() != null)
            predicates.add(criteriaBuilder.equal(productRoot.get("provider").get("id"), queries.getProviderId()));

        Predicate andPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        criteriaQuery.where(andPredicate);

        TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        List<Product> products = query.getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Product.class)));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(products, pageable, count);
    }
}
