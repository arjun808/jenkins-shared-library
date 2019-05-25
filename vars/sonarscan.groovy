def call(def URL) {
    def scannerHome = tool 'Sonar';
    withSonarQubeEnv('sonar') {
      sh "mvn sonar:sonar -Dsonar.host.url=${URL}"
   }
 }
 
