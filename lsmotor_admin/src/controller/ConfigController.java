package controller;

import model.BDD;
import model.POJOs.Config;
import model.VenteModel;
import view.ConfigPanel;
import view.CategoriePanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * ============================================================
 *  CONTROLLER : ConfigController
 * ============================================================
 *  Rôle : Gère l'onglet Configuration (marge globale).
 *
 *  ⚠️ On utilise VenteModel ici car getMarge() et
 *     saveMarge() sont dans VenteModel (pas de ConfigModel
 *     séparé comme expliqué dans VenteModel.java).
 *
 *  ATTRIBUTS À DÉCLARER :
 *  ──────────────────────
 *  → private ConfigPanel  vue;
 *  → private VenteModel   modele;
 *
 *  CONSTRUCTEUR :
 *  ──────────────
 *  → public ConfigController(BDD db, ConfigPanel vue)
 *
 *  DANS LE CONSTRUCTEUR :
 *
 *  1. Stocker les attributs :
 *     this.vue    = vue;
 *     this.modele = new VenteModel(db);
 *
 *  2. Brancher le bouton Charger :
 *     vue.getBtnCharger()
 *        .addActionListener(e -> chargerMarge());
 *
 *  3. Brancher le bouton Enregistrer :
 *     vue.getBtnEnregistrer()
 *        .addActionListener(e -> sauvegarderMarge());
 *
 *  4. Effacer le statut quand on tape dans le champ :
 *     vue.getMargeTexte() → non, ce n'est pas un getter
 *     de champ mais de valeur. Pour écouter les frappes
 *     il faut exposer le champMarge dans ConfigPanel.
 *
 *     ⚠️ OPTION SIMPLE : ne pas effacer automatiquement.
 *        Le statut s'efface tout seul à la prochaine
 *        action (Charger ou Enregistrer).
 *
 *  5. Charger la marge au démarrage :
 *     chargerMarge();
 *
 * ============================================================
 *  MÉTHODE : chargerMarge()
 * ============================================================
 *  Signature : private void chargerMarge()
 *
 *  COMMENT FAIRE :
 *  1. Config config = modele.getMarge();
 *
 *  2. if (config != null) {
 *         vue.setMarge(config.getMargePourcent());
 *         vue.setDateMaj(config.getDateMaj());
 *         vue.effacerStatut();
 *     } else {
 *         vue.afficherStatut(
 *             "✕  Impossible de charger la configuration.",
 *             false
 *         );
 *     }
 *
 * ============================================================
 *  MÉTHODE : sauvegarderMarge()
 * ============================================================
 *  Signature : private void sauvegarderMarge()
 *
 *  COMMENT FAIRE :
 *  1. Récupérer la valeur saisie :
 *     String txt = vue.getMargeTexte();
 *
 *  2. Parser en double :
 *     double valeur;
 *     try {
 *         valeur = Double.parseDouble(txt);
 *     } catch (NumberFormatException e) {
 *         vue.afficherStatut(
 *             "✕  Entrez un nombre valide (ex: 40.00).",
 *             false
 *         );
 *         return;
 *     }
 *
 *  3. Valider la plage :
 *     if (valeur < 0 || valeur > 200) {
 *         vue.afficherStatut(
 *             "✕  La marge doit être entre 0 et 200.",
 *             false
 *         );
 *         return;
 *     }
 *
 *  4. Enregistrer :
 *     boolean ok = modele.saveMarge(valeur);
 *
 *  5a. Si ok :
 *      vue.afficherStatut(
 *          "✓  Marge enregistrée avec succès.",
 *          true
 *      );
 *      chargerMarge();
 *      ⚠️ On rappelle chargerMarge() pour afficher
 *         la nouvelle DateMaj mise à jour par MySQL
 *
 *  5b. Si pas ok :
 *      vue.afficherStatut(
 *          "✕  Erreur lors de l'enregistrement.",
 *          false
 *      );
 *
 * ============================================================
 */
public class ConfigController {

    // Attributs : vue + modele
    private ConfigPanel vue;
    private VenteModel modele;


    // Constructeur :
    // → Stocker attributs
    // → Brancher btnCharger     → chargerMarge()
    // → Brancher btnEnregistrer → sauvegarderMarge()
    // → chargerMarge()

    public ConfigController(BDD uneDb, ConfigPanel vue) {
        this.vue = vue;
        this.modele = new VenteModel(uneDb);

        vue.getBtnCharger()
                .addActionListener(e -> chargerMarge());
        vue.getBtnEnregistrer()
                .addActionListener(e -> sauvegarderMarge());
                
        changerMarge();

    }


    // chargerMarge()
    private void chargerMarge(){
        Config config = modele.getMarge();

        if (config != null){
            vue.setMarge(config.getMargePourcent());
            vue.setDateMaj(config.getDateMaj());
            vue.effacerStatut();
        } else {
            vue.afficherStatut(
                    "Impossible decharger la configuration",
                    false
            );
        }
    }
    // sauvegarderMarge()
    private void sauvegarderMarge(){
        String txt = vue.getMargeTexte();
        double valeur;
        try {
            valeur = Double.parseDouble(txt);
        } catch (Exception e) {
            vue.afficherStatut(
                    "Entrer un nombre valide (ex: 40.00",
                    false
            );
            return;
        }
        if (valeur < 0 ||valeur > 200){
            vue.afficherStatut(
                    "La marge doit être entre 0 et 200",
                    false
            );
            return;
        }
        boolean ok = modele.saveMarge(valeur);
        if (ok){
            vue.afficherStatut(
                    "Marge enregistée avec succès",
                    true
            );
            chargerMarge();
        } else {
            vue.afficherStatut(
                    "Errer lors de l'enregistrement",
                    false
            );
        }
    }
}