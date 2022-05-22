plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.10.1"
}

group = "cf.liyu"
version = "0.3"

dependencies {
    api("com.google.code.gson:gson:2.9.0")
}

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}
