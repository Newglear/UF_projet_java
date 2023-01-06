package chavardage.message;

public enum UDPType {
    DemandeConnexion, /* broadcast*/
    AlreadyConnected, /*unicast, si deux users ont envoyé des demandes de connexion en même temps ça peut faire des couacs*/
    AckPseudoOk, /* unicast*/
    AckPseudoPasOK, /* unicast*/
    NewUser, /* broadcast*/
    ChangementPseudo, /* broadcast*/
    Deconnexion /* broadcast*/
}
