pipeline {
    agent any
    environment {
        registry = "hakkou7/kaddem"
        registryCredential = 'dockerhub'
        dockerImage = ''
    }
    stages {
        stage ('maven sonar') {
            steps{
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
        stage('Building our image') {
            steps {
                script {
                    dockerImage = docker.build(registry + ":$BUILD_NUMBER")
                }
            }
        }
        stage('Deploy our image') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Deploy to Nexus') {
            steps {
                script {
                    sh 'mvn deploy:deploy-file -Durl=localhost:8081 -DrepositoryId=deploymentRepo -Dfile=target/kaddem-abdelhak.war -DgroupId=tn.esprit.spring -DartifactId=kaddem -Dversion=abdelhak -Dpackaging=war'
                }
            }
        }
        stage('Cleaning up') {
            steps {
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
    }
}
