# ServerGuide

## English

**ServerGuide** is a configurable and multilingual Paper plugin that displays a native Minecraft guide window when players join your server.

Use it to welcome new players and explain important server features such as land claiming, protected doors and chests, homes, rules, commands, websites, Discord servers and more.

No client mod or resource pack is required.

---

## ✨ Features

- Native Minecraft dialog window
- Automatically opens when a player joins
- Multiple configurable guide pages
- Previous and next navigation
- **Do not show again** button
- Players can reopen the guide at any time
- Automatic Minecraft client-language detection
- English fallback for unsupported languages
- 22 bundled locale files
- MiniMessage formatting support
- Clickable website and Discord links
- Configurable server name, website and Discord address
- UUID-based player preferences
- Configurable join delay
- Automatic repair of malformed bundled language files
- No client mod required
- No resource pack required

---

## 📝 Easy Configuration

Basic server information only needs to be entered once in `config.yml`:

    server:
      name: "My Minecraft Server"
      website: "https://example.com"
      website-display: "example.com"
      discord: "https://discord.gg/example"
      discord-display: "discord.gg/example"

These values can then be used inside all guide pages:

    <server_name>
    <website>
    <website_display>
    <discord>
    <discord_display>

---

## 📖 Configurable Pages

Server owners can add, remove and reorder guide pages:

    pages:
      - welcome
      - claiming
      - protection
      - homes
      - commands

Every page supports:

- Custom titles
- Multiple text lines
- Colors and formatting
- Commands
- Clickable links
- Empty lines
- MiniMessage tags

Example:

    pages:
      claiming:
        title: "<green><bold>Claiming Land</bold></green>"
        lines:
          - "<white>Hold a golden shovel in your hand.</white>"
          - ""
          - "<yellow>1.</yellow> Select the first corner."
          - "<yellow>2.</yellow> Select the opposite corner."
          - "<green>Your area is now protected.</green>"

---

## 🌍 Languages

ServerGuide automatically detects the language selected in the player's Minecraft client.

The plugin includes 22 locale files and uses English as a fallback whenever a translation or text entry is unavailable.

Language files are located in:

    plugins/ServerGuide/languages/

Server owners can freely edit existing files or create additional translations.

---

## 🎮 Commands

    /serverguide
    /serverguide open
    /serverguide reload
    /serverguide reset
    /serverguide reset <player>

Aliases:

    /guide
    /anleitung

---

## 🔐 Permissions

    serverguide.use
    serverguide.admin
    serverguide.bypass

Players with `serverguide.bypass` will not receive the guide automatically when joining.

---

# Deutsch

**ServerGuide** ist ein konfigurierbares und mehrsprachiges Paper-Plugin, das Spielern beim Betreten des Servers ein natives Minecraft-Anleitungsfenster anzeigt.

Damit kannst du neue Spieler begrüßen und wichtige Serverfunktionen wie Grundstückssicherung, geschützte Türen und Truhen, Zuhause-Punkte, Regeln, Befehle, Website, Discord und vieles mehr erklären.

Es wird weder eine Client-Mod noch ein Resourcepack benötigt.

---

## ✨ Funktionen

- Natives Minecraft-Dialogfenster
- Automatische Anzeige beim Betreten des Servers
- Mehrere frei konfigurierbare Seiten
- Navigation mit Zurück- und Weiter-Buttons
- Button **„Nicht mehr anzeigen“**
- Spieler können die Anleitung jederzeit erneut öffnen
- Automatische Erkennung der Minecraft-Clientsprache
- Englische Rückfallsprache
- 22 mitgelieferte Sprachdateien
- Unterstützung für MiniMessage-Formatierungen
- Anklickbare Website- und Discord-Links
- Zentral konfigurierbarer Servername
- Zentral konfigurierbare Website und Discord-Adresse
- Speicherung der Spielereinstellungen anhand der UUID
- Einstellbare Verzögerung nach dem Beitritt
- Automatische Reparatur beschädigter mitgelieferter Sprachdateien
- Keine Client-Mod erforderlich
- Kein Resourcepack erforderlich

---

## 📝 Einfache Konfiguration

Die grundlegenden Serverinformationen müssen nur einmal in der `config.yml` eingetragen werden:

    server:
      name: "Mein Minecraft Server"
      website: "https://example.com"
      website-display: "example.com"
      discord: "https://discord.gg/example"
      discord-display: "discord.gg/example"

Diese Werte können anschließend auf allen Anleitungsseiten verwendet werden:

    <server_name>
    <website>
    <website_display>
    <discord>
    <discord_display>

---

## 📖 Frei konfigurierbare Seiten

Serverbetreiber können Seiten ergänzen, entfernen und neu sortieren:

    pages:
      - welcome
      - claiming
      - protection
      - homes
      - commands

Jede Seite unterstützt:

- Eigene Überschriften
- Beliebig viele Textzeilen
- Farben und Formatierungen
- Befehle
- Anklickbare Links
- Leerzeilen
- MiniMessage-Tags

Beispiel:

    pages:
      claiming:
        title: "<green><bold>Land sichern</bold></green>"
        lines:
          - "<white>Halte eine Goldschaufel in der Hand.</white>"
          - ""
          - "<yellow>1.</yellow> Markiere die erste Ecke."
          - "<yellow>2.</yellow> Markiere die gegenüberliegende Ecke."
          - "<green>Dein Gebiet ist jetzt geschützt.</green>"

---

## 🌍 Sprachen

ServerGuide erkennt automatisch die Sprache, die ein Spieler in seinem Minecraft-Client eingestellt hat.

Das Plugin enthält 22 Sprachdateien. Wenn eine Übersetzung oder ein Texteintrag nicht verfügbar ist, wird automatisch Englisch verwendet.

Die Sprachdateien befinden sich unter:

    plugins/ServerGuide/languages/

Serverbetreiber können vorhandene Dateien frei bearbeiten oder zusätzliche Übersetzungen erstellen.

---

## 🎮 Befehle

    /serverguide
    /serverguide open
    /serverguide reload
    /serverguide reset
    /serverguide reset <Spieler>

Aliase:

    /guide
    /anleitung

---

## 🔐 Berechtigungen

    serverguide.use
    serverguide.admin
    serverguide.bypass

Spieler mit `serverguide.bypass` erhalten die Anleitung beim Betreten nicht automatisch.
