# Electricity Business - Application Console Java

## Description

Backend de l'application Electricity Business réalisé dans le cadre de ma formation CDA chez Human Booster.

Cette application console simule un service de réservation de bornes de recharge électrique avec gestion des comptes, lieux, bornes, et réservations; sans bibliothèques tierces, avec persistance sur fichiers JSON.

---

## Fonctionnalités obligatoires implémentées

### Comptes

*  Inscription d'un nouvel utilisateur avec génération automatique d'un code de validation affiché en console (simulation d'e-mail).
*  Validation du compte à l’aide du code fourni.
*  Connexion / déconnexion d’un utilisateur validé.

### Bornes & lieux

*  Ajout et modification d’un lieu (nom + adresse).
*  Ajout et modification d’une borne (tarif horaire) rattachée à un lieu.
*  Suppression d’une borne possible uniquement si **aucune réservation future** n’est liée à celle-ci.

### Réservations

*  Recherche interactive des bornes disponibles sur un créneau horaire (format `yyyy-MM-dd HH:mm`).
*  Création d’une réservation (statut initial `EN_ATTENTE`).
*  Traitement des réservations (acceptation / refus) via un menu dédié à l’opérateur.

### Documents

*  Génération automatique d’un **reçu au format `.txt`** lors de l’acceptation d’une réservation (stocké dans `exports/`).

### IHM console

*  Menu principal clair avec navigation structurée.
*  Validation des entrées utilisateurs avec relances si besoin.
*  Sélection interactive par **index** pour les lieux, bornes et réservations (plus ergonomique que la saisie d’ID).

---

## Bonus implémentés

*  Persistance via fichiers JSON (sans bibliothèque tierce).
*  Chargement automatique des données existantes à l’ouverture.
*  Blocage de suppression des bornes avec réservations futures.
*  Formatage ISO propre des dates pour fiabilité (lecture/écriture).

---

## Structure du projet

> Le projet suit une structure inspirée du modèle MVC (Model / View / Controller), en s’écartant volontairement de la structure demandée dans l’énoncé.
>
> Cela permet une meilleure séparation des responsabilités, conforme aux standards du développement logiciel réel :
>
> * `controller/` : logique de gestion des entrées utilisateurs (UI textuelle)
> * `service/` : logique métier + interfaces
> * `repository/` : gestion des accès aux fichiers JSON simulant la persistance
> * `model/` : entités et énumérations du domaine

```
src/
├── com.humanbooster
    ├── model/         -> Entités : Utilisateur, LieuRecharge, BorneRecharge, Reservation, enums
    ├── controller/     -> Contrôleurs pour les menus
    ├── service/        -> Services (logique métier) + interfaces demandées
    ├── repository/     -> Repositories simulés avec fichiers JSON
    └── ui/             -> Menu principal + Main
```

---

## Fichiers générés / requis

* `data/utilisateurs.json`
* `data/lieux.json`
* `data/reservations.json`
* `exports/recu_<id>.txt` (lors d'une réservation acceptée)

---

## Informations importantes

* Le système est entièrement en mémoire + fichiers JSON (pas de BDD)
* Aucune dépendance externe
* Les identifiants (lieux, bornes, utilisateurs, réservations) sont générés via `UUID`
* Le code respecte l'architecture MVC : Controller / Service / Repository / Model
