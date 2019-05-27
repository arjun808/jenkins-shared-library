def call(def pom, def duser, def dburl, def dbuser, def dbpassword) {
pomvalues = readMavenPom file: "${pom}"
imagename = "${pomvalues.artifactId.toLowerCase()}"
sh "mv ${WORKSPACE}/${BUILD_NUMBER}/${pomvalues.artifactId}-${pomvalues.version}.${pomvalues.packaging} ./${pomvalues.artifactId}.${pomvalues.packaging}"
  sh "docker build . -t ${duser}/${imagename}:${BUILD_NUMBER} --build-arg database_url=${dburl} --build-arg database_user=${dbuser} --build-arg database_password=${dbpassword} --build-arg artifact=${pomvalues.artifactId}.${pomvalues.packaging}\""
  sh "docker push ${duser}/${imagename}:${BUILD_NUMBER}"
}
