def call(def pom, def duser, def dburl, def dbuser, def dbpassword) {
pomvalues = readMavenPom file: "${pom}"
imagename = "${pomvalues.artifactId.toLowerCase()}"
sh "mv ${WORKSPACE}/${BUILD_NUMBER}/${pomvalues.artifactId}-${pomvalues.version}.${pomvalues.packaging} ./${pomvalues.artifactId}.${pomvalues.packaging}"
  sh "docker build . -t ${duser}/${imagename}:${BUILD_NUMBER} --build-arg artifact=${pomvalues.artifactId}.${pomvalues.packaging} database_url=${dburl} database_user=${dbuser} database_password=${dbpassword}"
  sh "docker push ${duser}/${imagename}:${BUILD_NUMBER}"
}
