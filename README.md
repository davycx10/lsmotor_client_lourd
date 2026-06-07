# lsmotor_client_lourd
client lourd ppe

**Prérequis** :
Java *11+*
MySQL installé et lancé en local
Connecteur JDBC MySQL dans les dépendances (mysql-connector-java)
Dans IntelliJ <-- pour *mysql:mysql-connector-java* : **File** → *Project Structure* → **Libraries** → Clique sur + → From Maven → Tape : *mysql:mysql-connector-java:8.0.33* (ou version supérieure)
La base créée avec le script SQL fourni (dans lsmotor_admin)

---

## Présentation de l'application

**LS Motors Admin Console** est une application de bureau Java (client lourd) développée dans le cadre d'un PPE BTS SIO SLAM par l'équipe **NYT (Nyght)**.

Elle permet à l'administrateur de la concession **LS Motors Dealership** — une concession automobile dans un univers de jeu de rôle (GTA V RP) — de gérer toutes les données utilisées par le site web LS Motors :

- Les **utilisateurs** (admins, employés, joueurs)
- Les **marques** automobiles disponibles (plus de 50 marques GTA V)
- Les **catégories** de véhicules (Sport, SUV, Berline, Moto...)
- Le **catalogue de véhicules** avec prix et disponibilité
- L'**historique des ventes** hebdomadaires par employé
- La **marge globale** appliquée automatiquement sur les prix du site

> ⚠️ Accès réservé exclusivement aux administrateurs. Les employés et le public n'ont pas accès à cette console.

---

## Lancer l'application

1. S'assurer que MySQL est lancé en local
2. Exécuter le script `script.sql` pour créer la base `ls_motors`
3. Ouvrir le projet dans **IntelliJ IDEA**
4. Ajouter le connecteur JDBC MySQL dans les dépendances (voir prérequis ci-dessus)
5. Ouvrir `Main.java` à la racine du projet
6. Modifier les 4 constantes de connexion :

```java
private static final String SERVEUR = "localhost";
private static final String USER    = "root";
private static final String MDP     = "ton_mot_de_passe";
private static final String BDD_NOM = "ls_motors";
```

7. Lancer `Main.java` → la fenêtre de connexion apparaît
8. Se connecter avec un compte dont le rôle est `admin` en base

---

## Structure du projet

```
LSMotors/
├── Main.java                         ← Point d'entrée + paramètres BDD
│
├── model/                            ← Données & SQL uniquement (jamais de Swing ici)
│   │
│   ├── POJOs/                        ← Objets Java représentant les tables SQL
│   │   ├── Utilisateur.java          ← table `utilisateur`
│   │   ├── Vehicule.java             ← table `vehicule`
│   │   ├── Marque.java               ← table `marque`
│   │   ├── Categorie.java            ← table `categorie`
│   │   ├── Vente.java                ← table `vente`
│   │   └── Config.java               ← table `config`
│   │
│   ├── BDD.java                      ← Connexion MySQL (similaire à PDO en PHP)
│   ├── UtilisateurModel.java         ← Requêtes SQL sur `utilisateur`
│   ├── VehiculeModel.java            ← Requêtes SQL sur `vehicule`
│   ├── MarqueModel.java              ← Requêtes SQL sur `marque`
│   ├── CategorieModel.java           ← Requêtes SQL sur `categorie`
│   └── VenteModel.java               ← Requêtes SQL sur `vente` + `config`
│
├── controller/                       ← Logique : fait le lien vue ↔ modèle
│   ├── LoginController.java          ← Authentification admin
│   ├── UtilisateurController.java    ← CRUD utilisateurs
│   ├── VehiculeController.java       ← CRUD véhicules
│   ├── MarqueController.java         ← CRUD marques
│   ├── CategorieController.java      ← CRUD catégories
│   ├── VenteController.java          ← Lecture historique ventes
│   └── ConfigController.java         ← Modification marge globale
│
└── view/                             ← Interface graphique Swing (charte LS Motors)
    ├── Theme.java                    ← Couleurs, polices, méthodes de style centralisées
    ├── LoginView.java                ← Écran de connexion
    ├── MainView.java                 ← Fenêtre principale (CardLayout)
    ├── HeaderPanel.java              ← Barre fixe du haut (logo + utilisateur connecté)
    ├── SidebarPanel.java             ← Menu de navigation latéral
    ├── DashboardPanel.java           ← Page d'accueil avec cartes statistiques
    ├── BaseCrudPanel.java            ← Classe de base réutilisable (tableau + recherche)
    ├── UtilisateurPanel.java         ← Hérite de BaseCrudPanel, formulaire utilisateur
    ├── VehiculePanel.java            ← Hérite de BaseCrudPanel, combos marque/catégorie
    ├── CategoriePanel.java           ← Hérite de BaseCrudPanel, formulaire simple
    ├── MarquePanel.java              ← Hérite de BaseCrudPanel, formulaire simple
    ├── VentePanel.java               ← 2 tableaux en onglets, lecture seule
    └── ConfigPanel.java              ← Marge globale, carte centrée

    ressource ----> contient les images
```

---

## Architecture MVC

Le projet suit le patron de conception **MVC (Modèle - Vue - Contrôleur)** :

```
UTILISATEUR
    ↓ clique
  VUE (view/)          → affiche, collecte les saisies, ne contient PAS de logique
    ↓ notifie
CONTROLLER (controller/) → reçoit l'événement, valide, appelle le modèle
    ↓ interroge
  MODÈLE (model/)      → exécute les requêtes SQL, retourne les données
    ↓ retourne
CONTROLLER             → met à jour la vue avec les nouvelles données
    ↓ met à jour
  VUE                  → affiche le résultat
```

**Règle d'or** : une vue ne parle jamais directement à un modèle, et un modèle ne connaît pas la vue. Tout passe par le controller.

---

## Navigation dans l'application

L'application fonctionne comme un système de pages, similaire à `index.php` en PHP :

```
LoginView
    ↓ connexion réussie (rôle admin vérifié en BDD)
MainView  ←→  SidebarPanel (navigation)
    │
    ├── [0] DashboardPanel      ← page d'accueil
    ├── [1] UtilisateurPanel    ← gestion des comptes
    ├── [2] CategoriePanel      ← gestion des catégories
    ├── [3] MarquePanel         ← gestion des marques
    ├── [4] VehiculePanel       ← gestion du catalogue
    ├── [5] VentePanel          ← historique des ventes
    └── [6] ConfigPanel         ← marge globale
```

`MainView` utilise un **CardLayout** : un seul panneau est visible à la fois. Quand l'utilisateur clique sur un item dans la sidebar, `afficherPanel(index)` est appelée et le bon panneau s'affiche.

---

## Liens entre les fichiers

### Au démarrage

```
Main.java
  → crée BDD + LoginView + LoginController
      LoginController branche les boutons de LoginView
          → sur clic "SE CONNECTER" : appelle UtilisateurModel.verifierConnexion()
              → si admin trouvé : crée MainView(db, utilisateur)
              → MainView crée tous les panneaux + tous les controllers
```

### Exemple : ajout d'un véhicule

```
[Utilisateur remplit le formulaire dans VehiculePanel et clique "+ Ajouter"]
    ↓
VehiculeController.ajouter()
    → lit les champs via vue.getNom(), vue.getPrix()...
    → récupère la Marque sélectionnée : (Marque) vue.getMarqueSelectionnee()
    → récupère la Categorie sélectionnée : (Categorie) vue.getCategorieSelectionnee()
    → crée un objet Vehicule (POJO)
    → appelle VehiculeModel.ajouter(vehicule)
        → exécute INSERT INTO vehicule (...) VALUES (?)
        → retourne true si succès
    → si succès : appelle chargerTableau() + vue.viderFormulaire()
    → si échec : appelle vue.afficherErreur("message")
```

### Exemple : navigation via la sidebar

```
[Utilisateur clique sur "Véhicules" dans SidebarPanel]
    ↓
SidebarPanel appelle listener.onNavigate(IDX_VEHICULES)
    ↓
MainView.afficherPanel(4)
    → sidebar.setItemActif(4)     ← met à jour le style de la sidebar
    → cardLayout.show(panelContenu, "vehicules")  ← affiche VehiculePanel
```

---

## Les POJOs — à quoi ça sert ?

Un **POJO (Plain Old Java Object)** est une classe simple qui représente **une ligne de table SQL en mémoire Java**.

```
Ligne SQL en BDD :              Objet POJO en Java :
──────────────────────────────────────────────────────
vehicule.ID = 1            →   vehicule.getId() = 1
vehicule.NomModele = "X"   →   vehicule.getNomModele() = "X"
vehicule.Actif = 1         →   vehicule.isActif() = true
```

Ils se trouvent dans `model/pojos/` et ne contiennent que des attributs, getters et setters — aucune logique SQL.

> ⚠️ `Marque.java` et `Categorie.java` ont un `toString()` qui retourne uniquement le nom/libellé car Swing utilise `toString()` pour afficher les items dans les JComboBox.

---

## Base de données

| Table | Rôle |
|-------|------|
| `utilisateur` | Comptes admin, employés et joueurs |
| `marque` | Marques automobiles RP |
| `categorie` | Catégories de véhicules |
| `vehicule` | Catalogue des véhicules (avec FK vers marque + catégorie) |
| `vente` | Historique des ventes (créé par le site web, lu ici) |
| `config` | Configuration globale — 1 seule ligne (ID = 1), contient la marge |

Le fichier `script.sql` crée toute la base. Les fichiers `insert.sql` et `update_image.sql` sont des compléments pour alimenter les données.

---

## Charte graphique

Définie dans `view/Theme.java`, à utiliser dans toutes les vues :

| Élément | Valeur |
|---------|--------|
| Fond principal | `#0D0D10` |
| Fond cartes/panneaux | `#22222C` |
| Or (accent principal) | `#C9A227` |
| Texte blanc | `#FFFFFF` |
| Texte gris | `#8F9096` |
| Police | `Segoe UI` |

Pour styler un composant : `Theme.styleButtonPrimary(btn)`, `Theme.styleInput(field)`, etc.

---

## Répartition des tâches suggérée

Chaque membre peut travailler sur une couche indépendamment :

| Couche | Fichiers | Peut travailler seul ? |
|--------|----------|------------------------|
| POJOs | `model/pojos/*.java` | ✅ Oui, pas de dépendances |
| Models | `model/*Model.java` | ✅ Oui (nécessite les POJOs) |
| Views | `view/*.java` | ✅ Oui (déjà fournis) |
| Controllers | `controller/*.java` | ⚠️ Nécessite Models + Views |

**Ordre recommandé :**
1. Compléter les **POJOs** (attributs + getters/setters)
2. Implémenter les **Models** (requêtes SQL — guide dans les commentaires)
3. Tester les Models avec des `System.out.println` dans `Main.java`
4. Implémenter les **Controllers** (brancher vue ↔ modèle — guide dans les commentaires)
5. Tester chaque onglet un par un

---
