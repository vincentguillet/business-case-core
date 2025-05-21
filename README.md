# ⚡ Electricity Business – Application Console Java (Hibernate + Docker)

## Description

Application console réalisée dans le cadre de ma formation CDA chez Human Booster.  
Elle simule un système de **réservation de bornes de recharge électrique**, avec gestion des utilisateurs, lieux, bornes, réservations et génération de reçus.

La version actuelle repose sur :
- Java 21
- Hibernate 6 (ORM JPA)
- MySQL 8 (via Docker)
- Maven (build & packaging)
- Exécution conteneurisée avec Docker Compose

---

## Fonctionnalités

### ✅ Gestion des comptes utilisateurs
- Inscription avec génération de code de validation affiché en console (simulation email)
- Validation du compte via code
- Connexion / déconnexion

### ✅ Gestion des lieux & bornes
- Ajout / modification / suppression de lieux
- Ajout / modification / suppression de bornes dans un lieu
- Suppression conditionnelle : une borne ne peut être supprimée si une réservation future existe

### ✅ Réservations
- Recherche interactive des bornes disponibles sur un créneau donné
- Création de réservations avec statut initial `EN_ATTENTE`
- Traitement des réservations (`ACCEPTEE`, `REFUSEE`) via un menu administrateur

### ✅ Génération de documents
- Génération automatique d’un reçu `.txt` dans `exports/` après acceptation d’une réservation

---

## ⚙️ Architecture technique

- **Hibernate 6** avec JPA Annotations (`@Entity`, `@OneToMany`, etc.)
- **MySQL** comme base relationnelle (via Docker)
- **DAO Hibernate** personnalisés pour chaque entité
- **Aucune bibliothèque externe** (hors dépendances Hibernate & JDBC)
- Structure modulaire MVC (Controller / Service / DAO / Model)

---

## 📁 Structure du code

```
src/
└── main/
    └── java/
        └── com.humanbooster
            ├── model/         → Entités JPA : Utilisateur, LieuRecharge, BorneRecharge, Reservation
            ├── dao/           → DAO Hibernate génériques et spécifiques
            ├── service/       → Logique métier
            ├── controller/    → Gestion console des actions utilisateurs
            ├── ui/            → Main + Menu interactif
            └── config/        → Configuration Hibernate
```

---

## ▶️ Lancement du programme

### **Pré-requis** :
- Docker + Docker Compose installés et fonctionnels

### **Commandes** :

```bash
docker compose run -it --build app
```

> Cela :
> - Build le projet Java via Maven
> - Lance MySQL (port 3366) avec schéma `electricity-business`
> - Démarre l’application console une fois la BDD prête

---

## 📦 Structure des fichiers

| Fichier                  | Rôle |
|--------------------------|------|
| `pom.xml`                | Configuration Maven (Java 21, Hibernate 6, MySQL connector) |
| `Dockerfile`             | Image Java + Build Maven |
| `docker-compose.yml`     | Stack app + BDD MySQL |
| `wait-for-it.sh`         | Script pour attendre la disponibilité de MySQL |
| `exports/`               | Répertoire des reçus générés |

---

## 💡 Notes techniques

- Identifiants générés par la base via `@GeneratedValue(strategy = GenerationType.IDENTITY)`
- Relations correctement modélisées (`@ManyToOne`, `@OneToMany`)
- Les entités sont persistées automatiquement grâce à `hibernate.hbm2ddl.auto=update`
- La configuration Hibernate est dynamique via `HibernateConfig.java` et les variables d’environnement
