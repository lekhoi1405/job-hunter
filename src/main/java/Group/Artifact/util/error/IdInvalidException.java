package Group.Artifact.util.error;

public class IdInvalidException extends RuntimeException {
    public IdInvalidException(String message){
        super(message);
    }
    public IdInvalidException(){
        super("id not found");
    }
}   
