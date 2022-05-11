pipeline {
    agent {
        kubernetes {
            yamlFile 'deployment/kubernetes/agent-pod.yaml'  // path to the pod definition relative to the root of our project 
        }
    }
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
        SERVICE_COLOR = 'N/A'
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
                        DOCKER_IMAGE = docker.build "$REGISTRY"
                        //DOCKER_IMAGE = docker.build("${env.REGISTRY}:${env.BUILD_ID}")
                    }
                }
            }
        }
        stage('Push to DockerHub') {
            steps {
                script {
                    container('docker'){
                        docker.withRegistry('', CRED) {

                            DOCKER_IMAGE.push("$env.BUILD_ID")
                            // DOCKER_IMAGE.push("latest")
                            //DOCKER_IMAGE.push()
                            //docker.image(DOCKER_IMAGE).push()

                        }
                    }   
                }
            }
        }//end stage
        stage('Set eks use'){
            steps{
                container('kubectl'){
                    script{
                        withAWS(credentials:'aws-creds', region:'us-east-1'){
                            sh 'aws eks update-kubeconfig --name team-magma-XOglcml3'
                        }
                    }
                }
            }
        }//end stage

        stage('Get current service color'){
            steps{
                container('kubectl'){
                    script{
                        withAWS(credentials:'aws-creds', region:'us-east-1'){
                            SERVICE_COLOR = sh '$(kubectl get service -n team-magma backend-service -o jsonpath="{.spec.selector.color}")'

                        }
                        sh 'echo $SERVICE_COLOR'
                    }
                }
            }
        }


        // stage('Red Deployment'){
        //     steps{
        //         withAWS(credentials:'aws-creds', region:'us-east-1'){
        //             sh 'kubectl apply -f ./deployment/kubernetes/red-backend-deployment.yml -n team-magma'
        //         }
        //     }
        // }//end stage

        // stage('Black Deployment'){
        //     steps{
        //         withAWS(credentials:'aws-creds', region:'us-east-1'){
        //             sh 'kubectl apply -f ./deployment/kubernetes/black-backend-deployment.yml -n team-magma'
        //         }
        //     }
        // }//end stage

		// stage('Create the service in kubernetes cluster traffic to red deployment') {
		// 	steps {
		// 		withAWS(credentials:'aws-creds', region:'us-east-1') {
		// 			sh 'kubectl apply -f ./deployment/kubernetes/red-backend-service.yml -n team-magma'
		// 		}
		// 	}
		// }//end stage

        // stage('Waiting for approval'){
        //     steps{
        //         script{
        //             try {
        //                 timeout(time:15, unit: 'MINUTES'){
        //                     approved = input mesasage: 'Redirect traffic to Black?', ok: 'Continue',
        //                         parameters: [choice(name: 'approved', choices: 'Yes\nNo', description: 'Redirect to black')]
        //                     if(approved != 'Yes'){
        //                         error('Redirect not approved')
        //                     }
        //                 }
        //             } catch (error){
        //                 error('Redirect not approved in time')
        //             }
        //         }
        //     } 

        // }//end stage

		// stage('Create the service in kubernetes cluster traffic to black deployment') {
		// 	steps {
        //         container ('kubectl') {
		// 		    withAWS(credentials:'aws-creds', region:'us-east-1') {
		// 			    sh 'kubectl apply -f ./deployment/kubernetes/black-backend-service.yml -n team-magma'
		// 		    }
        //         }
		// 	}
		// }//end stage

    }//end stages

}

