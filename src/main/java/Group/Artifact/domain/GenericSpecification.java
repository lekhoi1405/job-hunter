package Group.Artifact.domain;

import org.apache.coyote.BadRequestException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import Group.Artifact.domain.entity.Company;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class GenericSpecification<T> implements Specification<T> {
    private SearchCriteria criteria;
    public GenericSpecification(SearchCriteria criteria){
        this.criteria = criteria;
    }
@Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        
        Class<?> type = root.get(criteria.getKey()).getJavaType();
        String stringValue = criteria.getValue().toString();
    

        Object castedValue;
        if (type == Integer.class || type == int.class) {
            castedValue = Integer.parseInt(stringValue);
        } else if (type == Double.class || type == double.class) {
            castedValue = Double.parseDouble(stringValue);
        } else if (type == Boolean.class || type == boolean.class) {
            castedValue = Boolean.parseBoolean(stringValue);
        } else {
            castedValue = stringValue; 
        }

        
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                root.get(criteria.getKey()), (Comparable) castedValue);
        } 
        
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(   
                root.get(criteria.getKey()), (Comparable) castedValue);
        } 
        
        else{
            if (type == String.class) {
                return builder.like(root.get(criteria.getKey()), "%" + castedValue + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), castedValue);
            }
        }
    }
}