def call(def pom, def duser, def dburl, def dbuser, def dbpassword) {
pomvalues = readMavenPom file: "${pom}"
imagename = "${pomvalues.artifactId.toLowerCase()}"
sh "mv ${WORKSPACE}/${BUILD_NUMBER}/${pomvalues.artifactId}-${pomvalues.version}.${pomvalues.packaging} ./${pomvalues.artifactId}.${pomvalues.packaging}"
sh "set +x"
sh "docker build . -t ${duser}/${imagename}:${BUILD_NUMBER} --build-arg dburl=\"${dburl}\" --build-arg dbuser=\"${dbuser}\" --build-arg dbpassword=\"${dbpassword}\" --build-arg artifact=${pomvalues.artifactId}.${pomvalues.packaging}"
echo "Docker build underway...."
sh "set -x"
sh "docker push ${duser}/${imagename}:${BUILD_NUMBER}"
}
