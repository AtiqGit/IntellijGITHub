pipeline {
    agent any
    tools {
        jdk 'jdk17'
        maven 'maven'
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

        stage("Deploy  Container") {
            steps {
                script {
                    deploy adapters: [tomcat9(
                        alternativeDeploymentContext: '',
                        credentialsId: 'tomcat-pwd',
                        path: '',
                        url: 'http://localhost:8085/'
                    )],
                    contextPath: 'springDataRedis',
                    war: '**/*.war'
                }
            }
        }
    }
}
