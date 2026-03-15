package model;

/**
 * ============================================================
 *  POJO : Utilisateur
 * ============================================================
 *  Représente UNE ligne de la table `utilisateur` en mémoire.
 *
 *  CORRESPONDANCE EXACTE AVEC TA TABLE SQL :
 *
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
 *
 *  ⚠️  Le champ s'appelle "passwrd" (pas "motDePasse")
 *      car c'est le nom exact dans ta BDD.
 *
 *  CE QUE TU DOIS ÉCRIRE :
 *
 *  1. Les 8 attributs privés listés ci-dessus
 *     → private int id;
 *     → private String nom;
 *     → etc...
 *
 *  2. Un constructeur VIDE (sans paramètres)
 *     → public Utilisateur() {}
 *     → Nécessaire quand on crée un objet vide avant de
 *       le remplir champ par champ avec les setters
 *
 *  3. Un constructeur COMPLET (tous les paramètres)
 *     → public Utilisateur(int id, String nom, ...)
 *     → Utilisé dans les Model quand on lit une ligne SQL
 *       et qu'on veut créer l'objet d'un coup
 *
 *  4. Un GETTER par attribut (retourne la valeur)
 *     → public int getId()          { return id; }
 *     → public String getNom()      { return nom; }
 *     → etc... un par un pour chaque attribut
 *
 *  5. Un SETTER par attribut (modifie la valeur)
 *     → public void setId(int id)        { this.id = id; }
 *     → public void setNom(String nom)   { this.nom = nom; }
 *     → etc... un par un pour chaque attribut
 *
 *  6. Un toString() pour débugger
 *     → public String toString() {
 *            return "Utilisateur{id=" + id + ", nom=" + nom + "}";
 *       }
 *     → Pratique pour faire System.out.println(unUtilisateur)
 * ============================================================
 */
public class Utilisateur {

    // Étape 1 : écris tes 8 attributs privés ici

    // Étape 2 : écris ton constructeur vide ici

    // Étape 3 : écris ton constructeur complet ici

    // Étape 4 : écris tes getters ici

    // Étape 5 : écris tes setters ici

    // Étape 6 : écris ton toString() ici
}