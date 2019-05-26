def call(def pom){
pomvalues = readMavenPom file: "${pom}"
k8appname = "${pomvalues.artifactId.toLowerCase()}"
sh "kubectl set image deployment ${k8appname} ${k8appname}=arjun808/${k8appname}:${BUILD_NUMBER}"
sh "kubectl rollout status deployment ${k8appname}"
}
