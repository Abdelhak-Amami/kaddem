pipeline {
    agent any
    environment {
        registry = "hakkou7/kaddem"
        registryCredential = 'dockerhub'
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

                    sh " docker build ./ -t hakkou7/kaddem:abdelhak "
                   
                   
                }
            }
        }

        stage('push docker  image'){
            steps{
                script {
                     docker.withRegistry('', registryCredential) {
                        sh " docker push hakkou7/kaddem:abdelhak "
                    }
                }
            }
        }
        stage('cleaning image'){
            steps{
                script {
                     
                        sh " docker rmi hakkou7/kaddem:abdelhak"
                    
                }
            }
        }
        stage('Deploy App on k8s') {
          steps {
            withCredentials([
                string(credentialsId: 'my_kubernetes', variable: 'api_token')
                ]) {
                 sh 'kubectl --token $api_token --server 192.168.103.2:8443  --insecure-skip-tls-verify=true delete pods -l app=spring-deploy'
                   }
                }
    }
    }
}
