pipeline {
    agent any
    environment {
        registry = "nagui69/kaddem"
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

                    sh " docker build ./ -t nagui69/kaddem:abdelhak "
                   
                   
                }
            }
        }

        stage('push docker  image'){
            steps{
                script {
                     docker.withRegistry('', registryCredential) {
                        sh " docker push nagui69/kaddem:abdelhak "
                    }
                }
            }
        }
        stage('cleaning image'){
            steps{
                script {
                     
                        sh " docker rmi nagui69/kaddem:abdelhak"
                    
                }
            }
        }

        stage('Apply Kubernetes files') {
            steps {
             withKubeConfig([credentialsId: 'kube' ]) {
              sh 'sed -i "s/abdelhak/dev${new_commitShort}/g" deploy.yaml'
              sh 'kubectl apply -f deploy.yaml'
            }
          }
        }
    }
}
