def testfail = true
pipeline {
    agent any

    tools {
        maven 'Maven'
    }
    // options {
    //     buildDiscarder(logRotator(daysToKeepStr: '7', numToKeepStr: '1'))
    //     disableConcurrentBuilds()
    // }

    environment {
        PORT = 8080
        IMAGE_TAG = 'latest'
        REGISTRY = 'teammagma/bubbleback'
        CONTAINER_NAME = "bubbleback"
        CRED = "dockerhub"
        DOCKER_IMAGE=''
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
                container('docker'){
                    script{
                        dockerImage = docker.build "$registry"
                    }
                }
                    sh 'docker build -t ${IMAGE_TAG} -f Dockerfile .'
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