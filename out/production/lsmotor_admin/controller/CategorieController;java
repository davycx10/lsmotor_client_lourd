package controller;

import model.BDD;
import model.Categorie;
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

    // Constructeur :
    // → Stocker attributs
    // → Brancher boutons + recherche + sélection
    // → chargerTableau()

    // chargerTableau()
    // rechercher()
    // ajouter()
    // modifier()
    // supprimer()
    // remplirFormulaire()
}