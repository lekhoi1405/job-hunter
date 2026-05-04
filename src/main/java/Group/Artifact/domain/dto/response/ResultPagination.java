package Group.Artifact.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResultPagination<T> {
    private Meta meta;
    private T Result;
}
