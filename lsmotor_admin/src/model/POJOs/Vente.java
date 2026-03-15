package model;

/**
 * ============================================================
 *  POJO : Vente
 * ============================================================
 *  Représente UNE ligne de la table `vente` en mémoire.
 *
 *  CORRESPONDANCE EXACTE AVEC TA TABLE SQL :
 *
 *    Colonne SQL       Type SQL         →   Attribut Java
 *    ──────────────────────────────────────────────────────
 *    ID                INT              →   int    id
 *    ID_Employe        INT (FK)         →   int    idEmploye
 *    ID_Vehicule       INT (FK)         →   int    idVehicule
 *    NomClient         VARCHAR(150)     →   String nomClient
 *    DateVente         DATETIME         →   String dateVente
 *    PrixVente         DECIMAL(10,2)    →   double prixVente
 *
 *  ATTRIBUTS SUPPLÉMENTAIRES (pas de colonne SQL directe) :
 *
 *    Comme pour Vehicule, on ajoute des attributs remplis
 *    par des jointures SQL pour l'affichage dans le tableau.
 *
 *    nomEmploye  →  String  nomEmploye
 *    (rempli via JOIN utilisateur ON ID_Employe = utilisateur.ID)
 *
 *    nomVehicule →  String  nomVehicule
 *    (rempli via JOIN vehicule ON ID_Vehicule = vehicule.ID)
 *
 *    EXEMPLE de requête qui les remplit (dans VenteModel) :
 *      SELECT
 *        v.ID,
 *        v.ID_Employe,
 *        v.ID_Vehicule,
 *        v.NomClient,
 *        v.DateVente,
 *        v.PrixVente,
 *        CONCAT(u.Prenom, ' ', u.Nom) AS nomEmploye,
 *        vh.NomModele                 AS nomVehicule
 *      FROM vente v
 *      JOIN utilisateur u  ON v.ID_Employe  = u.ID
 *      JOIN vehicule    vh ON v.ID_Vehicule = vh.ID
 *      ORDER BY v.DateVente DESC
 *
 *  POINT IMPORTANT sur DateVente :
 *
 *    En SQL c'est un DATETIME.
 *    En Java on le stocke en String pour simplifier
 *    l'affichage dans le tableau.
 *
 *    Dans le ResultSet (VenteModel) tu feras :
 *      String date = rs.getString("DateVente");
 *    → MySQL retourne une String formatée directement.
 *
 *  POINT IMPORTANT sur PrixVente :
 *
 *    En SQL c'est un DECIMAL(10,2).
 *    En Java on utilise double.
 *
 *    Dans le ResultSet tu feras :
 *      double prix = rs.getDouble("PrixVente");
 *
 *  CE QUE TU DOIS ÉCRIRE :
 *
 *  1. Les 8 attributs privés :
 *     → Les 6 colonnes SQL :
 *        private int    id;
 *        private int    idEmploye;
 *        private int    idVehicule;
 *        private String nomClient;
 *        private String dateVente;
 *        private double prixVente;
 *     → Les 2 attributs de jointure :
 *        private String nomEmploye;
 *        private String nomVehicule;
 *
 *  2. Un constructeur VIDE
 *     → public Vente() {}
 *
 *  3. Un constructeur COMPLET (8 paramètres)
 *     → Dans l'ordre :
 *        (int id, int idEmploye, int idVehicule,
 *         String nomClient, String dateVente, double prixVente,
 *         String nomEmploye, String nomVehicule)
 *     → Utilisé dans VenteModel quand on lit une ligne SQL
 *
 *  4. Les GETTERS pour chaque attribut
 *     → public int    getId()          { return id;          }
 *     → public int    getIdEmploye()   { return idEmploye;   }
 *     → public int    getIdVehicule()  { return idVehicule;  }
 *     → public String getNomClient()   { return nomClient;   }
 *     → public String getDateVente()   { return dateVente;   }
 *     → public double getPrixVente()   { return prixVente;   }
 *     → public String getNomEmploye()  { return nomEmploye;  }
 *     → public String getNomVehicule() { return nomVehicule; }
 *
 *  5. Les SETTERS pour chaque attribut
 *     → Un setter par attribut, même principe que les getters
 *     → public void setId(int id)              { this.id = id; }
 *     → public void setPrixVente(double prix)  { this.prixVente = prix; }
 *     → etc...
 *
 *  6. Un toString() pour débugger
 *     → Affiche au minimum : id, nomClient, nomVehicule, prixVente
 *     → Ex : "Vente{id=1, client=Tony Montana,
 *                    vehicule=Zentorno, prix=1680000.0}"
 *
 *  RAPPEL : ce POJO n'a PAS de JComboBox associé
 *  donc le toString() peut être complet, pas besoin
 *  de retourner juste un champ comme Marque/Categorie.
 *
 * ============================================================
 */
public class Vente {

    // Étape 1 : 8 attributs privés
    // → 6 colonnes SQL + nomEmploye + nomVehicule

    // Étape 2 : constructeur vide

    // Étape 3 : constructeur complet (8 paramètres)
    // → Dans l'ordre : id, idEmploye, idVehicule,
    //   nomClient, dateVente, prixVente,
    //   nomEmploye, nomVehicule

    // Étape 4 : 8 getters
    // → getId(), getIdEmploye(), getIdVehicule(),
    //   getNomClient(), getDateVente(), getPrixVente(),
    //   getNomEmploye(), getNomVehicule()

    // Étape 5 : 8 setters
    // → setId(), setIdEmploye(), setIdVehicule(),
    //   setNomClient(), setDateVente(), setPrixVente(),
    //   setNomEmploye(), setNomVehicule()

    // Étape 6 : toString()
    // → Affiche id, nomClient, nomVehicule, prixVente
}