package Group.Artifact.domain;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.data.jpa.domain.Specification;

import Group.Artifact.domain.entity.Company;

public class CompanySpec {
    public static Specification<Company> hasName(String name){
        return (root,query,builder)->{
            if(name==null||name.isEmpty())return null;
                                                //name like '%keyword%'
            return builder.like(root.get("name"),"%"+name+"%");
        };
    }

    public static Specification<Company> createAtLessThanEq(){
        return(root,query,builder)->{
            Instant instant = Instant.now().minus(1, ChronoUnit.DAYS);
            return builder.lessThan(root.get("createdAt"),instant);
        };
    }
}
