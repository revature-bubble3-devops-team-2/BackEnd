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
            discordSend description: ":cyclone: *Cloning Repo*", result: currentBuild.currentResult, webhookURL: discordurl
            checkout scm
          }
      }
      stage('clean maven project') {
        steps {
        discordSend description: ":soap: *Cleaning ${env.JOB_NAME}*", result: currentBuild.currentResult, webhookURL: discordurl
            sh 'mvn clean'
        }
      }
      stage('test maven project') {
        steps {
            discordSend description: ":memo: *Testing ${env.JOB_NAME}*", result: currentBuild.currentResult, webhookURL: discordurl
            sh 'mvn test'
            script {testfail = false}
        }
      }
      stage('package maven jar') {
        steps {
            discordSend description: ":package: *Packaging ${env.JOB_NAME}*", result: currentBuild.currentResult, webhookURL: discordurl
            sh 'mvn -DskipTests package'
        }
      }
      stage('remove previous docker image') {
        steps {
            discordSend description: ":axe: *Removing Previous Image*", result: currentBuild.currentResult, webhookURL: discordurl
            sh 'docker rmi ${IMAGE_TAG} || true'
        }
      }
      stage('create docker image') {
        steps {
            discordSend description: ":screwdriver: *Building Docker Image*", result: currentBuild.currentResult, webhookURL: discordurl
            sh 'docker build -e fail! -t ${IMAGE_TAG} -f Dockerfile .'
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
                    statusComment = "**${env.JOB_NAME} ended in ${env.currentResult}**"
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