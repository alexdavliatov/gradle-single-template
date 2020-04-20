## Gradle Kotlin single module template
### Includes
1. `ktlint` - [linter and formatter](https://github.com/JLLeitschuh/ktlint-gradle);
2. `sonarqube` - static code analysis.

    Includes a number of checkers could be added:

        a. PMD;
        b. ...

3. `dokka` - Kotlin documentation.

    NB: [fails](https://github.com/Kotlin/dokka/issues/294) Java version >= `10`.
    Waiting for version >= `0.11.0`

4. `detekt` - Kotlin static code analyser;
5. `kotest` - Kotlin test framework;
6. `jUnit 5` - JUnit 5 platform;
7. `taskTree` - investigate underlying Gradle tasks

Example:
```
./gradlew javadoc taskTree

...

------------------------------------------------------------
Root project
------------------------------------------------------------

:javadoc
+--- :classes
|    +--- :compileJava
|    |    \--- :compileKotlin
|    \--- :processResources
\--- :compileKotlin
```
