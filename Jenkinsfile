def discordurl = "https://discord.com/api/webhooks/908092496905637938/kTyL4F8KdvJfbuOzTdV-u8foJbqRiltJUGYWSbJ65tT61W_AIGGhFva-iuMN-CbYINFH"
def testfail = true
pipeline {
   agent any

   options {disableConcurrentBuilds()}

   environment {
        DB_URL = "jdbc:postgresql://bubble.cvtq9j4axrge.us-east-1.rds.amazonaws.com:5432/postgres"
        DB_USER = "postgres"
        DB_PASS = "Password123!"
        PORT = 8082
        IMAGE_TAG = "bubbleimg"
        CONTAINER_TAG = "bubblemain"
   }

   stages {
      stage('checkout') {
          steps {
            checkout scm
            discordSend description: ":cyclone: *Cloned Repo*", result: currentBuild.currentResult, webhookURL: discordurl
          }
      }
      stage('clean maven project') {
        steps {
            sh 'mvn clean'
            discordSend description: ":soap: *Cleaned ${env.JOB_NAME}*", result: currentBuild.currentResult, webhookURL: discordurl
        }
      }
      stage('test maven project') {
        steps {
            sh 'mvn test'
            discordSend description: ":memo: *Tested ${env.JOB_NAME}*", result: currentBuild.currentResult, webhookURL: discordurl
            script {testfail = false}
        }
      }
      stage('package maven jar') {
        steps {
            sh 'mvn -DskipTests package'
            discordSend description: ":package: *Packaged ${env.JOB_NAME}*", result: currentBuild.currentResult, webhookURL: discordurl
        }
      }
      stage('remove previous docker image') {
        steps {
            sh 'docker rmi ${IMAGE_TAG} || true'
            discordSend description: ":axe: *Removed Previous Docker Image*", result: currentBuild.currentResult, webhookURL: discordurl
        }
      }
      stage('create docker image') {
        steps {
            sh 'docker build -e fail! -t ${IMAGE_TAG} -f Dockerfile .'
            discordSend description: ":screwdriver: *Built New Docker Image*", result: currentBuild.currentResult, webhookURL: discordurl
        }
      }
   }
    post {
        failure {
            script {
                def statusComment = ""
                if (testfail) {
                    def summary = junit testResults: '**/target/surefire-reports/*.xml'
                    statusComment = "*[${env.JOB_NAME}] <${env.BUILD_URL}|#${env.BUILD_NUMBER}>* failed to build on ${env.GIT_BRANCH} branch."
                    statusComment += "\nRan ${summary.getTotalCount()} total tests."
                    statusComment += "\n\tFailed ${summary.getFailCount()}, Passed ${summary.getPassCount()}, Skipped ${summary.getSkipCount()}"
                    statusComment += "\nSeems you still have a ways to go hm? :face_with_monocle:"
                } else {
                    statusComment = "**${env.JOB_NAME} ended in ${currentBuild.currentResult}**"
                    statusComment += "\n\tCheck the stage that failed for more information"
                }
                discordSend description: statusComment, result: currentBuild.currentResult, webhookURL: discordurl
            }
        }
        success {
            discordSend description: "Pipeline successful! All changes properly integrated!", result: currentBuild.currentResult, webhookURL: discordurl
        }
    }
}