plugins {
    id("java")
}

group = "com.zikeyang"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

var pulsarVersion = "3.1.1"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.pulsar:pulsar-client:$pulsarVersion")
}

tasks.test {
    useJUnitPlatform()
}
