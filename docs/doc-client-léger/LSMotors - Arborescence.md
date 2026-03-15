# **LS MOTORS – Structure du Projet**

## **Auteurs**
- **Dave ISRAEL**  
- **Youssef BOUMILK**  
- **Noah MILLOT**  
- **Théo DORIVAL**  

**Entreprise :** NYT (Nyght)  
**Formation :** BTS SIO – Option SLAM  

---

## **Sommaire**
1. [Structure complète du projet](#structure-complète-du-projet)  
2. [Rôle de chaque dossier](#rôle-de-chaque-dossier)

---

# **Structure complète du projet**

```plaintext
LSMOTORS/
│
├── BDD/
│     ├── bdd.php
│     ├── insert.sql
│     ├── update_image.sql
│     └── script.sql
│
├── controller/
│     ├── categorie/
│     │     ├── deleteCategorie.php
│     │     ├── insertCategorie.php
│     │     ├── selectCategorie.php
│     │     └── updateCategorie.php
│     │
│     ├── config/
│     │     ├── selectConfig.php
│     │     └── updateConfig.php
│     │
│     ├── marque/
│     │     ├── deleteMarque.php
│     │     ├── insertMarque.php
│     │     ├── selectMarque.php
│     │     └── updateMarque.php
│     │
│     ├── panier/
│     │     └── panierController.php
│     │
│     ├── rdv/
│     │     └── rdvController.php
│     │
│     ├── utilisateur/
│     │     ├── deleteUtilisateur.php
│     │     ├── insertUtilisateur.php
│     │     ├── loginUtilisateur.php
│     │     ├── selectUtilisateur.php
│     │     └── updateUtilisateur.php
│     │
│     ├── vehicule/
│     │     ├── deleteVehicule.php
│     │     ├── insertVehicule.php
│     │     ├── selectVehicule.php
│     │     └── updateVehicule.php
│     │
│     └── vente/
│           ├── deleteVente.php
│           ├── insertVente.php
│           ├── selectVente.php
│           └── updateVente.php
│
├── model/
│     ├── categorie/
│     │     └── categorieModel.php
│     ├── config/
│     │     └── configModel.php
│     ├── marque/
│     │     └── marqueModel.php
│     ├── panier/
│     │     └── panierModel.php
│     ├── rdv/
│     │     └── rdvModel.php
│     ├── utilisateur/
│     │     └── utilisateurModel.php
│     ├── vehicule/
│     │     └── vehiculeModel.php
│     └── vente/
│           └── venteModel.php
│
├── view/
│     ├── commun/
│     │     ├── footer.php
│     │     └── header.php
│     │
│     ├── pages/
│     │     ├── login/
│     │     │     ├── connexion.php
│     │     │     └── inscription.php
│     │     │
│     │     ├── user/
│     │     │     (pages utilisateur)
│     │     │
│     │     ├── admin/
│     │     │     ├── admin_page.php
│     │     │     ├── achats.php
│     │     │     ├── profil.php
│     │     │     ├── rdv_client.php
│     │     │     ├── rdv_demandes.php
│     │     │     ├── rdv_historique_employe.php
│     │     │     ├── rdv_mes.php
│     │     │     ├── rdv.php
│     │     │     ├── ventes_ajouter.php
│     │     │     └── ventes_historique.php
│     │
│     ├── accueil.php
│     ├── categories.php
│     ├── contact.php
│     └── listeVehicules.php
│
├── public/
│     ├── css/
│     │     ├── accueil.css
│     │     ├── admin.css
│     │     ├── categories.css
│     │     ├── connexion.css
│     │     ├── contact.css
│     │     ├── inscription.css
│     │     ├── listeVehicules.css
│     │     ├── ventes_ajouter.css
│     │     └── ventes_historique.css
│     │
│     ├── style.css
│     │
│     ├── img/
│     │     ├── emplacement.png
│     │     ├── logo.png
│     │     ├── logoComplet.png
│     │     └── logoWhite.png
│     │
│     └── js/
│           ├── dropdownHover.js
│           ├── search.js
│           └── theme.js
│
├── index.php
├── info.txt
├── README.md
└── seed_pseudo.php
```

---

# **Rôle de chaque dossier**

| Dossier / Fichier | Rôle |
|-------------------|------|
| **BDD/** | Connexion BDD + scripts SQL |
| **controller/** | Logique métier, traitement des requêtes |
| **model/** | Accès aux données, requêtes PDO préparées |
| **view/** | Affichage HTML/PHP |
| **view/commun/** | Header + footer globaux |
| **view/pages/login/** | Connexion / inscription |
| **view/pages/user/** | Pages utilisateur connecté |
| **view/pages/admin/** | Pages administrateur |
| **view/accueil.php** | Page d’accueil publique |
| **view/categories.php** | Affichage des catégories |
| **view/listeVehicules.php** | Catalogue public |
| **public/css/** | Styles CSS |
| **public/img/** | Logos et images |
| **public/js/** | Scripts JS (menu, recherche, thème) |
| **index.php** | Point d’entrée unique, routeur MVC |
| **seed_pseudo.php** | Génération de données de test |

