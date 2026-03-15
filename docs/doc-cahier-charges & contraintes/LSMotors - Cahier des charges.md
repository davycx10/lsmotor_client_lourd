# **LS MOTORS – Cahier des Charges**

## **Auteurs**
- **Dave ISRAEL**  
- **Youssef BOUMILK**  
- **Noah MILLOT**  
- **Théo DORIVAL**  

**Entreprise :** NYT (Nyght)  
**Formation :** BTS SIO – Option SLAM  

---

## **Sommaire**
1. [Présentation du Projet](#présentation-du-projet)  
2. [Objectifs du Projet](#objectifs-du-projet)  
3. [Périmètre Fonctionnel](#périmètre-fonctionnel)  
   - Fonctionnalités Administrateur  
   - Fonctionnalités Employé  
   - Fonctionnalités Publiques  
4. [Contraintes du Projet](#contraintes-du-projet)  
5. [Technologies et Architecture](#technologies-et-architecture)  
6. [Charte Graphique](#charte-graphique)  
7. [Organisation du Projet](#organisation-du-projet)  
   - Organisation générale  
   - Répartition détaillée des tâches  
8. [Planning Prévisionnel](#planning-prévisionnel)  
9. [Livrables Attendus](#livrables-attendus)  
10. [Critères d’Acceptation](#critères-dacceptation)

---

# **Présentation du Projet**

Le projet **LS Motors** consiste à développer une plateforme web complète destinée à une concession automobile RP située dans l’univers de Los Santos.

La plateforme permet :

- un **CRUD complet** des véhicules,  
- la gestion des **catégories** et **marques**,  
- la gestion des **employés** via un système de rôles,  
- l’affichage public d’un **catalogue dynamique**,  
- une **interface administrateur sécurisée**.

Le projet est réalisé en **architecture MVC** selon les bonnes pratiques PHP.

- **Client fictif :** Gérant de LS Motors (serveur RP)  
- **Prestataire :** NYT (Night), composé de 4 développeurs  

---

# **Objectifs du Projet**

## **Objectif principal**
Créer un site web moderne, fonctionnel et administrable permettant de gérer la concession LS Motors comme un véritable outil métier.

## **Objectifs secondaires**
- Optimiser la gestion RP des véhicules et des employés  
- Faciliter l’accès aux informations via une interface publique claire  
- Sécuriser les accès via un système de rôles  
- Respecter une charte graphique cohérente avec LS Motors  

---

# **Périmètre Fonctionnel**

## **3.1 Fonctionnalités Administrateur**
- CRUD véhicules  
- CRUD catégories  
- CRUD marques  
- CRUD utilisateurs + gestion des rôles  
- Accès complet au back-office  
- Tableaux récapitulatifs  

## **3.2 Fonctionnalités Employé**
- Consultation du catalogue  
- Recherche + filtrage  
- Affichage par catégorie  
- Accès limité au back-office  

## **3.3 Fonctionnalités Publiques**
- Page d’accueil  
- Catalogue complet  
- Filtre par catégories  
- Page contact (liens Discord)  
- Navigation claire  

---

# **Contraintes du Projet**

Les contraintes sont réparties en 6 catégories :

- Contraintes fonctionnelles  
- Contraintes techniques  
- Contraintes graphiques  
- Contraintes organisationnelles  
- Contraintes d’accessibilité  
- Contraintes de sécurité  



---

# **Technologies et Architecture**

## **Architecture**
- MVC complet  
- Routing via `index.php`  
- Séparation stricte : `models/`, `controllers/`, `views/`  

## **Langages et outils**
- PHP natif  
- MySQL (PDO sécurisé)  
- HTML / CSS / JS  
- GitHub pour le versionning  

## **Base de données**
Tables obligatoires :
- `utilisateur`  
- `vehicule`  
- `categorie`  
- `marque`  
- `vente`  

---

# **Charte Graphique**

## **Couleurs**
- Noir  
- Blanc  
- Gris foncé  
- Doré `#CBA135`

## **Polices**
- Montserrat  
- Sans-serif  

## **Ambiance**
- Premium  
- Automobile  
- Moderne  
- RP réaliste  

---

# **Organisation du Projet**

## **7.1 Organisation générale**

Équipe **NYT (Nyght)** :
- **Noah** – Chef de projet, développeur, organisation  
- **Youssef** – Documentation, validation, maquettage, analyse  
- **Théo** – Qualité, validation, maquettage, analyse  
- **Dave** – Développeur, gestion Git, DevOps, Gestion de projet  

Méthodologie : **Agile**, réunions régulières, suivi Trello.

---

## **7.2 Répartition détaillée des tâches**

### ✔ Organisation du projet
- Trello  
- Nom du groupe (NYT)  
- Nom du projet (LSMotors)  
- Cahier des charges  
- Contraintes du projet  
- Contexte  
- Documentation prestataire  
- Documentation client  
- Problématique  
- Charte graphique  
- GitHub  

### ✔ Développement & conception
- Base de données (MCD / MPD / SQL)  
- Arborescence du projet  
- Models PHP  
- Controllers PHP  
- CRUD complet  
- Views  
- Wireframe  
- Maquettes Figma  
- Use Case + UML  
- Méthode Merise  
- Diagramme de Gantt  

### ✔ Qualité & tests
- Tests fonctionnels  
- Relecture du code  
- Vérification conformité E6  
- Propositions d’amélioration  
- Gestion des retours utilisateur  

---

# **Planning Prévisionnel**

Un **diagramme de Gantt** couvre :

- Analyse  
- Conception  
- Développement  
- Tests  
- Livraison  
- Documentation  

---

# **Livrables Attendus**

## **Livrables techniques**
- Base de données (SQL)  
- Application web MVC  
- Code source GitHub  
- Documentation technique  

## **Livrables graphiques**
- Wireframe  
- Maquettes Figma  
- Charte graphique  
- Logo LS Motors  

## **Livrables documentaires**
- Cahier des charges  
- Documentation client  
- Documentation prestataire  
- Contraintes  
- Problématique  
- Use Case  
- MCD / MLD  
- Diagramme de Gantt  
- Présentation orale  

---

# **Critères d’Acceptation**

Pour valider le projet :

- ✔ Toutes les pages doivent fonctionner  
- ✔ CRUD complet  
- ✔ Authentification sécurisée  
- ✔ Rôle admin respecté  
- ✔ Cohérence visuelle  
- ✔ Navigation fluide  
- ✔ Tâches terminées par chaque membre  
- ✔ Conformité au cahier des charges  
- ✔ Dossier E6 complet et propre  

