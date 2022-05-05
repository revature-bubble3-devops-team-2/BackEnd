pipeline {
  agent {
    kubernetes {
      label 'bubble-back'
      defaultContainer 'jnlp'
      yaml """
apiVersion: v1
kind: Pod
metadata:
labels:
  component: ci
spec:
  # Use service account that can deploy to all namespaces
  serviceAccountName: cd-jenkins
  containers:
  - name: maven
    image: maven:latest
    command:
    - cat
    tty: true
    volumeMounts:
      - mountPath: "/root/.m2"
        name: m2
  - name: docker
    image: docker:latest
    command:
    - cat
    tty: true
    volumeMounts:
    - mountPath: /var/run/docker.sock
      name: docker-sock
  volumes:
    - name: docker-sock
      hostPath:
        path: /var/run/docker.sock
    - name: m2
      persistentVolumeClaim:
        claimName: m2
"""
}
   }
   environment {
       IMAGE_TAG = "teammagma/bubbleback"
   }
   
  stages {
      stage('Clean Directory') {
            steps {
                sh 'mvn clean'
            }
        }
    stage('Maven Build') {
      steps {
        container('maven') {
          sh """
                        mvn package -DskipTests
                                                """
        }
      }
    }
    stage('Docker build') {
      steps {
        container('docker') {
          sh """
            docker build -t bubbleback:$BUILD_NUMBER .
          """
        }
      }
    }
  }
}





// def testfail = true
// pipeline {
//     agent any

//     tools {
//         maven 'Maven'
//     }
//     // options {
//     //     buildDiscarder(logRotator(daysToKeepStr: '7', numToKeepStr: '1'))
//     //     disableConcurrentBuilds()
//     // }

//     environment {
//         PORT = 8080
//         IMAGE_TAG = 'latest'
//         REGISTRY = 'teammagma/bubbleback'
//         CONTAINER_NAME = "bubbleback"
//         CRED = "dockerhub"
//         DOCKER_IMAGE=''
//     }

//     stages {

//         stage('Clean Directory') {
//             steps {
//                 sh 'mvn clean'
//             }
//         }
//         stage('Package Jar') {
//             steps {
//                 sh 'mvn -DskipTests package'
//             }
//         }
//         stage('Create Image') {
//             steps {
//                 container('docker'){
//                     script{
//                         dockerImage = docker.build "$registry"
//                     }
//                 }
//                     sh 'docker build -t ${IMAGE_TAG} -f Dockerfile .'
//             }
//         }
//         stage('Push to DockerHub') {
//             steps {
//                 script {
//                     docker.withRegistry('', CRED) {
//                         docker.image(IMAGE_TAG).push()
//                     }
//                 }
//             }
//         }
//     }
// }

