package Group.Artifact.domain.dto;

public class userDTO {
    public record CreateRequest(String name, String email, String password, int age, String gender, String address){}
}
