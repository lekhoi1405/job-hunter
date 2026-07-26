package Group.Artifact.domain.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ResultPagination<T> {
    private T result;
    private Meta meta;
        @Builder
        @Setter
        @Getter
        public static class Meta {
            private int current;
            private int pageSize;
            private int pages;
            private long total;
        }
}
