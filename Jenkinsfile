pipeline {
    agent any
    environment {
        registry = "hakkou7/kaddem"
        registryCredential = 'dockerhub'
        dockerImage = ''
        previousCommitSHA = sh(script: 'git log -n 1 HEAD^ --format=%H', returnStdout: true).trim()
        previousCommitShort = previousCommitSHA.take(8)
        new_commitSHA = "${env.GIT_COMMIT}"
        new_commitShort = new_commitSHA.take(8) 
    }

    stages {
        stage('Get Previous Commit SHA') {
            steps {
                script {
                    previousCommitSHA = sh (script: 'git log -n 1 HEAD^ --format=%H', returnStdout: true).trim()
                    previousCommitShort = previousCommitSHA.take(8)
                    new_commitSHA="${env.GIT_COMMIT}"
                    new_commitShort=new_commitSHA.take(8) 
                }
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
                    sh "docker build ./ -t hakkou7/kaddem:dev${new_commitShort}"
                }
            }
        }

        stage('push docker  image'){
            steps{
                script {
                    docker.withRegistry('', registryCredential) {
                        sh "docker push hakkou7/kaddem:dev${new_commitShort}"
                    }
                }
            }
        }

        stage('cleaning image'){
            steps{
                script {
                    sh "docker rmi hakkou7/kaddem:dev${new_commitShort}"
                }
            }
        }


    }
}
