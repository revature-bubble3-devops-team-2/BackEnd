pipeline {
   agent any

   stages {
      stage('checkout'){
          steps {
            checkout scm
          }
      }
      stage('clean') {
        steps {
            sh 'mvn clean'
        }
      }
      stage('package') {
        steps {
          sh 'mvn package'
        }
      }
   }
   post {
      always {
        discordSend result: currentBuild.currentResult, title: JOB_NAME, description: "Jenkins build completed",
            webhookURL: "https://discord.com/api/webhooks/908092496905637938/kTyL4F8KdvJfbuOzTdV-u8foJbqRiltJUGYWSbJ65tT61W_AIGGhFva-iuMN-CbYINFH"
      }

   }
}