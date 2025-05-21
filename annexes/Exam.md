# Cahier des charges – Electricity Business (TP Java Hibernate – Évaluation DAO ORM)

## 1. **Objectif général**

Le projet "Electricity Business" évolue : vous allez désormais **connecter l'application à une base de données** en utilisant **Hibernate**.

L’objectif est de persister les entités précédemment manipulées en mémoire, via une **couche DAO** propre, fonctionnelle et bien structurée.

Ce TP constitue une **évaluation individuelle**. Le rendu doit respecter les consignes ci-dessous.

---

## 2. **Portée fonctionnelle**

### **Fonctionnalités obligatoires**

Vous devez :

- Configurer **Hibernate** avec une base H2 ou MySQL. (spécifiez dans votre README l’architecture choisie)
- Annoter et mapper les entités suivantes :

  `Utilisateur`, `LieuRecharge`, `BorneRecharge`, `Reservation`

- Implémenter pour chacune un DAO :
   - Interface de DAO
   - Implémentation concrète
- Fournir les opérations **CRUD minimales** :
   - **Créer**
   - **Lire par identifiant**
   - **Lire tous**
   - **Mettre à jour**
   - **Supprimer**

> Les tests peuvent se faire via une classe App.java ou des méthodes statiques de test simples.
>

---

## 3. **Contraintes techniques**

| Élément | Détail attendu |
| --- | --- |
| **Technologie** | Java 20+, Hibernate 6, JDBC (H2 ou MySQL au choix) |
| **Aucune bibliothèque externe** | Seules les dépendances Hibernate sont autorisées |
| **Structure du projet** | Voir ci-dessous |
| **ORM** | Annotations JPA uniquement (`@Entity`, `@Id`, `@OneToMany`, etc.) |
| **Relationnelles** | Les relations doivent être correctement modélisées (ex : une borne appartient à un lieu, une réservation à un utilisateur et à une borne) |

### 📁 **Structure du code attendue**

```
src/
 ├─ model/         → entités annotées (Hibernate)
 ├─ dao/           → interfaces + implémentations DAO
 └─ App.java       → classe principale ou tests
resources/
 └─ hibernate.cfg.xml

```

(vous pouvez également placer la gestion de la sessionFactory dans un dossier util)

---

## 4. **Modèle de données à mapper**

| Entité | Attributs principaux | Relations |
| --- | --- | --- |
| `Utilisateur` | `id`, `email`, `motDePasse`, `codeValidation`, `valide`, `role` | `@OneToMany` réservations |
| `LieuRecharge` | `id`, `nom`, `adresse` | `@OneToMany` bornes |
| `BorneRecharge` | `id`, `etat`, `tarifHoraire` | `@ManyToOne` lieu  `@OneToMany` réservations |
| `Reservation` | `id`, `dateDebut`, `dateFin`, `statut` | `@ManyToOne` utilisateur  `@ManyToOne` borne |

---

## 5. **Livrables obligatoires**

Vous devez rendre :

- Le code source complet
- Le fichier `hibernate.cfg.xml` (ou properties) fonctionnel
- Un fichier `README.md` avec :
   - Description de votre implémentation
   - Explication de vos choix techniques
   - Résultat attendu des méthodes DAO testées

---

## 6. **Fonctionnalités Bonus (facultatives)**

Implémentez une ou plusieurs des fonctionnalités suivantes si vous souhaitez aller plus loin et valoriser votre code.

Ces fonctionnalités nécessitent une manipulation **plus avancée des requêtes** :

1. 🔍 **Rechercher toutes les bornes disponibles** pour un créneau horaire donné (non déjà réservées à ce moment).
2. 📅 **Lister toutes les réservations** faites par un utilisateur donné, triées par date.
3. 📈 **Calculer le montant total** à facturer pour une réservation terminée (durée × tarif horaire de la borne).
4. 🧾 **Rechercher les bornes** dont le tarif horaire dépasse une certaine valeur.
5. 🛠️ **Trouver les bornes actuellement en maintenance** ou inactives (filtrage sur l'état).

## 7. **Critères d’évaluation**

| Critère | Détail |
| --- | --- |
| **Respect du cahier des charges** | Fonctionnalités obligatoires présentes |
| **Qualité du code** | Structure, clarté, noms, indentation |
| **Modélisation correcte** | Entités, relations, cohérence JPA |
| **Fonctionnement** | Le code compile, s'exécute, et persiste correctement |
| **Bonus** | Fonctionnalités optionnelles implémentées correctement |


