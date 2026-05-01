package controller;

import model.BDD;
import model.POJOs.Categorie;
import model.CategorieModel;
import view.CategoriePanel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * ============================================================
 *  CONTROLLER : CategorieController
 * ============================================================
 *  Rôle : Gère toutes les interactions de l'onglet Catégories.
 *
 *  ⚠️ QUASI IDENTIQUE À MarqueController.
 *     Remplace partout :
 *       Marque      → Categorie
 *       MarqueModel → CategorieModel
 *       MarquePanel → CategoriePanel
 *       getNom()    → getLibelle()
 *       setNom()    → setLibelle()
 *       getValeurColonne(1) reste pareil (index 1 = libellé)
 *
 *  ATTRIBUTS À DÉCLARER :
 *  ──────────────────────
 *  → private CategoriePanel  vue;
 *  → private CategorieModel  modele;
 *
 *  CONSTRUCTEUR :
 *  ──────────────
 *  → public CategorieController(BDD db, CategoriePanel vue)
 *
 *  MÊME STRUCTURE QUE MarqueController :
 *  1. Stocker attributs
 *  2. Brancher btnAjouter   → ajouter()
 *  3. Brancher btnModifier  → modifier()
 *  4. Brancher btnSupprimer → supprimer()
 *  5. Brancher recherche    → rechercher()
 *  6. Brancher sélection    → remplirFormulaire()
 *  7. chargerTableau()
 *
 * ============================================================
 *  MÉTHODE : chargerTableau()
 * ============================================================
 *
 *  COMMENT FAIRE :
 *  1. List<Categorie> liste = modele.getAll();
 *
 *  2. Object[][] data = new Object[liste.size()][2];
 *     for (int i = 0; i < liste.size(); i++) {
 *         data[i][0] = liste.get(i).getId();
 *         data[i][1] = liste.get(i).getLibelle();
 *         ⚠️ getLibelle() et non getNom() !
 *     }
 *
 *  3. vue.majTableau(data);
 *
 *  ⚠️ Pas de setCompteur() ici car CategoriePanel
 *     n'a pas de lblCompteur (contrairement à MarquePanel).
 *
 * ============================================================
 *  MÉTHODE : rechercher()
 * ============================================================
 *
 *  COMMENT FAIRE :
 *  → Même logique que MarqueController.rechercher()
 *  → Utilise modele.rechercher(terme)
 *  → Convertit en Object[][] avec getLibelle()
 *
 * ============================================================
 *  MÉTHODE : ajouter()
 * ============================================================
 *
 *  COMMENT FAIRE :
 *  1. String libelle = vue.getLibelle();
 *     ⚠️ getLibelle() et non getNom() !
 *
 *  2. Valider non vide :
 *     if (libelle.isEmpty()) {
 *         vue.afficherErreur(
 *             "Le libellé ne peut pas être vide."
 *         );
 *         return;
 *     }
 *
 *  3. boolean ok = modele.ajouter(libelle);
 *
 *  4. Si ok → chargerTableau() + viderFormulaire()
 *     Sinon → vue.afficherErreur("Cette catégorie existe déjà.")
 *
 * ============================================================
 *  MÉTHODE : modifier()
 * ============================================================
 *
 *  COMMENT FAIRE :
 *  1. Vérifier sélection
 *  2. int id = (int) vue.getValeurColonne(0);
 *     String libelle = vue.getLibelle();
 *  3. Valider non vide
 *  4. boolean ok = modele.modifier(id, libelle);
 *  5. Si ok → chargerTableau() + viderFormulaire()
 *
 * ============================================================
 *  MÉTHODE : supprimer()
 * ============================================================
 *
 *  COMMENT FAIRE :
 *  1. Vérifier sélection
 *  2. int id = (int) vue.getValeurColonne(0);
 *
 *  3. Vérifier FK :
 *     if (modele.estUtilisee(id)) {
 *         vue.afficherErreur(
 *             "Impossible : cette catégorie est utilisée "
 *           + "par des véhicules."
 *         );
 *         return;
 *     }
 *
 *  4. Confirmation → modele.supprimer(id)
 *  5. Si ok → chargerTableau() + viderFormulaire()
 *
 * ============================================================
 *  MÉTHODE : remplirFormulaire()
 * ============================================================
 *
 *  COMMENT FAIRE :
 *  1. int row = vue.getLigneSelectionnee();
 *     if (row == -1) return;
 *  2. String libelle = (String) vue.getValeurColonne(1);
 *  3. vue.setLibelle(libelle);
 *     ⚠️ setLibelle() et non setNom() !
 *
 * ============================================================
 */
public class CategorieController {

    // Attributs : vue + modele
    private CategoriePanel vue;
    private CategorieModel modele;

    // Constructeur :
    // → Stocker attributs
    // → Brancher boutons + recherche + sélection
    // → chargerTableau()

    public CategorieController(BDD uneDb, CategoriePanel vue) {
        this.vue = vue;
        this.modele = new CategorieModel(uneDb);

        vue.getBtnAjouter()
                .addActionListener(e -> ajouter());
        vue.getBtnModifier()
                .addActionListener(e -> modifier());
        vue.getBtnSupprimer()
                .addActionListener(e -> supprimer());

        vue.getTable()
                .getSelectionModel()
                .addListSelectionListener(e ->{
                    if (!e.getValueIsAdjusting()){
                        remplirFormulaire();
                    }
                });

        vue.getChampRecherche()
                .addKeyListener(
                        new KeyAdapter() {
                            @Override
                            public void keyReleased(KeyEvent e) {
                                rechercher();
                            }
                        }
                );
        chargerTableau();
    }

    // chargerTableau()
    private void chargerTableau(){
       List<Categorie> liste = modele.getAll();

       Object[][] data = new Object[liste.size()][2];
       for (int i = 0; i < liste.size(); i++){
           data[i][0] = liste.get(i).getId();
           data[i][1] = liste.get(i).getLibelle();
       }
       vue.majTableau(data);
    }

    // rechercher()
    private void rechercher(){
        String terme =
                vue.getChampRecherche().getText().trim();

        List<Categorie> liste;
        if (terme.isEmpty()){
            liste = modele.getAll();
        } else {
            liste = modele.rechercher(terme);
        }
        Object[][] data = new Object[liste.size()][2];
        for (int i = 0; i < liste.size(); i++){
            data[i][0] = liste.get(i).getId();
            data[i][1] = liste.get(i).getLibelle();
        }
        vue.majTableau(data);
    }

    // ajouter()
    private void ajouter(){
        String libelle = vue.getLibelle();

        if (libelle.isEmpty()){
            vue.afficherErreur(
                    "Le libellé ne peut être vide"
            );
            return;
        }
        boolean ok = modele.ajouter(libelle);
        if (ok){
            chargerTableau();
            vue.viderFormulaire();
        } else {
            vue.afficherErreur(
                    "Cette catégorie existe déjà"
            );
        }
    }

    // modifier()
    private void modifier(){
        if (vue.getLigneSelectionnee() == -1){
            vue.afficherErreur(
                    "Selectionner une categorie à modifier"
            );
            return;
        }
        int id = (int) vue.getValeurColonne(0);
        String libelle = vue.getLibelle();
        if (libelle.isEmpty()){
            vue.afficherErreur(
                    "Le libellet ne peut être vide"
            );
            return;
        }
        boolean ok = modele.modifier(id, libelle);
        if (ok){
            chargerTableau();
            vue.viderFormulaire();
        } else {
            vue.afficherErreur("Erreur modification");
        }
    }
    // supprimer()
    private void supprimer(){
        if (vue.getLigneSelectionnee() == -1){
            vue.afficherErreur(
                    "Selectionner une categorie à supprimer"
            );
            return;
        }

        int id = (int) vue.getValeurColonne(0);
        if (modele.estUtilisee(id)){
            vue.afficherErreur(
                    "Impossible : cette catégorie est utilisée"
                    + "par des vehicules"
            );
            return;
        }
        int choix = JOptionPane.showConfirmDialog(
                vue,
                "Supprimer cette catégorie",
                "Confirmation",
                JOptionPane.YES_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (choix == JOptionPane.YES_OPTION){
            boolean ok = modele.supprimer(id);
            if (ok){
                chargerTableau();
                vue.viderFormulaire();
            } else {
                vue.afficherErreur("Erreur suppression");
            }
        }
    }
    // remplirFormulaire()
    private void remplirFormulaire(){
        int row = vue.getLigneSelectionnee();
        if (row == -1) return;
        String libelle = (String) vue.getValeurColonne(1);
        vue.setLibelle(libelle);
    }

}