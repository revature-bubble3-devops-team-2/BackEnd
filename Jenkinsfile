def testfail = true
pipeline {
    agent {
        kubernetes {
            yamlFile 'deployment/kubernetes/agent-pod.yaml'  // path to the pod definition relative to the root of our project 
        }
    }
    tools {
        maven 'Maven'
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
        stage('Push to DockerHub') {
            steps {
                script {
                    container('docker'){
                        docker.withRegistry('', CRED) {
                            docker.image(IMAGE_TAG).push()

                        }
                    }   
                }
            }
        }//end stage

        stage('Waiting for approval'){
            steps{
                script{
                    try {
                        timeout(time:30, unit: 'MINUTES'){
                            approved = input mesasage: 'Deploy to production?', ok: 'Continue',
                                parameters: [choice(name: 'approved', choices: 'Yes\nNo', description: 'Deploy this build to production')]
                            if(approved != 'Yes'){
                                error('Build not approved')
                            }
                        }
                    } catch (error){
                        error('Build not approved in time')
                    }
                }
            } 

        }//end stage

    }
}