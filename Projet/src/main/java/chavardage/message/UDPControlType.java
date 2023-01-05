package chavardage.message;

public enum UDPControlType {
    DemandeConnexion, /* broadcast*/
    AckPseudoOk, /* unicast*/
    AckPseudoPasOK, /* unicast*/
    NewUser, /* broadcast*/
    ChangementPseudo, /* broadcast*/
    Deconnexion /* broadcast*/
}
