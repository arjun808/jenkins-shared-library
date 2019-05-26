def call(def pom){
pomvalues = readMavenPom file: "${pom}"
k8foldername = "${pomvalues.artifactId.toLowerCase()}"
sh "kubectl apply -f ${JENKINS_HOME}/${k8foldername}/"
}
