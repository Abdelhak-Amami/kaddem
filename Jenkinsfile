pipeline {
    agent any
    environment {
        registry = "louman06/kaddem"
        registryCredential = 'dockerHub'
        dockerImage = ''
        previousCommitSHA = sh(script: 'git log -n 1 HEAD^ --format=%H', returnStdout: true).trim()
        previousCommitShort = previousCommitSHA.take(8)
        new_commitSHA = "${env.GIT_COMMIT}"
        new_commitShort = new_commitSHA.take(8) 
    }

    stages {

        stage('Test') {
            steps {
                sh 'mvn clean'
                sh 'mvn compile'
                sh 'mvn test'
            }
        }
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
                    sh "docker build ./ -t louman06/kaddem:dev${new_commitShort}"
                }
            }
        }

        stage('push docker  image'){
            steps{
                script {
                    docker.withRegistry('', registryCredential) {
                        sh "docker push louman06/kaddem:dev${new_commitShort}"
                    }
                }
            }
        }

        stage('cleaning image'){
            steps{
                script {
                    sh "docker rmi louman06/kaddem:dev${new_commitShort}"
                }
            }
        }
        stage('docker compose'){
            steps{
                script {
                    sh 'sed -i "s/abdelhak/dev${new_commitShort}/g" docker-compose.yml'
                    sh 'docker-compose up -d '
                }
            }
        }
    }
}
