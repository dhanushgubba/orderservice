pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'order-service'
        DOCKER_TAG = 'latest'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/dhanushgubba/orderservice.git'
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub',
                    usernameVariable: 'DOCKER_USERNAME',
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    script {
                        sh """
                            echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
                            docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} $DOCKER_USERNAME/${DOCKER_IMAGE}:${DOCKER_TAG}
                            docker push $DOCKER_USERNAME/${DOCKER_IMAGE}:${DOCKER_TAG}
                        """
                    }
                }
            }
        }
        stage('Deploy to EC2') {
			steps {
				sshagent(['ec2-ssh-key']) {
					sh """
					ssh -o StrictHostKeyChecking=no ubuntu@3.108.194.180 '
                    docker pull dhanushgubba/order-service:latest &&
                    docker stop order-service || true &&
                    docker rm order-service || true &&
                    docker run -d -p 8084:8084 --name order-service dhanushgubba/order-service:latest
                	'
           		 	"""
        		}
    		}
		}
    }
}
