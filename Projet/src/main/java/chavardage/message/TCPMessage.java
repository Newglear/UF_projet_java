package chavardage.message;

import chavardage.AssignationProblemException;

import java.io.Serializable;

public class TCPMessage implements Serializable { // TODO ajouter date et heure

    private final int envoyeurId;
    private final TCPType type;
    private final String data;


    public TCPMessage(TCPType type) throws WrongConstructorException {
        if (type!=TCPType.FermetureSession) throw new WrongConstructorException();
        this.type=type;
        this.envoyeurId=-1;
        this.data="";
    }

    public TCPMessage(TCPType type, int envoyeurId) throws WrongConstructorException {
        if (type!=TCPType.OuvertureSession) throw new WrongConstructorException();
        this.type=type;
        this.envoyeurId=envoyeurId;
        this.data="";
    }

    public TCPMessage(String data){
        this.type=TCPType.UserData;
        this.envoyeurId=-1;
        this.data=data;
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
