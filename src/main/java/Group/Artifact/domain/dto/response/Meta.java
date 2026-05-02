package Group.Artifact.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Meta {
    private int current;
    private int pageSize;
    private int pages;
    private long total;
}
