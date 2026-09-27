package Group.Artifact.util.error;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message){
        super(message); 
    }

    public AlreadyExistsException(){
        super("Existed resource");
    }
}
