def call(def server, def pom, def path) {
pomvalues = readMavenPom file: "${pom}"
sh "scp ${WORKSPACE}/${BUILD_NUMBER}/${pomvalues.artifactId}-${pomvalues.version}.${pomvalues.packaging} ubuntu@${server}:${path}${pomvalues.artifactId}.${pomvalues.packaging}"
}
