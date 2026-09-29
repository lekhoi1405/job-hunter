package Group.Artifact.util.error.ExceptionCustom;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message){
        super(message); 
    }

    public AlreadyExistsException(){
        super("Existed resource");
    }
}
