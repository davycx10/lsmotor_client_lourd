# **LS MOTORS – Manuel d’Utilisation (Console Admin Java)**

## **Auteurs**
- **Dave ISRAEL**  
- **Youssef BOUMILK**  
- **Noah MILLOT**  
- **Théo DORIVAL**  

**Entreprise :** NYT (Nyght)  
**Formation :** BTS SIO – Option SLAM  

---

## **Sommaire**
1. [Présentation](#présentation)  
2. [Connexion à la console](#connexion-à-la-console)  
3. [Tableau de bord](#tableau-de-bord)  
4. [Gestion des utilisateurs](#gestion-des-utilisateurs)  
   - Ajouter un utilisateur  
   - Modifier un utilisateur  
   - Supprimer un utilisateur  
5. [Gestion des catégories](#gestion-des-catégories)  
6. [Gestion des marques](#gestion-des-marques)  
7. [Gestion des véhicules](#gestion-des-véhicules)  
   - Ajouter  
   - Modifier  
   - Supprimer  
8. [Historique des ventes](#historique-des-ventes)  
9. [Configuration – Marge globale](#configuration--marge-globale)  
10. [Bonnes pratiques](#bonnes-pratiques)

---

# **Présentation**

La **Console Admin LS Motors** est une application de bureau développée en **Java**.  
Elle permet à l’administrateur de gérer toutes les données utilisées par le site web LS Motors.

Elle est **réservée exclusivement aux administrateurs**.  
Les employés et le public n’y ont pas accès.

| Caractéristique | Détail |
|-----------------|--------|
| **Type d’application** | Client lourd Java |
| **Accès** | Administrateurs uniquement |
| **Connexion** | Email + mot de passe admin |
| **Système requis** | Windows / macOS / Linux + Java |

---

# **Connexion à la console**

Au lancement :

1. Une fenêtre de connexion apparaît.  
2. Saisir **email administrateur** + **mot de passe**.  
3. Cliquer sur **Connexion**.

En cas d’erreur : un message d’authentification s’affiche.

---

# **Tableau de bord**

Après connexion, l’administrateur accède au **tableau de bord**, qui affiche :

- les modules disponibles,  
- le compte connecté,  
- un accès rapide aux onglets de gestion.

---

# **Gestion des utilisateurs**

L’onglet **Utilisateurs** permet de gérer les comptes de la concession.

Chaque utilisateur possède :

- Nom  
- Prénom  
- Email  
- Mot de passe  
- Rôle (admin / employé / joueur)  
- Pseudo Discord  

## **Ajouter un utilisateur**
1. Remplir les champs : Nom, Prénom, Email, Mot de passe  
2. Choisir le rôle  
3. Ajouter le pseudo Discord (optionnel)  
4. Cliquer sur **Ajouter**

## **Modifier un utilisateur**
1. Sélectionner un utilisateur dans le tableau  
2. Modifier les champs  
3. Cliquer sur **Modifier**

## **Supprimer un utilisateur**
1. Sélectionner l’utilisateur  
2. Cliquer sur **Supprimer**

Une barre de recherche permet de filtrer les utilisateurs.

---

# **Gestion des catégories**

L’onglet **Catégories** permet de gérer les catégories de véhicules (Sport, SUV, Moto, etc.).

## **Ajouter / Modifier / Supprimer**
- Saisir le libellé  
- Cliquer sur **Ajouter**, **Modifier** ou **Supprimer**  
- La recherche filtre instantanément les résultats  

⚠️ **Ne pas supprimer une catégorie utilisée par des véhicules.**

---

# **Gestion des marques**

L’onglet **Marques** liste toutes les marques automobiles RP (plus de 50 marques GTA V).

Fonctionnement identique aux catégories :

- Ajouter  
- Modifier  
- Supprimer  
- Recherche instantanée  

---

# **Gestion des véhicules**

L’onglet **Véhicules** est le module principal.  
Il affiche :

- Nom / Modèle  
- Prix  
- Marque  
- Catégorie  

## **Ajouter un véhicule**
1. Saisir le nom / modèle  
2. Choisir la marque  
3. Choisir la catégorie  
4. Saisir le prix (GTA $)  
5. Cliquer sur **Ajouter**

## **Modifier un véhicule**
1. Sélectionner un véhicule  
2. Les champs se remplissent automatiquement  
3. Modifier les informations  
4. Cliquer sur **Modifier**

## **Supprimer un véhicule**
1. Sélectionner le véhicule  
2. Cliquer sur **Supprimer**  
⚠️ Suppression définitive.

---

# **Historique des ventes**

L’onglet **Ventes** affiche l’historique hebdomadaire :

| Colonne | Description |
|--------|-------------|
| **Semaine (YYYYWW)** | Exemple : 202550 = semaine 50 de 2025 |
| **Début** | Date/heure de la première vente |
| **Total** | Montant total des ventes GTA $ |
| **Nb ventes** | Nombre de véhicules vendus |

Deux tableaux sont disponibles :

- Récapitulatif global  
- Détail par employé  

---

# **Configuration – Marge globale**

L’onglet **Config** permet de définir la **marge appliquée automatiquement** sur tous les véhicules.

Marge actuelle : **40 %**

## **Modifier la marge**
1. Cliquer sur **Charger**  
2. Modifier la valeur (%)  
3. Cliquer sur **Enregistrer**

Cette marge est utilisée pour calculer le **prix TTC RP** affiché sur le site web.

---

# **Bonnes pratiques**

- Se déconnecter en fermant l’application  
- Ne jamais partager ses identifiants admin  
- Vérifier qu’une catégorie ou marque n’est pas utilisée avant suppression  
- Cliquer sur **Rafraîchir** avant de consulter les ventes  
- Modifier la marge avec prudence (impact global sur les prix)  