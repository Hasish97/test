pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
        disableConcurrentBuilds()
    }

    stages {
        stage('Stop clickup service') {
        
            steps {
                sh 'sudo systemctl stop clickup'
            }
        }

        stage('git repo & clean') {
            steps {
               //sh "rm -rf  /s /q clickup"
                //sh "git clone https://github.com/Hasish97/clickup.git"
                sh "mvn clean"
            }
        }
        stage('install & build') {
            steps {
                sh "mvn clean install"
            }
        }
        stage('test') {
            steps {
                sh "mvn test"
            }
        }
        stage('package') {
            steps {
                sh "mvn package"
            }
        }
        
        stage('Start clickup service') {
                steps {
                    sh 'sudo systemctl start clickup'
                }
            }
            
            
        
        

    }

    
}
