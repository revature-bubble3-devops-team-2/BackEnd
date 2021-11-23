def testfail = true
pipeline {
    agent any

    options {
        buildDiscarder(logRotator(daysToKeepStr: '7', numToKeepStr: '1'))
        disableConcurrentBuilds()
    }

    environment {
        PORT = 8082
        IMAGE_TAG = "cpete22/revature-bubble:be"
        CONTAINER_NAME = "bubblebe"
        CRED = "dockerhub"
    }

    stages {
        stage('Clean Directory') {
            steps {
                sh 'mvn clean'
                discordSend description: ":soap: *Cleaned ${env.JOB_NAME}*", result: currentBuild.currentResult,
                webhookURL: env.WEBHO_BE
            }
        }
        stage('Run Tests') {
            steps {
                sh 'mvn test'
                discordSend description: ":memo: *Tested ${env.JOB_NAME}*", result: currentBuild.currentResult, webhookURL: env.WEBHO_BE
                script {testfail = false}
            }
        }
        stage('Package Jar') {
            steps {
                sh 'mvn -DskipTests package'
                discordSend description: ":package: *Packaged ${env.JOB_NAME}*", result: currentBuild.currentResult, webhookURL: env.WEBHO_BE
            }
        }
        stage('SonarCloud') {
            environment {
                SCANNER_HOME = tool 'sonar'
                ORGANIZATION = "revature-bubble"
                PROJECT_NAME = "Revature-Bubble_BackEnd"
            }
            steps {
                withSonarQubeEnv('CloudScan') {
                    sh '''$SCANNER_HOME/bin/sonar-scanner -Dsonar.organization=$ORGANIZATION \
                        -Dsonar.java.binaries=target/classes/com/revature/ \
                        -Dsonar.projectKey=$PROJECT_NAME \
                        -Dsonar.sources=.'''
                }
            }
        }
        stage("Quality Gate") {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    script {
                        def qg = waitForQualityGate abortPipeline: true
                        discordSend description: ":closed_lock_with_key: **Quality Gate: ${qg.status}**", result: currentBuild.currentResult, webhookURL: env.WEBHO_BE
                    }
                }
            }
        }
        stage('Remove Previous Artifacts') {
            steps {
                sh 'docker stop ${CONTAINER_NAME} || true'
                discordSend description: ":axe: *Removed Previous Docker Artifacts*", result: currentBuild.currentResult, webhookURL: env.WEBHO_BE
            }
        }
        stage('Create Image') {
            steps {
                sh 'docker build -t ${IMAGE_TAG} -f Dockerfile .'
                discordSend description: ":screwdriver: *Built New Docker Image*", result: currentBuild.currentResult, webhookURL: env.WEBHO_BE
            }
        }
        stage('Run Container') {
            steps {
                sh 'docker run -d --env DB_URL --env DB_USER --env DB_PASS --rm -p ${PORT}:${PORT} --name ${CONTAINER_NAME} ${IMAGE_TAG} '
                discordSend description: ":whale: *Running Docker Container*", result: currentBuild.currentResult, webhookURL: env.WEBHO_BE
            }
        }
        stage('Push to DockerHub') {
            steps {
                script {
                    docker.withRegistry('', CRED) {
                          docker.image(IMAGE_TAG).push()
                    }
                }
                discordSend description: ":face_in_clouds: *Pushed Latest to DockerHub*", result: currentBuild.currentResult, webhookURL: env.WEBHO_BE
            }
        }
    }
    post {
        failure {
            script {
                def statusComment = ""
                if (testfail) {
                    def summary = junit testResults: '**/target/surefire-reports/*.xml'
                    statusComment = "*[${env.JOB_NAME}] <#${env.BUILD_NUMBER}>* failed to build on ${env.GIT_BRANCH} branch."
                    statusComment += "\nRan ${summary.getTotalCount()} total tests."
                    statusComment += "\n\tFailed ${summary.getFailCount()}, Passed ${summary.getPassCount()}, Skipped ${summary.getSkipCount()}"
                    statusComment += "\nSeems you still have a ways to go hm? :face_with_monocle:"
                } else {
                    statusComment = "**${env.JOB_NAME} ended in ${currentBuild.currentResult}**"
                    statusComment += "\n\tCheck the stage that failed for more information"
                }
                discordSend description: statusComment, result: currentBuild.currentResult, webhookURL: env.WEBHO_BE
            }
        }
        success {
            discordSend description: ":potable_water: **Pipeline successful!**", result: currentBuild.currentResult, webhookURL: env.WEBHO_BE
            sh 'docker container ls'
        }
    }
}