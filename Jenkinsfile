pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Clean and Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        
        stage('Unit Test') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        
        stage('Publish to Nexus') {
            steps {
                // Publish your artifacts to Nexus repository
                // Example: sh 'mvn deploy'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                // Build Docker image
                sh 'docker build -t kaddem:latest .'
            }
        }
        
        stage('Deploy Image') {
            steps {
                
                sh 'kubectl get po'
            }
        }
    }
}
