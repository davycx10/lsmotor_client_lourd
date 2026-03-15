# **LS MOTORS – Cahier des Contraintes**

## **Auteurs**
- **Dave ISRAEL**  
- **Youssef BOUMLIK**  
- **Noah MILLOT**  
- **Théo DORIVAL**  

**Entreprise :** NYT (Nyght)

---

## **Sommaire**
1. [Contraintes Fonctionnelles](#contraintes-fonctionnelles)  
2. [Contraintes Techniques](#contraintes-techniques)  
3. [Contraintes Esthétiques & Identité visuelle](#contraintes-esthétiques--identité-visuelle)  
4. [Contraintes Organisationnelles](#contraintes-organisationnelles)  
5. [Contraintes d’Utilisation & Accessibilité](#contraintes-dutilisation--accessibilité)  
6. [Contraintes Légales & Éthiques](#contraintes-légales--éthiques)  
7. [Récapitulatif global](#récapitulatif-global)

---

# **Contraintes Fonctionnelles**

### ✔ Gestion des véhicules
- Ajouter / modifier / supprimer un véhicule  
- Affichage administrateur (tableau)  
- Affichage public  
- Organisation par **catégorie** et **marque**  
- Affichage prix + prix après taxe (35%)

### ✔ Gestion des catégories
- CRUD complet

### ✔ Gestion des marques
- CRUD complet

### ✔ Gestion des utilisateurs
- Création de comptes employés  
- Gestion des rôles (admin / employé)  
- Sécurisation des pages sensibles  
- Restrictions d’accès pour les employés

### ✔ Gestion des ventes
- Enregistrer une vente  
- Lier vente ↔ véhicule ↔ employé  
- Historique des ventes

### ✔ Partie publique
- Page d’accueil  
- Catalogue public  
- Page contact (liens Discord)  
- Logo → redirection accueil  
- Tri par catégorie

### ✔ Fonctionnalités RP
- Consultation rapide d’un véhicule  
- Utilisation mobile pendant les scènes RP  

---

# **Contraintes Techniques**

### ✔ Architecture
- **MVC obligatoire**  
- Dossiers séparés : `controllers/`, `models/`, `views/`  
- Router central dans `index.php`  
- Header/footer inclus automatiquement  

### ✔ Langages
- PHP natif  
- PDO (requêtes préparées obligatoires)  
- MySQL  
- HTML / CSS / JS léger  

### ✔ Base de données
Tables obligatoires :
- `utilisateur`  
- `marque`  
- `categorie`  
- `vehicule`  
- `vente`

### ✔ Sécurité
- `session_start()` unique  
- Vérification du rôle sur chaque page admin  
- Blocage des accès directs via URL  
- Protection SQL (PDO préparé)

### ✔ Normes BTS SIO
- MCD / MPD  
- Trello  
- Wireframe  
- Documentation complète  
- Figma  
- Xmind  
- Use Case  
- Merise  
- Gantt  
- Cahier des charges  
- GitHub  

---

# **Contraintes Esthétiques & Identité visuelle**

### ✔ Charte graphique
Couleurs imposées :
- Noir  
- Blanc  
- Gris foncé  
- Doré `#CBA135`

### ✔ Polices
- Montserrat  
- Sans-serif  

### ✔ Style général
- Thème automobile premium  
- Interface moderne et professionnelle  
- Responsive  
- Tableaux lisibles  
- Boutons homogènes  
- UX simple pour employés RP  

---

# **Contraintes Organisationnelles**

### ✔ Contexte client
- Client fictif : gérant LS Motors  
- Objectif : moderniser la gestion interne + catalogue public RP  

### ✔ Contexte prestataire
- Prestataire : NYT  
- 4 développeurs : Noah, Youssef, Théo, Dave  
- Organisation agile pédagogique  
- Répartition : analyse, maquettes, dev, tests  

### ✔ Cadre BTS SIO
- Projet évalué en E6  
- Documentation complète obligatoire  
- Application fonctionnelle exigée  
- Preuve des compétences du référentiel  

---

# **Contraintes d’Utilisation & Accessibilité**

### ✔ Utilisation
- Accès public sans connexion  
- Accès admin réservé  
- Navigation intuitive  
- Utilisation mobile (RP)

### ✔ Accessibilité RP
- Consultation rapide  
- Informations claires  
- Pas de surcharge visuelle  

### ✔ Temps réel RP
- Site réactif  
- Chargement rapide  
- Mises à jour instantanées  

---

# **Contraintes Légales & Éthiques**

Même en RP, obligations :

- Protection des données utilisateurs  
- Sécurité des comptes admin/employé  
- Accès limité selon les rôles  
- **Aucun mot de passe en clair** (hash obligatoire)

---

# **Récapitulatif global**

| Type de contrainte | Résumé |
|--------------------|--------|
| **Fonctionnelles** | CRUD véhicules, catégories, marques, utilisateurs, ventes ; partie publique + admin ; prix avec taxes ; rôles admin/employé |
| **Techniques** | MVC ; PHP + MySQL ; PDO ; routing ; sécurité sessions ; BDD complète |
| **Graphiques** | Noir/blanc/gris/doré ; Montserrat ; thème automobile premium |
| **Organisationnelles** | Projet NYT ; client LS Motors ; cadre RP ; exigences BTS |
| **Utilisation** | Simple, rapide, responsive ; adaptée au RP |
| **Légales / sécurité** | Hash mots de passe ; filtrage accès ; rôles ; protection BDD |

