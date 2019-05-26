def call(Map params) {
  try {
  node('master') {
  def _gitBranch = '*/'+params.gitBranch
  def _gitRepo = params.gitRepo
  def _sonarURL = params.SonarURL
  def _mvnGoal = params.mvnGoal
  def _POM = params.POM
  def _deploymentServer = params.deploymentServer
  def _email = params.email
  
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
} catch (err) {
     mail(body: "${err}. Check result at ${BUILD_URL}", subject: "Build Failed for Job ${JOB_NAME} - Build # ${BUILD_NUMBER}", to: _email)
     currentBuild.result = 'FAILURE'
	 }
}
return this
