def call(Map params) {
  def _gitBranch = '*/'+params.gitBranch
  def _gitRepo = params.gitRepo
  def _releaseRepo = params.releaseRepo
  def _snapshotRepo = params.snapshotRepo
  def _dbUrl = params.dbUrl
  def _dbUser = params.dbUser
  def _dbPassword = params.dbPassword
  def _dockerUser = params.dockerUser
  def _sonarURL = params.SonarURL
  def _mvnGoal = params.mvnGoal
  def _POM = params.POM
  def _deploymentServer = params.deploymentServer
  def _sshUser = params.sshUser
  def _deploymentPath = params.deploymentPath
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
  mvnbuild(_mvnGoal,_POM,_releaseRepo,_snapshotRepo)
}


stage('Artifact Download') {
artifactdownload(_POM,_snapshotRepo)
}

stage('Application Deployment'){
  artifactdeploy(_deploymentServer,_POM,_sshUser,_deploymentPath)
}

userInput = input(
        id: 'Proceed1', message: 'Was QA deployment successful?', parameters: [
        [$class: 'BooleanParameterDefinition', defaultValue: true, description: '', name: 'Please confirm']
        ])

if (userInput == true) {
stage('Docker Image Build'){
dockerbuild(_POM,_dockerUser,_dbUrl,_dbUser,_dbPassword)
}
stage('K8s Deployment'){
 k8sdeploy(_POM,_dockerUser)
}
stage('Email Notification'){
    mail(body: "Check result at ${BUILD_URL}", subject: "Build Succeeded for Job ${JOB_NAME} - Build # ${BUILD_NUMBER}", to: _email)
}
}
 }
} 
 catch (err) {
     if ("${err}" == "org.jenkinsci.plugins.workflow.steps.FlowInterruptedException"){
	 mail(body: "Build aborted. Check result at ${BUILD_URL}", subject: "Build Aborted for Job ${JOB_NAME} - Build # ${BUILD_NUMBER}", to: _email)
	 currentBuild.result = 'ABORTED'
	 } else{
     mail(body: "${err} Check result at ${BUILD_URL}", subject: "Build Failed for Job ${JOB_NAME} - Build # ${BUILD_NUMBER}", to: _email)
     currentBuild.result = 'FAILURE'
	 }
}
}

return this
