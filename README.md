# jenkins-shared-library

This repo contains code created as part of an exploration of the Jenkins Shared Library concept.
The following variables are used inside:
* gitBranch
* gitRepo
* releaseRepo
* snapshotRepo
* dbUrl
* dbUser
* dbPassword
* dockerUser
* sonarURL
* mvnGoal
* POM
* deploymentServer
* sshUser
* deploymentPath
* email

The main function MvnPipeline should be called in the pipeline script including the above mentioned variables.
