def call(Map params) {
  def _gitBranch = '*/'+params.gitBranch
  def _gitRepo = params.gitRepo
  def _sonarURL = params.SonarURL
  def _mvnGoal = params.mvnGoal
  def _POM = params.POM
  def _deploymentServer = params.deploymentServer
  def _email = params.email
  
  try {
  node('master') {
stage('Clone') 
{
  gitclone(_gitBranch,_gitRepo)
}


stage('SonarQube analysis') 
{
  sonarscan(_sonarURL)
}


stage('SonarQube quality gate check')
{
  sonarqg()
}

stage('Build & Upload')
{
  mvnbuild(_mvnGoal,_POM)
}


stage('Artifact Download') {
artifactdownload(_POM)
}

stage('Application Deployment'){
  artifactdeploy(_deploymentServer,_POM)
}

userInput = input(
        id: 'Proceed1', message: 'Was deployment successful?', parameters: [
        [$class: 'BooleanParameterDefinition', defaultValue: true, description: '', name: 'Please confirm']
        ])

if (userInput == true) {
stage('Docker Image Build'){
dockerbuild(_POM)
}
stage('K8s Deployment'){
 k8sdeploy(_POM)
}
stage('Email Notification'){
    mail(body: "Check result at ${BUILD_URL}", subject: "Build Succeeded for Job ${JOB_NAME} - Build # ${BUILD_NUMBER}", to: _email)
}
}
 }
} 
 catch (err) {
     if ("${err}" == "org.jenkinsci.plugins.workflow.steps.FlowInterruptedException"){
	 mail(body: "Check result at ${BUILD_URL}", subject: "Build Aborted for Job ${JOB_NAME} - Build # ${BUILD_NUMBER}", to: _email)
	 currentBuild.result = 'ABORTED'
	 } else{
     mail(body: "${err} Check result at ${BUILD_URL}", subject: "Build Failed for Job ${JOB_NAME} - Build # ${BUILD_NUMBER}", to: _email)
     currentBuild.result = 'FAILURE'
	 }
}
}

return this
