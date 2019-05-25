def call(def pom) {
pomvalues = readMavenPom file: "${pom}"
sh "docker build . -t arjun808/${pomvalues.artifactId} --build-arg artifact=${WORKSPACE}/${BUILD_NUMBER}/${pomvalues.artifactId}.${pomvalues.packaging}"
sh "docker push arjun808/${pomvalues.artifactId}"
}
