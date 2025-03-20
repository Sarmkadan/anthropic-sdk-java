plugins {
    id("java")
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":anthropic-java"))
    implementation(project(":anthropic-java-bedrock"))
    implementation(project(":anthropic-java-vertex"))
}

java {
    // Allow using more modern APIs, like `List.of` and `Map.of`, in examples.
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

application {
    mainClass = "com.anthropic.example.Main"
}
