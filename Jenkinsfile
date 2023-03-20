pipeline {
    agent any

    environment {
        IMAGE_NAME = 'everythingshop'
        CONTAINER_NAME = 'everythingshop'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    docker.build("${IMAGE_NAME}:latest")
                }
            }
        }

        stage('Docker Deploy') {
            steps {
                script {
                    sh "sudo docker stop ${CONTAINER_NAME} || true"
                    sh "sudo docker rm ${CONTAINER_NAME} || true"
                    sh "sudo docker run -d --name ${CONTAINER_NAME} -p 8080:8080 ${IMAGE_NAME}:latest"
                }
            }
        }
    }
}