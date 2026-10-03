# Arcane Wands (Fabric, MC 1.21.1)
Build: `gradle wrapper --gradle-version 8.12` (once), then `./gradlew build`.
Test: `./gradlew runClient`. Jar ends up in build/libs/.
Craft: amethyst shard + 2 sticks diagonally (or /give @s arcane:wand).
Right-click = cast, Sneak + right-click = next spell.
New spell: extend Spell, add it in Spells.register(), add a lang entry.

## No local setup? Use GitHub
Push this folder to a new GitHub repo. Open the Actions tab, run "Build jar",
then download the "arcane-jar" artifact. Unzip it to get arcane-1.0.0.jar.
