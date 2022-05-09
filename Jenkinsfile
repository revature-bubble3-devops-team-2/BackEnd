def testfail = true
pipeline {
    agent {
        kubernetes {
            yamlFile 'deployment/kubernetes/agent-pod.yaml'  // path to the pod definition relative to the root of our project 
            defaultContainer 'maven' 
        }
    }
    
    options {
        buildDiscarder(logRotator(daysToKeepStr: '7', numToKeepStr: '1'))
        disableConcurrentBuilds()
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
                container('docker') {
                    // sh 'docker build -t ${IMAGE_TAG} -f Dockerfile .'
                    script {
                        docker.build("${env.CONTAINER_NAME}:${env.BUILD_ID}")
                    }
                }
            }
        }
        //     stage('Push to DockerHub') {
        //     steps {
        //         script {
        //         docker.withRegistry('', CRED) {
        //             docker.image(IMAGE_TAG).push()
        //         }
        //     }
        // }
        //     }
        }
}