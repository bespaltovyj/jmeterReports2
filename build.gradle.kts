plugins {
	java
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("kg.apc:jmeter-plugins-autostop:0.1")
	implementation("kg.apc:jmeter-plugins-dummy:0.4")
	implementation("kg.apc:jmeter-plugins-manager:1.3")

	implementation("kg.apc:jmeter-plugins-cmn-jmeter:0.6")
}

tasks {
	val jmeterExtTask = register<Copy>("copyJmeterExt") {
		from({
			configurations.runtimeClasspath.get().filter {
				it.name.startsWith("jmeter-plugins-autostop") ||
				it.name.startsWith("jmeter-plugins-dummy") ||
				it.name.startsWith("jmeter-plugins-manager")
			}
		})
		into(layout.buildDirectory.dir("jmeterExt"))
	}

	val jmeterLibTask = register<Copy>("copyJmeterLib") {
		from({
			configurations.runtimeClasspath.get().filter {
				it.name.startsWith("jmeter-plugins-cmn-jmeter")
			}
		})
		into(layout.buildDirectory.dir("jmeterLib"))
	}

	register<Task>("copyDependenciesForJmeter") {
		dependsOn(jmeterExtTask, jmeterLibTask)
	}
}

