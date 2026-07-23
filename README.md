# ServerGuide

Server-side Paper plugin that displays a configurable, multilingual guide dialog when a player joins.

## Requirements

- Paper 26.1.2+
- Java 25
- No client mod
- No resource pack

## Features

- Native Minecraft dialog window
- Automatic client-language detection
- Configurable pages
- Previous/next navigation
- "Do not show again" button
- UUID-based player preference stored in PersistentDataContainer
- `/serverguide` command to reopen the guide
- `/serverguide reset [player]`
- `/serverguide reload`
- MiniMessage formatting
- Language fallback to English

## Building

Double-click `build.bat`.

The build script automatically:

- checks for Java 25
- downloads a private Eclipse Temurin JDK 25 when needed
- downloads Gradle 9.0.0 into `.build-tools`
- downloads the Paper API and all required dependencies
- builds the plugin JAR

Nothing is installed system-wide. Downloaded build tools remain inside the project folder.

The JAR will be created in `build/libs/ServerGuide-1.2.0.jar`.

## Editing content

Edit files in:

```text
plugins/ServerGuide/languages/
```

The English and German files are installed automatically. Additional language files can be copied from the JAR or created using the same structure.

After changes, run:

```text
/serverguide reload
```

## Included languages

German, English, French, Spanish, Italian, Dutch, Polish, Portuguese, Russian,
Turkish, Ukrainian, Czech, Hungarian, Romanian, Simplified Chinese, Japanese and Korean.

Regional variants include German (Austria/Switzerland), English (UK),
Spanish (Mexico) and Portuguese (Brazil/Portugal). English remains the fallback language.

## Windows build troubleshooting

Double-click `build.bat`. The window now always remains open after success or failure.
Detailed output is also saved in `build-log.txt`.
