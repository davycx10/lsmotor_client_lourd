package controller;

import model.*;
import model.POJOs.Categorie;
import model.POJOs.Marque;
import model.POJOs.Vehicule;
import view.VehiculePanel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * ============================================================
 *  CONTROLLER : VehiculeController
 * ============================================================
 *  Rôle : Gère l'onglet Véhicules.
 *         Le plus complexe car il gère aussi les combos
 *         marque et catégorie.
 *
 *  ATTRIBUTS À DÉCLARER :
 *  ──────────────────────
 *  → private VehiculePanel   vue;
 *  → private VehiculeModel   modele;
 *  → private MarqueModel     marqueModele;
 *  → private CategorieModel  categorieModele;
 *  ⚠️ On a besoin de 3 modèles différents ici car
 *     on doit charger les marques ET les catégories
 *     pour peupler les JComboBox
 *
 *  CONSTRUCTEUR :
 *  ──────────────
 *  → public VehiculeController(BDD db, VehiculePanel vue)
 *
 *  DANS LE CONSTRUCTEUR, FAIRE :
 *
 *  1. Stocker les attributs :
 *     this.vue              = vue;
 *     this.modele           = new VehiculeModel(db);
 *     this.marqueModele     = new MarqueModel(db);
 *     this.categorieModele  = new CategorieModel(db);
 *
 *  2. Peupler les combos EN PREMIER :
 *     chargerMarques();
 *     chargerCategories();
 *     ⚠️ AVANT chargerTableau() car le tableau
 *        a besoin des combos pour remplirFormulaire()
 *
 *  3. Brancher les boutons :
 *     vue.getBtnAjouter()  → ajouter()
 *     vue.getBtnModifier() → modifier()
 *     vue.getBtnSupprimer()→ supprimer()
 *
 *  4. Brancher la recherche :
 *     vue.getChampRecherche() → KeyAdapter → rechercher()
 *
 *  5. Brancher la sélection tableau :
 *     vue.getTable().getSelectionModel()
 *        → ListSelectionListener → remplirFormulaire()
 *
 *  6. Charger les données :
 *     chargerTableau();
 *
 * ============================================================
 *  MÉTHODE : chargerMarques()
 * ============================================================
 *  Signature : private void chargerMarques()
 *
 *  COMMENT FAIRE :
 *  1. Vider le combo d'abord :
 *     vue.getComboMarque().removeAllItems();
 *
 *  2. List<Marque> marques = marqueModele.getAll();
 *
 *  3. for (Marque m : marques) {
 *         vue.getComboMarque().addItem(m);
 *         // Swing affiche m.toString() = m.getNom()
 *     }
 *
 * ============================================================
 *  MÉTHODE : chargerCategories()
 * ============================================================
 *  Signature : private void chargerCategories()
 *
 *  COMMENT FAIRE :
 *  → Même principe que chargerMarques() :
 *  1. vue.getComboCategorie().removeAllItems();
 *  2. List<Categorie> cats = categorieModele.getAll();
 *  3. for (Categorie c : cats) {
 *         vue.getComboCategorie().addItem(c);
 *     }
 *
 * ============================================================
 *  MÉTHODE : chargerTableau()
 * ============================================================
 *  Signature : private void chargerTableau()
 *
 *  COMMENT FAIRE :
 *  1. List<Vehicule> liste = modele.getAll();
 *
 *  2. Object[][] data = new Object[liste.size()][6];
 *     for (int i = 0; i < liste.size(); i++) {
 *         Vehicule v = liste.get(i);
 *         data[i][0] = v.getId();
 *         data[i][1] = v.getNomModele();
 *         data[i][2] = v.getPrixCatalogue();
 *         data[i][3] = v.getNomMarque();
 *         data[i][4] = v.getNomCategorie();
 *         data[i][5] = v.isActif() ? "Oui" : "Non";
 *     }
 *
 *  3. vue.majTableau(data);
 *
 * ============================================================
 *  MÉTHODE : rechercher()
 * ============================================================
 *  → Même logique que les autres controllers
 *  → modele.rechercher(terme) ou modele.getAll()
 *  → Même conversion Object[][] que chargerTableau()
 *
 * ============================================================
 *  MÉTHODE : ajouter()
 * ============================================================
 *  Signature : private void ajouter()
 *
 *  COMMENT FAIRE :
 *  1. Récupérer les valeurs :
 *     String nom    = vue.getNom();
 *     String prixTxt= vue.getPrix();
 *     String image  = vue.getImage();
 *     String desc   = vue.getDescription();
 *     boolean actif = vue.isActif();
 *
 *  2. Récupérer marque et catégorie sélectionnées :
 *     Object selMarque = vue.getMarqueSelectionnee();
 *     Object selCat    = vue.getCategorieSelectionnee();
 *
 *  3. Valider :
 *     if (nom.isEmpty() || prixTxt.isEmpty()
 *             || selMarque == null || selCat == null) {
 *         vue.afficherErreur(
 *             "Nom, prix, marque et catégorie "
 *           + "sont obligatoires."
 *         );
 *         return;
 *     }
 *
 *  4. Parser le prix :
 *     double prix;
 *     try {
 *         prix = Double.parseDouble(prixTxt);
 *     } catch (NumberFormatException e) {
 *         vue.afficherErreur(
 *             "Le prix doit être un nombre valide."
 *         );
 *         return;
 *     }
 *
 *  5. Caster les objets combo :
 *     Marque    marque = (Marque)    selMarque;
 *     Categorie cat    = (Categorie) selCat;
 *
 *  6. Créer le Vehicule :
 *     Vehicule v = new Vehicule();
 *     v.setNomModele(nom);
 *     v.setPrixCatalogue(prix);
 *     v.setIdMarque(marque.getId());
 *     v.setIdCategorie(cat.getId());
 *     v.setImage(image);
 *     v.setDescription(desc);
 *     v.setActif(actif);
 *
 *  7. boolean ok = modele.ajouter(v);
 *
 *  8. Si ok → chargerTableau() + viderFormulaire()
 *     Sinon → vue.afficherErreur("Erreur ajout.")
 *
 * ============================================================
 *  MÉTHODE : modifier()
 * ============================================================
 *
 *  COMMENT FAIRE :
 *  → Même que ajouter() mais :
 *  1. Vérifier sélection d'abord
 *  2. Récupérer l'ID : int id = (int) vue.getValeurColonne(0)
 *  3. Créer le Vehicule avec l'ID en plus : v.setId(id)
 *  4. Appeler modele.modifier(v)
 *
 * ============================================================
 *  MÉTHODE : supprimer()
 * ============================================================
 *
 *  COMMENT FAIRE :
 *  1. Vérifier sélection
 *  2. int id = (int) vue.getValeurColonne(0)
 *
 *  3. Vérifier si des ventes existent :
 *     if (modele.estUtilise(id)) {
 *         vue.afficherErreur(
 *             "Impossible : ce véhicule a des ventes."
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
 *  Signature : private void remplirFormulaire()
 *
 *  C'est la méthode la plus délicate car il faut
 *  retrouver l'index des combos marque et catégorie.
 *
 *  COMMENT FAIRE :
 *  1. int row = vue.getLigneSelectionnee();
 *     if (row == -1) return;
 *
 *  2. Récupérer les valeurs de la ligne :
 *     String nom   = (String) vue.getValeurColonne(1);
 *     String prix  = String.valueOf(vue.getValeurColonne(2));
 *     ⚠️ Convertir le double en String pour le champ
 *
 *  3. Récupérer l'ID du vehicule pour chercher
 *     idMarque et idCategorie :
 *     int idVehicule = (int) vue.getValeurColonne(0);
 *
 *     → Tu dois faire un modele.getAll() et chercher
 *       le vehicule avec cet ID pour avoir idMarque
 *       et idCategorie :
 *
 *       List<Vehicule> liste = modele.getAll();
 *       Vehicule trouve = null;
 *       for (Vehicule v : liste) {
 *           if (v.getId() == idVehicule) {
 *               trouve = v;
 *               break;
 *           }
 *       }
 *       if (trouve == null) return;
 *
 *  4. Trouver l'index dans le combo marque :
 *     int idxMarque = -1;
 *     JComboBox<Object> comboM = vue.getComboMarque();
 *     for (int i = 0; i < comboM.getItemCount(); i++) {
 *         Marque m = (Marque) comboM.getItemAt(i);
 *         if (m.getId() == trouve.getIdMarque()) {
 *             idxMarque = i;
 *             break;
 *         }
 *     }
 *
 *  5. Même chose pour la catégorie :
 *     int idxCat = -1;
 *     JComboBox<Object> comboC = vue.getComboCategorie();
 *     for (int i = 0; i < comboC.getItemCount(); i++) {
 *         Categorie c = (Categorie) comboC.getItemAt(i);
 *         if (c.getId() == trouve.getIdCategorie()) {
 *             idxCat = i;
 *             break;
 *         }
 *     }
 *
 *  6. Appeler la méthode de la vue :
 *     vue.remplirFormulaire(
 *         trouve.getNomModele(),
 *         String.valueOf(trouve.getPrixCatalogue()),
 *         trouve.getImage(),
 *         trouve.getDescription(),
 *         trouve.isActif(),
 *         idxMarque,
 *         idxCat
 *     );
 *
 * ============================================================
 */
public class VehiculeController {

    // Attributs : vue + 3 modèles
    private VehiculePanel vue;
    private VehiculeModel modele;
    private MarqueModel marqueModel;
    private CategorieModel categorieModel;

    // Constructeur :

    public VehiculeController(BDD uneDb, VehiculePanel vue) {
        this.vue = vue;
        this.modele = new VehiculeModel(uneDb);
        this.marqueModel = new MarqueModel(uneDb);
        this.categorieModel = new CategorieModel(uneDb);

        vue.getBtnAjouter()
                .addActionListener(e -> ajouter());
        vue.getBtnModifier()
                .addActionListener(e -> modifier);
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
        vue.getTable()
                .getSelectionModel()
                .addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                        remplirFormulaire();
                    }
                });
        chargerTaleau();
    }

    // chargerMarques()
    private void chargerMarques(){
        vue.getComboMarque().removeAllItems();

        List<Marque> marques = marqueModel.getAll();
        for (Marque m : marques){
            vue.getComboMarque().addItem(m);
        }
    }
    // chargerCategories()
    private void chargerCategories(){
        vue.getComboCategorie().removeAllItems();

        List<Categorie> cats = categorieModel.getAll();
        for (Categorie c :cats){
            vue.getComboCategorie().addItem(c);
        }
    }
    // chargerTableau()
    private void chargerTableau(){
        List<Vehicule> liste = modele.getAll();

        Object[][] data = new Object[liste.size()][6];
        for (int i = 0; i < liste.size(); i++){
            Vehicule v = liste.get(i);
            data[i][0] = v.getId();
            data[i][1] = v.getNomModele();
            data[i][2] = v.getPrixCatalogue();
            data[i][3] = v.getNomMarque();
            data[i][4] = v.getNomCategorie();
            data[i][5] = v.isActif() ? "Oui" : "Non"

        }
        vue.majTableau(data);
    }
    // rechercher()
    // ajouter()
    // modifier()
    // supprimer()
    // remplirFormulaire()
}