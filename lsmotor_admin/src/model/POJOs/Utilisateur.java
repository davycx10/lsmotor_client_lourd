package model.POJOs;

/**
 * ============================================================
 *  POJO : Utilisateur
 * ============================================================
 *  Représente UNE ligne de la table `utilisateur` en mémoire.
 //*
 *  CORRESPONDANCE EXACTE AVEC TA TABLE SQL :
 //*
 *    Colonne SQL       Type SQL         →   Attribut Java
 *    ──────────────────────────────────────────────────────
 *    ID                INT              →   int    id
 *    Nom               VARCHAR(50)      →   String nom
 *    Prenom            VARCHAR(50)      →   String prenom
 *    Email             VARCHAR(100)     →   String email
 *    Passwrd           VARCHAR(255)     →   String passwrd
 *    Role              ENUM             →   String role
 *    DiscordPseudo     VARCHAR(100)     →   String discordPseudo
 *    DateInscription   DATETIME         →   String dateInscription
 //*
 *  ⚠️  Le champ s'appelle "passwrd" (pas "motDePasse")
 *      car c'est le nom exact dans ta BDD.
 //*
 *  CE QUE TU DOIS ÉCRIRE :
 //*
 *  1. Les 8 attributs privés listés ci-dessus
 *     → private int id;
 *     → private String nom;
 *     → etc...
 //*
 *  2. Un constructeur VIDE (sans paramètres)
 *     → public Utilisateur() {}
 *     → Nécessaire quand on crée un objet vide avant de
 *       le remplir champ par champ avec les setters
 //*
 *  3. Un constructeur COMPLET (tous les paramètres)
 *     → public Utilisateur(int id, String nom, ...)
 *     → Utilisé dans les Model quand on lit une ligne SQL
 *       et qu'on veut créer l'objet d'un coup
 //*
 *  4. Un GETTER par attribut (retourne la valeur)
 *     → public int getId()          { return id; }
 *     → public String getNom()      { return nom; }
 *     → etc... un par un pour chaque attribut
 //*
 *  5. Un SETTER par attribut (modifie la valeur)
 *     → public void setId(int id)        { this.id = id; }
 *     → public void setNom(String nom)   { this.nom = nom; }
 *     → etc... un par un pour chaque attribut
 //*
 *  6. Un toString() pour débugger
 *     → public String toString() {
 *            return "Utilisateur{id=" + id + ", nom=" + nom + "}";
 *       }
 *     → Pratique pour faire System.out.println(unUtilisateur)
 * ============================================================
 */

public class Utilisateur {

    // Étape 1 : écris tes 8 attributs privés ici
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String passwrd;
    private String role;
    private String discordPseudo;
    private String dateInscription;

    // Étape 2 : écris ton constructeur vide ici
    public Utilisateur (){

    }

    // Étape 3 : écris ton constructeur complet ici
    public Utilisateur(
            int id,
            String nom,
            String prenom,
            String email,
            String passwrd,
            String role,
            String discordPseudo,
            String dateInscription
    ){
        this.id              =   id;
        this.nom             =   nom;
        this.prenom          =   prenom;
        this.email           =   email;
        this.passwrd         =   passwrd;
        this.role            =   role;
        this.discordPseudo   =   discordPseudo;
        this.dateInscription =   dateInscription;
    }

    // Étape 4 : écris tes getters ici

    public int getId() {

        return id;
    }

    public String getNom() {

        return nom;
    }

    public String getPrenom() {

        return prenom;
    }

    public String getEmail() {

        return email;
    }

    public String getPasswrd() {

        return passwrd;
    }

    public String getRole() {

        return role;
    }

    public String getDiscordPseudo() {

        return discordPseudo;
    }

    public String getDateInscription() {

        return dateInscription;
    }

    // Étape 5 : écris tes setters ici

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public void setPrenom(String prenom) {

        this.prenom = prenom;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }

    public void setRole(String role) {

        this.role = role;
    }

    public void setDiscordPseudo(String discordPseudo) {

        this.discordPseudo = discordPseudo;
    }

    public void setDateInscription(String dateInscription) {

        this.dateInscription = dateInscription;
    }

    // Étape 6 : écris ton toString() ici

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", passwrd='" + passwrd + '\'' +
                ", role='" + role + '\'' +
                ", discordPseudo='" + discordPseudo + '\'' +
                ", dateInscription='" + dateInscription + '\'' +
                '}';
    }
}