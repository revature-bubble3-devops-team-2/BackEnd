pipeline {
   agent any

   stages {
      stage('checkout'){
          steps {
            checkout scm
          }
      }
   }
   post {
      always {
        discordSend result: currentBuild.currentResult,
            webhookURL: "https://discord.com/api/webhooks/908092496905637938/kTyL4F8KdvJfbuOzTdV-u8foJbqRiltJUGYWSbJ65tT61W_AIGGhFva-iuMN-CbYINFH"
      }
   }
}