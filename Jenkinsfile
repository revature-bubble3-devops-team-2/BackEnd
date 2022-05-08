def testfail = true
pipeline {
    agent {
        kubernetes {
            label 'docker-in-docker'
            yaml """
apiVersion: v1
kind: Pod
metadata:
  name: docker-pod
  labels:
    app: docker
spec:
  containers:
    - name: docker-cmds
      image: docker:19.03.1
      command: ['docker', 'run', '-p', '80:80', 'httpd:latest']
      resources: 
        limits:
          cpu: 10m
          memory: 256Mi
        requests: 
          cpu: 5m 
          memory: 64Mi 
      env: 
        - name: DOCKER_HOST 
          value: tcp://localhost:2375 
    - name: docker-daemon 
      image: docker:19.03.1-dind 
      resources:
        limits:
          cpu: 20m
          memory: 512Mi
        requests: 
          cpu: 5m 
          memory: 64Mi 
      securityContext:
        privileged: true
      volumeMounts:
        - name: dockersock
          mountPath: /var/lib/docker
  volumes: 
    - name: dockersock
      emptyDir: {}
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