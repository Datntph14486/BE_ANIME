package com.qlmh.datn_qlmh.utilities;

import com.qlmh.datn_qlmh.entities.ProductEntity;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;

import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Sort;

import java.util.*;

public class QueryUtils {
    public static String buildLikeExp(final String query) {
        if (query == null || !H.isTrue(query.trim())) {
            return null;
        }
        return "%" + query.trim().replaceAll("\\s+", "%") + "%";
    }

    public static <T> Predicate buildLikeFilter(final Root<T> root, final CriteriaBuilder cb, final String keyword, final String... fieldNames) {
        final String likeExp = buildLikeExp(keyword);
        if (!H.isTrue(likeExp) || !H.isTrue(fieldNames)) {
            return cb.and();
        }
        return cb.or(H.collect(Arrays.asList(fieldNames), (index, fieldName) -> cb.like(cb.upper(root.get(fieldName)), likeExp.toUpperCase())).toArray(new Predicate[0]));
    }

    public static <T> Predicate buildLikeFilter(final Root<T> root, final CriteriaBuilder cb, final String keyword, final Path... paths) {
        final String likeExp = buildLikeExp(keyword);
        if (!H.isTrue(likeExp)) {
            return cb.and();
        }
        return cb.or(H.collect(Arrays.asList(paths), (index, path) -> cb.like(cb.upper((Expression<String>) path), likeExp.toUpperCase())).toArray(new Predicate[0]));
    }

    public static <T> Predicate buildSimpleLikeFilter(final Root<T> root, final CriteriaBuilder cb, final String keyword, final String... fieldNames) {
        if (!H.isTrue(keyword) || !H.isTrue(fieldNames)) {
            return cb.and();
        }
        return cb.or(H.collect(Arrays.asList(fieldNames), (index, fieldName) -> cb.like(cb.upper(root.get(fieldName)), ("%" + keyword + "%").toUpperCase())).toArray(new Predicate[0]));
    }

    public static <T, P> Predicate
    buildEqFilter(final Root<T> root, final CriteriaBuilder cb, final String fieldName, final P value) {
        return H.isTrue(value) ? cb.equal(root.get(fieldName), value) : cb.and();
    }

    public static <T, P> Predicate buildEqFilter(final Root<T> root, final CriteriaBuilder cb, final Path fieldName, final P value) {
        return H.isTrue(value) ? cb.equal(fieldName, value) : cb.and();
    }

    public static <T> Predicate buildIsDeleteFilter(final Root<T> root, final CriteriaBuilder cb) {
        return cb.equal(root.get("isDelete"), false);
    }

    public static <T, P> Predicate buildInFilter(final Root<T> root, final CriteriaBuilder cb, final String fieldName, final Collection<P> values) {
        return H.isTrue(values) ? root.get(fieldName).in(values) : cb.and();
    }

    public static <T, P> Predicate buildInFilter(final Root<T> root, final CriteriaBuilder cb, Path path, final Collection<P> values) {
        return H.isTrue(values) ? path.in(values) : cb.and();
    }

    public static <T, P> Predicate buildInFilter(final Root<T> root, final CriteriaBuilder cb, final String fieldName, String values) {
        return H.isTrue(values) ? root.get(fieldName).in((Object[]) values.split(",")) : cb.and();
    }

    public static <T, P> Predicate buildInFilter(final Root<T> root, final CriteriaBuilder cb, final String fieldName, Subquery<?> subquery) {
        return H.isTrue(subquery) ? root.get(fieldName).in(subquery) : cb.and();
    }

    public static <T, P> Predicate buildInFilter(final Root<T> root, final CriteriaBuilder cb, Path path, final Subquery<?> subquery) {
        return H.isTrue(subquery) ? path.in(subquery) : cb.and();
    }

    public static <T, P> Predicate buildNotInFilter(final Root<T> root, final CriteriaBuilder cb, final String fieldName, Subquery<?> subquery) {
        return H.isTrue(subquery) ? cb.not(root.get(fieldName).in(subquery)) : cb.and();
    }

    public static <T, P> Predicate buildNotInFilter(final Root<T> root, final CriteriaBuilder cb, final String fieldName, final Collection<P> values) {
        return H.isTrue(values) ? cb.not(root.get(fieldName).in(values)) : cb.and();
    }
    public static <T> Predicate buildFilter(final Root<T> root, final CriteriaBuilder cb, final String fieldName, final Collection<?> values) {
        return H.isTrue(values) ? root.get(fieldName).in(values) : cb.and();
    }
    public static <T> Predicate buildBetweenFilter(final Root<T> root, final CriteriaBuilder cb, final String fieldName, final Collection<?> values1, final Collection<?> values2) {
        List<Predicate> pricePredicates = new ArrayList<>();
        if (values1 != null && values2 != null && values1.size() == values2.size()) {
            Object[] arr1 = values1.toArray();
            Object[] arr2 = values2.toArray();
            for (int i = 0; i < arr1.length; i++) {
                Predicate pricePredicate = cb.between(root.get(fieldName), arr1[i].toString(), arr2[i].toString());
                pricePredicates.add(pricePredicate);
            }
        }
        return H.isTrue(pricePredicates) ? cb.or(pricePredicates.toArray(new Predicate[pricePredicates.size()])) : cb.and();
    }



    @SneakyThrows
    public static <T, P> Predicate buildGreaterThanFilter(final Root<T> root, final CriteriaBuilder cb,
                                                          final String fieldName, String valueStart, String pattern) {

        return H.isTrue(valueStart) ? cb.greaterThanOrEqualTo(cb.function("", Date.class, root.get(fieldName)),
                DateUtils.parseDate(valueStart, pattern)) : cb.and();
    }

    @SneakyThrows
    public static <T, P> Predicate buildLessThanFilter(final Root<T> root, final CriteriaBuilder cb,
                                                       final String fieldName, String valueEnd, String pattern) {

        return H.isTrue(valueEnd) ? cb.lessThanOrEqualTo(cb.function("", Date.class, root.get(fieldName)),
                DateUtils.parseDate(valueEnd, pattern)) : cb.and();
    }

    public static Sort buildSort(String sortBy, String sortDirection) {
        Sort sort;
        if (sortBy != null && sortDirection != null) {
            if (sortDirection.equalsIgnoreCase("desc")) {
                sort = Sort.by(sortBy).descending();
            } else {
                sort = Sort.by(sortBy).ascending();
            }
        } else {
            sort = Sort.unsorted();
        }
        return sort;
    }


}
