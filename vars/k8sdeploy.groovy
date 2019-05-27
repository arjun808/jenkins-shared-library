def call(def pom, def duser){
pomvalues = readMavenPom file: "${pom}"
k8appname = "${pomvalues.artifactId.toLowerCase()}"
  sh "kubectl set image deployment ${k8appname} ${k8appname}=${duser}/${k8appname}:${BUILD_NUMBER}"
sh "kubectl rollout status deployment ${k8appname}"
}
