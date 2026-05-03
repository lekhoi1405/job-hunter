package Group.Artifact.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchCriteria {
    private String key;       
    private String operation; 
    private Object value;

    public static List<SearchCriteria> convertStringToCriteria(String filter){
        String[] criteria = filter.split(",");
        
        return Arrays.stream(criteria).map(String::trim)
                                .filter(s->!s.isEmpty())
                                .map(s -> { String[] strings = SearchCriteria.splitFilter(s);
                                            return SearchCriteria.builder()
                                                            .key(strings[0])
                                                            .operation(strings[1])
                                                            .value(strings[2])
                                                            .build();
                                })
                                .toList();
    }

    public static String[] splitFilter(String filterString){
        String[] operation ={":",">","<","="};
        for (String element : operation) {
            if(filterString.contains(element)){
                return filterString.split(element);
            }
        }
        return null;
    }
}
