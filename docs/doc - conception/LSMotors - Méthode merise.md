# **LS MOTORS – Documentation Merise**

## **Auteurs**
- *Dave ISRAEL* 
- *Youssef BOUMLIK*  
- *Noah MILLOT*  
- *Théo DORIVAL*  

**Formation :** *BTS SIO – SLAM*  
**Entreprise :** NYT *(Nyght)*

---

## **Sommaire**
1. [Introduction à Merise](#introduction-à-merise)  
2. [Les trois niveaux Merise](#les-trois-niveaux-merise)  
3. [Démarche Merise appliquée au projet LS Motors](#démarche-merise-appliquée-au-projet-ls-motors)  
4. [Modèle Conceptuel de Données (MCD)](#modèle-conceptuel-de-données-mcd)  
5. [Modèle Logique de Données (MLD)](#modèle-logique-de-données-mld)  
6. [Modèle Physique de Données (MPD)](#modèle-physique-de-données-mpd)  
7. [Règles Merise utilisées](#règles-merise-utilisées)  
8. [Pourquoi Merise dans LS Motors ?](#pourquoi-merise-dans-ls-motors)

---

# **Introduction à Merise**

La méthode **Merise** est une méthode d’analyse et de conception des systèmes d’information.  
Elle sépare :

- les **données**  
- les **traitements**  
- les **échanges**  
- la **logique métier**

Dans **LS Motors**, Merise a permis de structurer les besoins et de concevoir une base de données cohérente et fiable.

---

# **Les trois niveaux Merise**

## 🔹 **1. Niveau Conceptuel**
Comprendre les besoins sans contraintes techniques.

Inclut :  
- **MCD**  
- Diagrammes de flux  
- Cas d’utilisation  

## 🔹 **2. Niveau Logique**
Traduction du conceptuel en structure exploitable par un SGBD.

Inclut :  
- **MLD**  
- Normalisation  
- Typage logique  

## 🔹 **3. Niveau Physique**
Implémentation réelle dans MySQL.

Inclut :  
- **MPD**  
- Types SQL  
- Contraintes  
- Clés primaires / étrangères  

---

# **Démarche Merise appliquée au projet LS Motors**

## **Analyse des besoins**

Besoins identifiés :

- Gestion des véhicules  
- Gestion des marques  
- Gestion des catégories  
- Gestion des utilisateurs  
- Sécurisation via rôles  
- Catalogue public  
- Back-office simple  

Entités clés :

- Véhicule  
- Marque  
- Catégorie  
- Utilisateur  
- Vente (optionnelle)  

---

# **Modèle Conceptuel de Données (MCD)**

## **Entités**

### **Véhicule**
- ID_Vehicule  
- Nom  
- Prix  
- Image  
- Description  

### **Marque**
- ID_Marque  
- Nom  

### **Catégorie**
- ID_Categorie  
- Libelle  

### **Utilisateur**
- ID_Utilisateur  
- Nom  
- Prenom  
- Email  
- Password  
- Role  

### **Vente**
- ID_Vente  
- Date  
- ID_Vehicule  
- ID_Utilisateur  
- PrixVendu  

## **Relations**
- Véhicule — Marque → **1,N**  
- Véhicule — Catégorie → **1,N**  
- Utilisateur — Vente → **1,N**  
- Véhicule — Vente → **1,N**  

---

# **Modèle Logique de Données (MLD)**

### **VEHICULE**
- ID (PK)  
- nom  
- prix  
- image  
- description  
- id_marque (FK)  
- id_categorie (FK)  

### **MARQUE**
- ID (PK)  
- nom  

### **CATEGORIE**
- ID (PK)  
- libelle  

### **UTILISATEUR**
- ID (PK)  
- nom  
- prenom  
- email  
- password  
- role  

### **VENTE**
- ID (PK)  
- date  
- id_vehicule (FK)  
- id_utilisateur (FK)  
- prix_vendu  

---

# **Modèle Physique de Données (MPD)**

### **Table `vehicule`**
```sql
ID INT AUTO_INCREMENT PRIMARY KEY,
Nom VARCHAR(100),
Prix INT,
Image VARCHAR(255),
Description TEXT,
ID_Marque INT,
ID_Categorie INT,
FOREIGN KEY (ID_Marque) REFERENCES marque(ID),
FOREIGN KEY (ID_Categorie) REFERENCES categorie(ID)
```

### **Table `marque`**
```sql
ID INT AUTO_INCREMENT PRIMARY KEY,
Nom VARCHAR(100)
```

### **Table `categorie`**
```sql
ID INT AUTO_INCREMENT PRIMARY KEY,
Libelle VARCHAR(100)
```

### **Table `utilisateur`**
```sql
ID INT AUTO_INCREMENT PRIMARY KEY,
Nom VARCHAR(50),
Prenom VARCHAR(50),
Email VARCHAR(100) UNIQUE,
Password VARCHAR(255),
Role VARCHAR(20)
```

### **Table `vente`**
```sql
ID INT AUTO_INCREMENT PRIMARY KEY,
Date DATETIME,
ID_Utilisateur INT,
ID_Vehicule INT,
PrixVendu INT,
FOREIGN KEY (ID_Utilisateur) REFERENCES utilisateur(ID),
FOREIGN KEY (ID_Vehicule) REFERENCES vehicule(ID)
```

---

# **Règles Merise utilisées**

- ✔ Une entité = un concept métier  
- ✔ Une relation = un lien entre deux entités  
- ✔ Normalisation en **3NF**  
- ✔ Clés primaires uniques  
- ✔ Typage adapté MySQL  

---

# **Pourquoi Merise dans LS Motors ?**

Merise a permis de :

- structurer proprement le projet  
- éviter les doublons  
- clarifier les relations  
- sécuriser les données  
- faciliter le CRUD  
- respecter les exigences SLAM  

Toute la structure du projet repose sur Merise.

