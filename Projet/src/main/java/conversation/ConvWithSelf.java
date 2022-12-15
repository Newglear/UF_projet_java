package conversation;

public class ConvWithSelf extends Exception {

    private final String mess;

    public ConvWithSelf(String s) {
        this.mess=s;
    }

    public String getMessage(){
        return mess;
    }
}
