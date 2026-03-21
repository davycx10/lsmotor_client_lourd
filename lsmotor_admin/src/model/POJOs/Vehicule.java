package model.POJOs;

/**
 * ============================================================
 *  POJO : Vehicule
 * ============================================================
 *  Représente UNE ligne de la table `vehicule` en mémoire.
 *
 *  CORRESPONDANCE EXACTE AVEC TA TABLE SQL :
 *
 *    Colonne SQL       Type SQL         →   Attribut Java
 *    ──────────────────────────────────────────────────────
 *    ID                INT              →   int     id
 *    NomModele         VARCHAR(150)     →   String  nomModele
 *    ID_Marque         INT (FK)         →   int     idMarque
 *    ID_Categorie      INT (FK)         →   int     idCategorie
 *    PrixCatalogue     DECIMAL(10,2)    →   double  prixCatalogue
 *    Description       TEXT             →   String  description
 *    Image             VARCHAR(255)     →   String  image
 *    Actif             TINYINT(1)       →   boolean actif
 //*
 *  ATTRIBUTS SUPPLÉMENTAIRES (pas de colonne SQL directe) :
 //*
 *    Ces deux attributs n'existent PAS dans la table vehicule.
 *    Ils sont remplis grâce à une jointure SQL (JOIN).
 *    Ils servent uniquement à l'affichage dans le tableau.
 //*
 *    nomMarque     →   String  nomMarque
 *    nomCategorie  →   String  nomCategorie
 //*
 *    EXEMPLE de requête qui les remplit (dans VehiculeModel) :
 *      SELECT v.*, m.Nom as nomMarque, c.Libelle as nomCategorie
 *      FROM vehicule v
 *      JOIN marque    m ON v.ID_Marque    = m.ID
 *      JOIN categorie c ON v.ID_Categorie = c.ID
 *
 *  POINT IMPORTANT sur le type boolean/TINYINT :
 *
 *    En SQL :  Actif = 1   →   En Java : actif = true
 *    En SQL :  Actif = 0   →   En Java : actif = false
 *
 *    Dans le ResultSet (VehiculeModel) tu feras :
 *      boolean actif = rs.getInt("Actif") == 1;
 *
 *    Dans la requête INSERT/UPDATE tu feras :
 *      int actifSQL = vehicule.isActif() ? 1 : 0;
 *
 *  CE QUE TU DOIS ÉCRIRE :
 *
 *  1. Les 10 attributs privés :
 *     → Les 8 de la table SQL
 *     → + nomMarque  (String)
 *     → + nomCategorie (String)
 *
 *  2. Un constructeur VIDE
 *     → public Vehicule() {}
 *
 *  3. Un constructeur COMPLET (les 10 attributs)
 *     → Utilisé dans VehiculeModel.getAll()
 *       quand on lit une ligne avec JOIN
 *
 *  4. Les GETTERS pour chaque attribut :
 *     → Attention : pour actif le getter se nomme isActif()
 *       et non getActif() → convention Java pour les boolean
 *     → public boolean isActif() { return actif; }
 *     → Pour tous les autres → public String getNomModele() etc.
 //*
 *  5. Les SETTERS pour chaque attribut
 *     → public void setActif(boolean actif) { this.actif = actif; }
 *     → etc...
 //*
 *  6. Un toString() utile pour débugger
 *     → Affiche au minimum : id, nomModele, prixCatalogue
 * ============================================================
 */
public class Vehicule {

    // Étape 1 : écris tes 10 attributs privés ici
    // (8 colonnes SQL + nomMarque + nomCategorie)
    private int id;
    private String nomModele;
    private int idMarque;
    private int idCategorie;
    private double prixCatalogue;
    private String description;
    private String image;
    private boolean actif;
    private String nomMarque;
    private String nomCategorie;

    // Étape 2 : constructeur vide
    public Vehicule(){}

    // Étape 3 : constructeur complet (10 paramètres)
    public Vehicule(
            int id,
            String nomModele,
            int idMarque,
            int idCategorie,
            double prixCatalogue,
            String description,
            String image,
            boolean actif,
            String nomMarque,
            String nomCategorie
    ){
        this.id            = id;
        this.nomModele     = nomModele;
        this.idMarque      = idMarque;
        this.idCategorie   = idCategorie;
        this.prixCatalogue = prixCatalogue;
        this.description   = description;
        this.image         = image;
        this.actif         = actif;
        this.nomMarque     = nomMarque;
        this.nomCategorie  = nomCategorie;
    }

    // Étape 4 : getters

    public int getId() {
        return id;
    }

    public String getNomModele() {
        return nomModele;
    }

    public int getIdMarque() {
        return idMarque;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public double getPrixCatalogue() {
        return prixCatalogue;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public boolean isActif() {
        return actif;
    }

    public String getNomMarque() {
        return nomMarque;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    // Étape 5 : setters

    public void setId(int id) {
        this.id = id;
    }

    public void setNomModele(String nomModele) {
        this.nomModele = nomModele;
    }

    public void setIdMarque(int idMarque) {
        this.idMarque = idMarque;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public void setPrixCatalogue(double prixCatalogue) {
        this.prixCatalogue = prixCatalogue;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public void setNomMarque(String nomMarque) {
        this.nomMarque = nomMarque;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    // Étape 6 : toString()

    @Override
    public String toString() {
        return "Vehicule{" +
                "id=" + id +
                ", nomModele='" + nomModele + '\'' +
                ", idMarque=" + idMarque +
                ", idCategorie=" + idCategorie +
                ", prixCatalogue=" + prixCatalogue +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", actif=" + actif +
                ", nomMarque='" + nomMarque + '\'' +
                ", nomCategorie='" + nomCategorie + '\'' +
                '}';
    }
}