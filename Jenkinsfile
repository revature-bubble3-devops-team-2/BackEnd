pipeline {
   agent any

   stages {
      stage('checkout') {
          steps {
            discordSend description: ":cyclone: *Cloning Repo*",
                        webhookURL: "https://discord.com/api/webhooks/908092496905637938/kTyL4F8KdvJfbuOzTdV-u8foJbqRiltJUGYWSbJ65tT61W_AIGGhFva-iuMN-CbYINFH"
            checkout scm
          }
      }
      stage('clean') {
        steps {
        discordSend description: ":soap: *Cleaning ${env.JOB_NAME}*",
                    webhookURL: "https://discord.com/api/webhooks/908092496905637938/kTyL4F8KdvJfbuOzTdV-u8foJbqRiltJUGYWSbJ65tT61W_AIGGhFva-iuMN-CbYINFH"
            sh 'mvn clean'
        }
      }
      stage('test') {
        steps {
            discordSend description: ":memo: *Testing ${env.JOB_NAME}*",
                        webhookURL: "https://discord.com/api/webhooks/908092496905637938/kTyL4F8KdvJfbuOzTdV-u8foJbqRiltJUGYWSbJ65tT61W_AIGGhFva-iuMN-CbYINFH"
            sh 'mvn test'
        }
      }
      stage('package') {
        steps {
            discordSend description: ":package: *Packaging ${env.JOB_NAME}*",
                        webhookURL: "https://discord.com/api/webhooks/908092496905637938/kTyL4F8KdvJfbuOzTdV-u8foJbqRiltJUGYWSbJ65tT61W_AIGGhFva-iuMN-CbYINFH"
          sh 'mvn -DskipTests package'
        }
      }
   }
   post {
      failure {
        script {
            def summary = junit testResults: '**/target/surefire-reports/*.xml'
            if (summary && summary.getFailCount > 0 ) {
                statusComment = "*[${env.JOB_NAME}] <${env.BUILD_URL}|#${env.BUILD_NUMBER}>* failed to build on ${env.GIT_BRANCH} branch."
                statusComment += "\nRan ${summary.getTotalCount()} total tests."
                statusComment += "\n\tFailed ${summary.getFailCount()}, Passed ${summary.getPassCount()}, Skipped ${summary.getSkipCount()}"
                statusComment += "\nSeems you still have a ways to go hm? :face_with_monocle:"
            }
        }
        discordSend description: statusComment, result: currentResult,
                    webhookURL: "https://discord.com/api/webhooks/908092496905637938/kTyL4F8KdvJfbuOzTdV-u8foJbqRiltJUGYWSbJ65tT61W_AIGGhFva-iuMN-CbYINFH"
      }
   }
}