package controller;

import model.BDD;
import model.POJOs.Marque;
import model.MarqueModel;
import view.MarquePanel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * ============================================================
 *  CONTROLLER : MarqueController
 * ============================================================
 *  Rôle : Gère toutes les interactions de l'onglet Marques.
 *
 *  ATTRIBUTS À DÉCLARER :
 *  ──────────────────────
 *  → private MarquePanel  vue;
 *  → private MarqueModel  modele;
 *
 *  CONSTRUCTEUR :
 *  ──────────────
 *  → public MarqueController(BDD db, MarquePanel vue)
 *
 *  DANS LE CONSTRUCTEUR, FAIRE 6 CHOSES :
 *
 *  1. Stocker les attributs :
 *     this.vue    = vue;
 *     this.modele = new MarqueModel(db);
 *
 *  2. Brancher bouton Ajouter :
 *     vue.getBtnAjouter()
 *        .addActionListener(e -> ajouter());
 *
 *  3. Brancher bouton Modifier :
 *     vue.getBtnModifier()
 *        .addActionListener(e -> modifier());
 *
 *  4. Brancher bouton Supprimer :
 *     vue.getBtnSupprimer()
 *        .addActionListener(e -> supprimer());
 *
 *  5. Brancher la barre de recherche :
 *     vue.getChampRecherche().addKeyListener(
 *         new KeyAdapter() {
 *             public void keyReleased(KeyEvent e) {
 *                 rechercher();
 *             }
 *         }
 *     );
 *
 *  6. Brancher la sélection dans le tableau :
 *     vue.getTable()
 *        .getSelectionModel()
 *        .addListSelectionListener(e -> {
 *            if (!e.getValueIsAdjusting()) {
 *                remplirFormulaire();
 *            }
 *        });
 *
 *  7. Charger les données :
 *     chargerTableau();
 *
 * ============================================================
 *  MÉTHODE : chargerTableau()
 * ============================================================
 *  Signature : private void chargerTableau()
 *
 *  COMMENT FAIRE :
 *  1. List<Marque> liste = modele.getAll();
 *
 *  2. Convertir en Object[][] :
 *     Object[][] data = new Object[liste.size()][2];
 *     for (int i = 0; i < liste.size(); i++) {
 *         data[i][0] = liste.get(i).getId();
 *         data[i][1] = liste.get(i).getNom();
 *     }
 *
 *  3. vue.majTableau(data);
 *
 *  4. Mettre à jour le compteur :
 *     vue.setCompteur(liste.size());
 *
 * ============================================================
 *  MÉTHODE : rechercher()
 * ============================================================
 *  Signature : private void rechercher()
 *
 *  COMMENT FAIRE :
 *  1. String terme =
 *         vue.getChampRecherche().getText().trim();
 *
 *  2. List<Marque> liste;
 *     if (terme.isEmpty()) {
 *         liste = modele.getAll();
 *     } else {
 *         liste = modele.rechercher(terme);
 *     }
 *
 *  3. Même conversion Object[][] que chargerTableau()
 *  4. vue.majTableau(data);
 *  5. vue.setCompteur(liste.size());
 *
 * ============================================================
 *  MÉTHODE : ajouter()
 * ============================================================
 *  Signature : private void ajouter()
 *
 *  COMMENT FAIRE :
 *  1. Récupérer le nom :
 *     String nom = vue.getNom();
 *
 *  2. Valider non vide :
 *     if (nom.isEmpty()) {
 *         vue.afficherErreur(
 *             "Le nom de la marque ne peut pas être vide."
 *         );
 *         return;
 *     }
 *
 *  3. Appeler le modèle :
 *     boolean ok = modele.ajouter(nom);
 *
 *  4a. Si ok :
 *      chargerTableau();
 *      vue.viderFormulaire();
 *
 *  4b. Si pas ok :
 *      vue.afficherErreur(
 *          "Cette marque existe peut-être déjà."
 *      );
 *
 * ============================================================
 *  MÉTHODE : modifier()
 * ============================================================
 *  Signature : private void modifier()
 *
 *  COMMENT FAIRE :
 *  1. Vérifier sélection :
 *     if (vue.getLigneSelectionnee() == -1) {
 *         vue.afficherErreur(
 *             "Sélectionnez une marque à modifier."
 *         );
 *         return;
 *     }
 *
 *  2. Récupérer l'ID et le nouveau nom :
 *     int    id  = (int)    vue.getValeurColonne(0);
 *     String nom = vue.getNom();
 *
 *  3. Valider non vide :
 *     if (nom.isEmpty()) {
 *         vue.afficherErreur("Le nom ne peut pas être vide.");
 *         return;
 *     }
 *
 *  4. Appeler le modèle :
 *     boolean ok = modele.modifier(id, nom);
 *
 *  5. Si ok → chargerTableau() + viderFormulaire()
 *     Sinon → vue.afficherErreur("Erreur modification.")
 *
 * ============================================================
 *  MÉTHODE : supprimer()
 * ============================================================
 *  Signature : private void supprimer()
 *
 *  COMMENT FAIRE :
 *  1. Vérifier sélection → même que modifier()
 *
 *  2. Récupérer l'ID :
 *     int id = (int) vue.getValeurColonne(0);
 *
 *  3. Vérifier si utilisée par des véhicules :
 *     if (modele.estUtilisee(id)) {
 *         vue.afficherErreur(
 *             "Impossible de supprimer : cette marque "
 *           + "est utilisée par des véhicules."
 *         );
 *         return;
 *     }
 *
 *  4. Demander confirmation :
 *     int choix = JOptionPane.showConfirmDialog(
 *         vue,
 *         "Supprimer cette marque ?",
 *         "Confirmation",
 *         JOptionPane.YES_NO_OPTION,
 *         JOptionPane.WARNING_MESSAGE
 *     );
 *
 *  5. if (choix == JOptionPane.YES_OPTION) {
 *         boolean ok = modele.supprimer(id);
 *         if (ok) {
 *             chargerTableau();
 *             vue.viderFormulaire();
 *         } else {
 *             vue.afficherErreur("Erreur suppression.");
 *         }
 *     }
 *
 * ============================================================
 *  MÉTHODE : remplirFormulaire()
 * ============================================================
 *  Signature : private void remplirFormulaire()
 *
 *  COMMENT FAIRE :
 *  1. int row = vue.getLigneSelectionnee();
 *     if (row == -1) return;
 *
 *  2. String nom = (String) vue.getValeurColonne(1);
 *
 *  3. vue.setNom(nom);
 *
 * ============================================================
 */
public class MarqueController {

    // Attributs : vue + modele
    private MarquePanel vue;
    private MarqueModel modele;

    // Constructeur :
    // → Stocker attributs
    // → Brancher boutons + recherche + sélection
    // → chargerTableau()

    public MarqueController(BDD uneDb,MarquePanel vue) {
        this.vue = vue;
        this.modele = new MarqueModel(uneDb);

        vue.getBtnAjouter()
                .addActionListener(e -> ajouter());
        vue.getBtnModifier()
                .addActionListener(e -> modifier());
        vue.getBtnSupprimer()
                .addActionListener(e -> supprimer());
        vue.getChampRecherche()
                .addKeyListener(
                        new KeyAdapter() {
                            @Override
                            public void keyReleased(KeyEvent e) {
                                rechercher();
                            }
                        }
                );
        ChargerTableau();
    }

    // chargerTableau()
    private void ChargerTableau(){
        List<Marque> liste = modele.getAll();
        Object[][] data = new Object[liste.size()][2];
        for (int i = 0; i < liste.size(); i++){
            data[i][0] = liste.get(i).getId();
            data[i][1] = liste.get(i).getNom();
        }
        vue.majTableau(data);
        vue.setCompteur(liste.size());
    }
    // rechercher()
    private void rechercher(){
        String terme =
                vue.getChampRecherche().getText().trim();

        List<Marque> liste;
        if (terme.isEmpty()){
            liste = modele.getAll();
        } else{
            liste = modele.rechercher(terme);
        }
        Object[][] data = new Object[liste.size()][2];
        for (int i = 0; i < liste.size(); i++){
            data[i][0] = liste.get(i).getId();
            data[i][1] = liste.get(i).getNom();
        }
        vue.majTableau(data);
        vue.setCompteur(liste.size());
    }
    // ajouter()
    private void ajouter(){
        String nom = vue.getNom();
        if (nom.isEmpty()){
            vue.afficherErreur(
                    "Le nom de la marque ne peut être vide"
            );
            return;
        }
        boolean ok = modele.ajouter(nom);
        if (ok){
            ChargerTableau();
            vue.viderFormulaire();
        } else {
            vue.afficherErreur(
                    "Cette marqe existe déjà"
            );
        }
    }
    // modifier()
    private void modifier(){
        if (vue.getLigneSelectionnee() == -1){
            vue.afficherErreur("Selecionner une marque à modifier");
            return;
        }
        int id = (int) vue.getValeurColonne(0);
        String nom = vue.getNom();
        if (nom.isEmpty()){
            vue.afficherErreur(
                    "Le nom ne peut être vide"
            );
            return;
        }
        boolean ok = modele.modifier(id, nom);
        if (ok){
            ChargerTableau();
            vue.viderFormulaire();
        } else {
            vue.afficherErreur("Erreur modification");
        }
    }
    // supprimer()
    private void supprimer(){
        if (vue.getLigneSelectionnee() == -1){
            vue.afficherErreur(
                    "Selectionner une marque à supprimer"
            );
            return;
        }
        int id = (int) vue.getValeurColonne(0);

        if (modele.estUtilisee(id)){
            vue.afficherErreur(
                    "Impossible de supprimer: cette marque "
                    + "est utilisée par des véhicules. "
            );
            return;
        }
        int choix = JOptionPane.showConfirmDialog(
                vue,
                "Supprimer cette marque ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (choix == JOptionPane.YES_NO_OPTION){
            boolean ok = modele.supprimer(id);
            if (ok){
                ChargerTableau();
                vue.viderFormulaire();
            } else {
                vue.afficherErreur("Erreur suppression ");
            }
        }
    }

    // remplirFormulaire()
    private void remplirFormulaire(){
        int row = vue.getLigneSelectionnee();
        if (row == -1) return;
        String nom = (String) vue.getValeurColonne(1);
        vue.setNom(nom);
    }
}