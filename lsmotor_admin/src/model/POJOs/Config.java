package model.POJOs;

/**
 * ============================================================
 *  POJO : Config
 * ============================================================
 *  Représente LA ligne unique de la table `config` en mémoire.
 *
 *  CORRESPONDANCE EXACTE AVEC TA TABLE SQL :
 *
 *    Colonne SQL       Type SQL         →   Attribut Java
 *    ──────────────────────────────────────────────────────
 *    ID                INT              →   int    id
 *    MargePourcent     DECIMAL(5,2)     →   double margePourcent
 *    DateMaj           DATETIME         →   String dateMaj
 *
 *  PARTICULARITÉ IMPORTANTE :
 *
 *    Cette table ne contient QU'UNE SEULE ligne (ID = 1).
 *    Elle a été créée par ton script SQL avec :
 *      INSERT IGNORE INTO config (ID, MargePourcent)
 *      VALUES (1, 40.00);
 *
 *    Donc dans ConfigModel tu ne feras JAMAIS de INSERT.
 *    Seulement :
 *      → Un SELECT pour lire la marge
 *      → Un UPDATE pour modifier la marge
 *
 *  POINT IMPORTANT sur MargePourcent :
 *
 *    En SQL c'est un DECIMAL(5,2).
 *    → Stocke jusqu'à 999.99
 *    → Ex : 40.00 = marge de 40%
 *    En Java on utilise double.
 *
 *    Dans le ResultSet (ConfigModel) tu feras :
 *      double marge = rs.getDouble("MargePourcent");
 *
 *  POINT IMPORTANT sur DateMaj :
 *
 *    Cette colonne est mise à jour AUTOMATIQUEMENT par MySQL
 *    grâce à : ON UPDATE CURRENT_TIMESTAMP
 *    Tu n'as JAMAIS à la modifier toi-même en Java.
 *    Tu la lis juste pour l'afficher dans ConfigPanel.
 *
 *    Dans le ResultSet tu feras :
 *      String date = rs.getString("DateMaj");
 *
 *  COMMENT CE POJO EST UTILISÉ :
 *
 *    Dans ConfigModel.getMarge() :
 *      → Tu fais SELECT * FROM config WHERE ID = 1
 *      → Tu crées un objet Config avec les valeurs lues
 *      → Tu le retournes au ConfigController
 *
 *    Dans ConfigController :
 *      → Il reçoit l'objet Config
 *      → Il appelle config.getMargePourcent()
 *        pour mettre à jour ConfigPanel
 *      → Il appelle config.getDateMaj()
 *        pour afficher la date de modification
 *
 *    Dans ConfigModel.saveMarge(double marge) :
 *      → Tu n'as PAS besoin de ce POJO en paramètre
 *      → Tu passes juste le double directement
 *      → UPDATE config SET MargePourcent = ? WHERE ID = 1
 *
 *  CE QUE TU DOIS ÉCRIRE :
 *
 *  1. Les 3 attributs privés :
 *     → private int    id;
 *        (toujours 1, mais on le stocke quand même)
 *     → private double margePourcent;
 *        (ex : 40.0)
 *     → private String dateMaj;
 *        (ex : "2025-03-15 14:32:00")
 *
 *  2. Un constructeur VIDE
 *     → public Config() {}
 *
 *  3. Un constructeur COMPLET (3 paramètres)
 *     → public Config(int id, double margePourcent,
 *                     String dateMaj)
 *     → Utilisé dans ConfigModel.getMarge() :
 *        return new Config(
 *            rs.getInt("ID"),
 *            rs.getDouble("MargePourcent"),
 *            rs.getString("DateMaj")
 *        );
 *
 *  4. Les GETTERS pour chaque attribut :
 *     → public int    getId()
 *        { return id; }
 *     → public double getMargePourcent()
 *        { return margePourcent; }
 *     → public String getDateMaj()
 *        { return dateMaj; }
 *
 *  5. Les SETTERS pour chaque attribut :
 *     → public void setId(int id)
 *        { this.id = id; }
 *     → public void setMargePourcent(double marge)
 *        { this.margePourcent = marge; }
 *     → public void setDateMaj(String dateMaj)
 *        { this.dateMaj = dateMaj; }
 *
 *  6. Un toString() pour débugger
 *     → Affiche : id, margePourcent, dateMaj
 *     → Ex : "Config{id=1, marge=40.0%, dateMaj=2025-03-15}"
 *
 *  RÉSUMÉ DE LA LOGIQUE GLOBALE :
 *
 *    ConfigPanel  ←→  ConfigController  ←→  ConfigModel
 *         ↑                                      ↑
 *    (la vue,                              (les requêtes
 *     champs +                              SQL SELECT
 *     boutons)                              et UPDATE)
 *                          ↑
 *                    (fait le lien,
 *                     utilise Config
 *                     pour transporter
 *                     les données)
 *
 * ============================================================
 */
public class Config {

    // Étape 1 : 3 attributs privés
    // → private int    id;
    // → private double margePourcent;
    // → private String dateMaj;

    // Étape 2 : constructeur vide

    // Étape 3 : constructeur complet
    // → (int id, double margePourcent, String dateMaj)

    // Étape 4 : 3 getters
    // → getId()
    // → getMargePourcent()
    // → getDateMaj()

    // Étape 5 : 3 setters
    // → setId(int id)
    // → setMargePourcent(double marge)
    // → setDateMaj(String dateMaj)

    // Étape 6 : toString()
    // → Affiche id, margePourcent, dateMaj
}