plugins {
    `multiloader-loader`
    id("net.neoforged.moddev.legacyforge") version "2.0.107"
    kotlin("jvm") version "2.2.0"
    id("com.google.devtools.ksp") version "2.2.0-2.0.2"
    id("dev.kikugie.fletching-table") version "0.1.0-alpha.13"
}

legacyForge {
    enable {
        forgeVersion = "${mod.mc}-${commonMod.dep("forge")}"
    }
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.1.0")
    annotationProcessor("org.spongepowered:mixin:0.8.5-SNAPSHOT:processor")
    modImplementation("dev.isxander:yet-another-config-lib:${commonMod.dep("yacl")}-forge")
    implementation("me.xdrop:fuzzywuzzy:1.4.0")
    jarJar("me.xdrop:fuzzywuzzy:1.4.0")
    runtimeOnly("me.xdrop:fuzzywuzzy:1.4.0")
}

legacyForge {
    runs {
        register("client") {
            client()
            ideName = "Forge Client (${project.path})"
        }
        register("server") {
            server()
            ideName = "Forge Server (${project.path})"
        }
    }

    parchment {
        commonMod.depOrNull("parchment")?.let {
            mappingsVersion = it
            minecraftVersion = commonMod.mc
        }
    }

    mods {
        register(commonMod.id) {
            sourceSet(sourceSets.main.get())
        }
    }
}

sourceSets.main {
    resources.srcDir("src/generated/resources")
}

tasks {
    jar {
        finalizedBy("reobfJar")
    }
}