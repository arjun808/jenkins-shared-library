def call(def server, def pom) {
pomvalues = readMavenPom file: "${pom}"
sh "scp ${WORKSPACE}/${JOB_NAME}/${BUILD_NUMBER}/${pomvalues.artifactId}-${pomvalues.version}.${pomvalues.packaging} ubuntu@${server}:/home/ubuntu/deploy/${pomvalues.artifactId}.${pomvalues.packaging}"
}
