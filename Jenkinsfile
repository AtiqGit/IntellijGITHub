pipeline {
    agent any
    tools {
        jdk 'jdk17'
        maven 'maven'
    }

    environment {
        APP_NAME     = "spring-docker-cicd"
        RELEASE_NO   = "1.0.0"
        DOCKER_USER  = "atiqur786"
        IMAGE_NAME   = "${DOCKER_USER}/${APP_NAME}"
        IMAGE_TAG    = "${RELEASE_NO}-${BUILD_NUMBER}"
    }

    stages {

        stage("SCM Checkout") {
            steps {
                git branch: 'master', url: 'https://github.com/AtiqGit/IntellijGITHub.git'
            }
        }

        stage("Build Process") {
            steps {
                bat 'mvn clean install'
            }
        }

        stage("Docker Login") {
            steps {
                withCredentials([string(credentialsId: 'jam', variable: 'TOKEN')]) {
                    bat '''
                        docker logout
                        echo %TOKEN% | docker login -u %DOCKER_USER% --password-stdin
                    '''
                }
            }
        }

        stage("Build Image") {
            steps {
                bat "docker build --pull -t %IMAGE_NAME%:%IMAGE_TAG% ."
            }
        }

        stage("Push Image to Docker Hub") {
            steps {
                bat "docker push %IMAGE_NAME%:%IMAGE_TAG%"
            }
        }

    }
}
