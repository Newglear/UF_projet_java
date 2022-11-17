public abstract class UDPMessage implements Message {


    int destinataireId;
    ControlType controlType;

    public UDPMessage(int destinataireId, ControlType controlType){
        this.controlType=controlType;
        switch (controlType) {
            case UDPNewUser, UDPChangePseudo, UDPQuitNetwork -> this.destinataireId = 0; //on est en broadcast et j'ai décidé que ça serait 0
            default -> // on est pas en broadcast
                    this.destinataireId = destinataireId;
        }
    }





}
