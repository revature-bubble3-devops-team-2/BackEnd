def testfail = true
pipeline {
    agent {
        kubernetes {
            label 'docker-in-docker-maven'
            yaml """
apiVersion: v1
kind: Pod
spec:
containers:
- name: docker-client
  image: docker:19.03.1
  command: ['sleep', '99d']
  env:
    - name: DOCKER_HOST
      value: tcp://localhost:2375
- name: docker-daemon
  image: docker:19.03.1-dind
  env:
    - name: DOCKER_TLS_CERTDIR
      value: ""
  securityContext:
    privileged: true
  volumeMounts:
      - name: cache
        mountPath: /var/lib/docker
volumes:
  - name: cache
    hostPath:
      path: /tmp
      type: Directory
"""
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
        stage('Checkout') {
            steps {
                git 'https://github.com/jenkinsci/docker-jnlp-slave.git'
            }
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
            container('docker-cmds') {
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