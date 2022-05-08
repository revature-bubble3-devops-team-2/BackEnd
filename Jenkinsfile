pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        PORT = 8080
        IMAGE_TAG = 'latest'
        REGISTRY = 'teammagma/bubbleback'
        CONTAINER_NAME = 'bubbleback'
        CRED = 'dockerhub'
        DOCKER_IMAGE = ''
    }

    stages {
        stage('Clean Directory') {
      steps {
        sh 'mvn clean'
      }
        }
        stage('Package Jar') {
      steps {
        sh 'mvn -DskipTests package'
      }
        }
        stage('Create Image') {
      steps {
        //container('docker-cmds') {
          script {
            docker.build("${env.CONTAINER_NAME}:${env.BUILD_ID}")
          }
        //}
      }
        }
        stage('Push to DockerHub') {
      steps {
        script {
          docker.withRegistry('', CRED) {
            docker.image(IMAGE_TAG).push()
          }
        }
      }
        }
    }
}
