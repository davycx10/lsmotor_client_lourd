package model.POJOs;

/**
 * ============================================================
 *  POJO : Categorie
 * ============================================================
 *  Représente UNE ligne de la table `categorie` en mémoire.
 *
 *  CORRESPONDANCE EXACTE AVEC TA TABLE SQL :
 *
 *    Colonne SQL       Type SQL         →   Attribut Java
 *    ──────────────────────────────────────────────────────
 *    ID                INT              →   int    id
 *    Libelle           VARCHAR(100)     →   String libelle
 *
 *  Quasi identique à Marque.java mais avec "libelle"
 *  à la place de "nom" — car c'est le nom de ta colonne SQL.
 *
 *  ⚠️  MÊME RÈGLE CRITIQUE QUE Marque.java — LE toString() :
 *
 *    Ce POJO est aussi utilisé dans un JComboBox
 *    dans VehiculePanel :
 *      comboCategorie.addItem(uneCategorie);
 *
 *    Swing appelle toString() pour afficher chaque item.
 *
 *    DONC ton toString() doit retourner UNIQUEMENT le libellé.
 *
 *    EXEMPLE de ce que verra l'utilisateur dans le combo :
 *      ┌─────────────────┐
 *      │ Sport           │  ← toString() retourne "Sport"
 *      │ SUV             │  ← toString() retourne "SUV"
 *      │ Berline         │  ← toString() retourne "Berline"
 *      │ Moto            │  ← toString() retourne "Moto"
 *      └─────────────────┘
 *
 *  DIFFÉRENCE AVEC Marque.java :
 *
 *    Marque    → colonne "Nom"     → attribut "nom"
 *    Categorie → colonne "Libelle" → attribut "libelle"
 *
 *    Donc ici les getters/setters utilisent "libelle" :
 *      getLibelle() au lieu de getNom()
 *      setLibelle() au lieu de setNom()
 *
 *  CE QUE TU DOIS ÉCRIRE :
 *
 *  1. Les 2 attributs privés :
 *     → private int    id;
 *     → private String libelle;
 *     ⚠️ "libelle" et non "nom" — correspond à ta colonne SQL
 *
 *  2. Un constructeur VIDE
 *     → public Categorie() {}
 *
 *  3. Un constructeur avec 2 paramètres
 *     → public Categorie(int id, String libelle)
 *     → Utilisé dans CategorieModel.getAll() :
 *        while (rs.next()) {
 *            liste.add(new Categorie(
 *                rs.getInt("ID"),
 *                rs.getString("Libelle")
 *            ));
 *        }
 *
 *  4. Le getter getId()
 *     → public int getId() { return id; }
 *
 *  5. Le getter getLibelle()
 *     → public String getLibelle() { return libelle; }
 *     ⚠️ getLibelle() et non getNom() !
 *
 *  6. Le setter setId(int id)
 *     → public void setId(int id) { this.id = id; }
 *
 *  7. Le setter setLibelle(String libelle)
 *     → public void setLibelle(String libelle) {
 *            this.libelle = libelle;
 *       }
 *     ⚠️ setLibelle() et non setNom() !
 *
 *  8. Le toString() ← CRITIQUE pour le JComboBox
 *     → Doit retourner SEULEMENT this.libelle
 *     → Exactement comme Marque mais avec libelle
 *
 *  RAPPEL du lien avec VehiculePanel :
 *
 *    Dans VehiculeController tu feras :
 *      List<Categorie> cats = categorieModel.getAll();
 *      for (Categorie c : cats) {
 *          vehiculePanel.getComboCategorie().addItem(c);
 *          // Swing affiche c.toString() = c.getLibelle()
 *          // Donc l'utilisateur voit "Sport", "SUV", etc.
 *      }
 *
 *    Quand l'utilisateur sélectionne "Sport" et clique
 *    Ajouter, le controller fait :
 *      Object sel = vehiculePanel.getCategorieSelectionnee();
 *      Categorie cat = (Categorie) sel;
 *      int idCat = cat.getId(); // récupère l'ID pour le SQL
 *
 * ============================================================
 */
public class Categorie {

    // Étape 1 : 2 attributs privés
    // ⚠️ "libelle" et non "nom"
    private int id;
    private String libelle;

    // Étape 2 : constructeur vide
    public Categorie(){}

    // Étape 3 : constructeur (int id, String libelle)
    public Categorie(
            int id,
            String libelle
    ){
        this.id = id;
        this.libelle = libelle;
    }

    // Étape 4 : getter getId()

    public int getId() {
        return id;
    }


    // Étape 5 : getter getLibelle()
    public String getLibelle() {
        return libelle;
    }

    // Étape 6 : setter setId()

    public void setId(int id) {
        this.id = id;
    }

    // Étape 7 : setter setLibelle()

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


    // Étape 8 : toString() → return this.libelle;

    @Override
    public String toString() {
        return "Categorie{" +
                "libelle='" + libelle + '\'' +
                '}';
    }
}