package model.POJOs;

/**
 * ============================================================
 *  POJO : Marque
 * ============================================================
 *  Représente UNE ligne de la table `marque` en mémoire.
 *
 *  CORRESPONDANCE EXACTE AVEC TA TABLE SQL :
 *
 *    Colonne SQL       Type SQL         →   Attribut Java
 *    ──────────────────────────────────────────────────────
 *    ID                INT              →   int    id
 *    Nom               VARCHAR(100)     →   String nom
 *
 *  C'est le POJO le plus simple : seulement 2 attributs.
 *
 *  ⚠️  POINT CRITIQUE — LE toString() :
 *
 *    Ce POJO est utilisé directement dans un JComboBox
 *    dans VehiculePanel :
 *      comboMarque.addItem(uneMarque);
 *
 *    Quand Swing affiche les items d'un JComboBox,
 *    il appelle automatiquement toString() sur chaque objet.
 *
 *    DONC si ton toString() retourne :
 *      "Marque{id=1, nom=Pegassi}"   ← mauvais, affiché tel quel
 *
 *    Le combo affichera : "Marque{id=1, nom=Pegassi}" 😱
 *
 *    ALORS QUE si ton toString() retourne :
 *      "Pegassi"                     ← correct
 *
 *    Le combo affichera : "Pegassi" ✓
 *
 *    RÈGLE : toString() doit retourner UNIQUEMENT le nom.
 *      → public String toString() { return this.nom; }
 *
 *  CE QUE TU DOIS ÉCRIRE :
 *
 *  1. Les 2 attributs privés :
 *     → private int    id;
 *     → private String nom;
 *
 *  2. Un constructeur VIDE
 *     → public Marque() {}
 *
 *  3. Un constructeur avec 2 paramètres
 *     → public Marque(int id, String nom)
 *     → Utilisé dans MarqueModel.getAll() :
 *        while (rs.next()) {
 *            liste.add(new Marque(
 *                rs.getInt("ID"),
 *                rs.getString("Nom")
 *            ));
 *        }
 *
 *  4. Le getter getId()
 *     → public int getId() { return id; }
 *
 *  5. Le getter getNom()
 *     → public String getNom() { return nom; }
 *
 *  6. Le setter setId(int id)
 *     → public void setId(int id) { this.id = id; }
 *
 *  7. Le setter setNom(String nom)
 *     → public void setNom(String nom) { this.nom = nom; }
 *
 *  8. Le toString() ← LE PLUS IMPORTANT ICI
 *     → Doit retourner SEULEMENT this.nom
 *     → Pas d'accolades, pas de "id=", juste le nom
 *     → C'est ce qui s'affiche dans le JComboBox
 *
 * ============================================================
 */
public class Marque {

    // Étape 1 : 2 attributs privés (id et nom)
    private int id;
    private String nom;

    // Étape 2 : constructeur vide
    public Marque(){

    }

    // Étape 3 : constructeur (int id, String nom)
    public Marque(
            int id,
            String nom
    ){
        this.id = id;
        this.nom = nom;
    }

    // Étape 4 : getter getId()
    public int getId() {
        return id;
    }


    // Étape 5 : getter getNom()
    public String getNom() {
        return nom;
    }

    // Étape 6 : setter setId()

    public void setId(int id) {
        this.id = id;
    }

    // Étape 7 : setter setNom()

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Étape 8 : toString() → return this.nom;
    // ⚠️ Seulement "return this.nom;" rien d'autre !


    @Override
    public String toString() {
        return "Marque{" +
                "nom='" + nom + '\'' +
                '}';
    }
}