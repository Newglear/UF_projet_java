package chavardage.message;

import chavardage.AssignationProblemException;

import java.io.Serializable;

public class TCPMessage implements Serializable {

    private final int destinataireId;
    private final int envoyeurId;
    private final TCPType type;
    private final String data;


    public TCPMessage(int destinataireId, TCPType type) throws WrongConstructorException {
        if (type!=TCPType.FermetureSession) throw new WrongConstructorException();
        this.destinataireId=destinataireId;
        this.type=type;
        this.envoyeurId=-1;
        this.data="";
    }

    public TCPMessage(int destinataireId, TCPType type, int envoyeurId) throws WrongConstructorException {
        if (type!=TCPType.OuvertureSession) throw new WrongConstructorException();
        this.destinataireId=destinataireId;
        this.type=type;
        this.envoyeurId=envoyeurId;
        this.data="";
    }

    public TCPMessage(int destinataireId, String data){
        this.destinataireId=destinataireId;
        this.type=TCPType.UserData;
        this.envoyeurId=-1;
        this.data=data;
    }

    public int getDestinataireId(){
        return this.destinataireId;
    }

    public String getData(){
        return this.data;
    }

    public TCPType getType() {
        return type;
    }

    public int getEnvoyeurId() throws AssignationProblemException {
        if (this.envoyeurId==-1) throw new AssignationProblemException("TCPMessage", "envoyeurId");
        return envoyeurId;
    }
}
