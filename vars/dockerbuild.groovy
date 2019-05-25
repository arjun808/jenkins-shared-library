def call(def pom) {
pomvalues = readMavenPom file: "${pom}"
imagename = "${pomvalues.artifactId.toLowerCase()}"
sh "docker build . -t arjun808/${imagename} --build-arg artifact=${WORKSPACE}/${BUILD_NUMBER}/${pomvalues.artifactId}.${pomvalues.packaging}"
sh "docker push arjun808/${imagename}"
}
