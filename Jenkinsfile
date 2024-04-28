pipeline {
    agent any
    environment {
        registry = "hakkou7/kaddem"
        registryCredential = '2c07f91c-be5f-45c6-829e-9502c12ef0bd'
        dockerImage = ''
    
    }
    stages {
        stage ('maven sonar') {
            steps {
                sh 'mvn clean'
                sh 'mvn compile'
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=admin1'
            }
        }
        stage ('maven build') {
            steps {
                sh 'mvn package'
            }
        }

        stage("PUBLISH TO NEXUS") {
            steps {
                sh 'mvn deploy'
            }
        }
        stage('Building docker  image') {
            steps {
                script {

                    sh " docker build ./ -t rayennaffouti/kaddem:rayen "
                   
                   
                }
            }
        }

        stage('push docker  image'){
            steps{
                script {
                     docker.withRegistry('', registryCredential) {
                        sh " docker push rayennaffouti/kaddem:rayen "
                    }
                }
            }
        }
        stage('cleaning image'){
            steps{
                script {
                     
                        sh " docker rmi rayennaffouti/kaddem:rayen"
                    
                }
            }
        }
        stage('Deploy with Docker Compose') {
            steps {
                sh 'docker compose up -d'
            }
        }
        stage('deploy our image') {
            steps {
                script {
                    sh "minikube stop"
                    sh "minikube start" 
                    sh "kubectl delete pods -l app=spring-deploy "
                }
            }
        }
    }
}
