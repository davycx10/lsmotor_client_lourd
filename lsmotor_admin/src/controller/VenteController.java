package controller;

import model.BDD;
import model.VenteModel;
import view.VentePanel;

import java.util.List;

/**
 * ============================================================
 *  CONTROLLER : VenteController
 * ============================================================
 *  Rôle : Gère l'onglet Historique des ventes.
 *         Lecture seule → pas d'ajout/modification/suppression.
 *
 *  ATTRIBUTS À DÉCLARER :
 *  ──────────────────────
 *  → private VentePanel  vue;
 *  → private VenteModel  modele;
 *
 *  CONSTRUCTEUR :
 *  ──────────────
 *  → public VenteController(BDD db, VentePanel vue)
 *
 *  DANS LE CONSTRUCTEUR :
 *
 *  1. Stocker les attributs :
 *     this.vue    = vue;
 *     this.modele = new VenteModel(db);
 *
 *  2. Brancher le bouton Rafraîchir :
 *     vue.getBtnRafraichir()
 *        .addActionListener(e -> charger());
 *
 *  3. Charger les données au démarrage :
 *     charger();
 *
 * ============================================================
 *  MÉTHODE : charger()
 * ============================================================
 *  Signature : private void charger()
 *  Rôle : Recharge les deux tableaux + le total semaine.
 *
 *  COMMENT FAIRE :
 *
 *  1. Charger le tableau global :
 *     List<Object[]> global = modele.getAllGlobal();
 *     Object[][] dataGlobal =
 *         global.toArray(new Object[0][]);
 *     vue.majTableauGlobal(dataGlobal);
 *
 *  2. Charger le tableau par employé :
 *     List<Object[]> employe = modele.getAllParEmploye();
 *     Object[][] dataEmploye =
 *         employe.toArray(new Object[0][]);
 *     vue.majTableauEmploye(dataEmploye);
 *
 *  3. Mettre à jour le total semaine :
 *     double total = modele.getTotalSemaineCourante();
 *     vue.setTotalSemaine(
 *         String.format("%,.0f", total)
 *     );
 *     ⚠️ String.format("%,.0f", 4200000.0)
 *        → "4,200,000" (formatage avec séparateur)
 *        Tu peux adapter le format selon ta préférence
 *
 *  4. Mettre à jour le nombre de ventes :
 *     int nb = modele.getNbVentesSemaineCourante();
 *     vue.setNbVentes(nb);
 *
 * ============================================================
 */
public class VenteController {

    // Attributs : vue + modele
    private VentePanel vue;
    private VenteModel modele;

    // Constructeur :
    // → Stocker attributs
    // → Brancher btnRafraichir → charger()
    // → charger()

    public VenteController(BDD uneDb, VentePanel vue) {
        this.vue = vue;
        this.modele =new VenteModel(uneDb);

        vue.getBtnRafraichir()
                .addActionListener(e -> charger());
        charger();
    }

    // charger()
    private void charger(){
        List<Object[]> employe = modele.getAllParEmploye();
        Object[][] dataEmploye =
                employe.toArray(new Object[0][]);
        vue.majTableauEmploye(dataEmploye);

        double total = modele.getTotalSemaineCourante();
        vue.setTotalSemaine(
                String.format("%, .0f", total)
        );

        int nb = modele.getNbVentesSemaineCourante();
        vue.setNbVentes(nb);
    }
}